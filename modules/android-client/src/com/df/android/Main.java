package com.df.android;

import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnDragListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.df.android.entity.Dish;
import com.df.android.entity.Item;
import com.df.android.entity.Shop;
import com.df.android.entity.Table;
import com.df.android.menu.Menu;
import com.df.android.menu.MenuPagerAdapter;
import com.df.android.network.NetworkStatusChangeListener;
import com.df.android.network.NetworkStatusMonitor;
import com.df.android.order.OnsiteOrderLine;
import com.df.android.order.Order;
import com.df.android.order.OrderChangeListener;
import com.df.android.order.OrderLine;
import com.df.android.order.SalesOrder;
import com.df.android.ui.OrderListViewAdapter;

public class Main extends Activity implements OrderChangeListener,
		NetworkStatusChangeListener {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		final Shop shop = initShop("demo");

		ViewPager viewPager = (ViewPager) findViewById(R.id.menuPager);
		viewPager.setAdapter(new MenuPagerAdapter(this, shop));

		((Button) findViewById(R.id.btnCreateOrder))
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View view) {
						final Context cxt = view.getContext();
						final String[] tables = new String[shop.getTables()
								.size()];

						int i = 0;
						for (Table table : shop.getTables())
							tables[i++] = table.getId();

						AlertDialog.Builder builder = new AlertDialog.Builder(
								cxt);
						builder.setTitle(R.string.chooseTable).setItems(tables,
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int which) {
										createOrder(tables[which]);
									}
								});

						builder.create().show();

					}

				});

		findViewById(R.id.openClose).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				LinearLayout leftPane = (LinearLayout) findViewById(R.id.leftPane);

				if (leftPane.getVisibility() == View.VISIBLE) {
					leftPane.setVisibility(View.GONE);
				} else {
					leftPane.setVisibility(View.VISIBLE);
				}
			}
		});

		NetworkStatusMonitor networkMonitor = new NetworkStatusMonitor();
		networkMonitor.registerListener(this);
		this.registerReceiver(networkMonitor, new IntentFilter(
				ConnectivityManager.CONNECTIVITY_ACTION));
	}

	private Shop initShop(String shopId) {
		Shop shop = new Shop(shopId, shopId);

		Menu menu = buildMenuFromMetaFile("cache/" + shopId + "/menu.xml");
		List<Table> tables = buildTablesFromMetaFile("cache/" + shopId
				+ "/tables.xml");

		shop.setMenu(menu);
		shop.setTables(tables);

		return shop;
	}

	private void createOrder(final String table) {
		new Table(table);
		Order order = new SalesOrder(table, Order.OrderType.Onsite);
		order.registerChangeListener(this);

		final ListView lstOrder = (ListView) findViewById(R.id.lstOrder);
		OrderListViewAdapter orderAdapter = new OrderListViewAdapter(this,
				order);
		order.registerChangeListener(orderAdapter);
		// orderAdapter.setOrder(order);
		lstOrder.setAdapter(orderAdapter);
		lstOrder.setOnDragListener(new OnDragListener() {
			@Override
			public boolean onDrag(View v, DragEvent event) {
				switch (event.getAction()) {
				case DragEvent.ACTION_DRAG_STARTED:
					break;
				case DragEvent.ACTION_DRAG_ENTERED:
					v.setBackgroundColor(Color.LTGRAY);
					break;
				case DragEvent.ACTION_DRAG_EXITED:
					v.setBackgroundColor(Color.WHITE);
					break;
				case DragEvent.ACTION_DROP:
					break;
				case DragEvent.ACTION_DRAG_ENDED:
					v.setBackgroundColor(Color.WHITE);
					View srcView = (View) event.getLocalState();
					Item item = (Item) srcView.getTag();
					Order.currentOrder().addLine(new OnsiteOrderLine(item, Table.getCurrentTable()));
				default:
					break;
				}
				return true;
			}
		});

		((TextView) findViewById(R.id.orderId)).setText(order.getId());
	}

	private Menu buildMenuFromMetaFile(String metaFile) {
		Menu menu = new Menu();

		Log.i(getClass().getName(), "Building menu from " + metaFile);

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document dom = builder.parse(this.getAssets().open(metaFile));
			Element root = dom.getDocumentElement();
			NodeList xmlItems = root.getElementsByTagName("item");

			Log.d(getClass().getName(), "Found " + xmlItems.getLength()
					+ " items");

			for (int i = 0; i < xmlItems.getLength(); i++) {
				Element xmlItem = (Element) xmlItems.item(i);
				String name = xmlItem.getAttribute("name");
				// ItemCategory type =
				// MenuItem.dishTypes[Integer.parseInt(xmlItem
				// .getAttribute("type"))];
				String image = xmlItem.getAttribute("image");
				float price = Float.parseFloat(xmlItem.getAttribute("price"));
				//
				Item item = new Dish(name, name);
				item.setPrice(price);
				item.setImage(image);
				menu.addItem(item);
			}
		} catch (Exception e) {
			Log.e(getClass().getName(),
					"Fail to load menu from meta file due to " + e);
		}

		return menu;
	}

	private List<Table> buildTablesFromMetaFile(String metaFile) {
		List<Table> tables = new ArrayList<Table>();

		Log.i(getClass().getName(), "Building tables from " + metaFile);

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document dom = builder.parse(this.getAssets().open(metaFile));
			Element root = dom.getDocumentElement();
			NodeList xmlItems = root.getElementsByTagName("table");

			Log.d(getClass().getName(), "Found " + xmlItems.getLength()
					+ " tables");

			for (int i = 0; i < xmlItems.getLength(); i++) {
				Element xmlItem = (Element) xmlItems.item(i);
				String id = xmlItem.getAttribute("id");

				tables.add(new Table(id));
			}
		} catch (Exception e) {
			Log.e(getClass().getName(),
					"Fail to load menu from meta file due to " + e);
		}

		return tables;
	}

	// @Override
	// public void onMenuItemAdded(MenuItem item) {
	// ((OrderAdapter) ((ListView) findViewById(R.id.lstOrder)).getAdapter())
	// .notifyDataSetChanged();
	// ((TextView) findViewById(R.id.orderCount)).setText(""
	// + Order.currentOrder().getCount());
	// }
	//
	// @Override
	// public void onMenuItemRemoved(MenuItem item) {
	// ((OrderAdapter) ((ListView) findViewById(R.id.lstOrder)).getAdapter())
	// .notifyDataSetChanged();
	// ((TextView) findViewById(R.id.orderCount)).setText(""
	// + Order.currentOrder().getCount());
	// }

	private void showNetworkStatus() {
		TextView tvNetworkStatus = (TextView) findViewById(R.id.tvNetworkStatus);
		Button btnSendOrder = (Button) findViewById(R.id.btnSendOrder);
		if (isOnline()) {
			tvNetworkStatus.setVisibility(View.GONE);
			btnSendOrder.setVisibility(View.VISIBLE);
		} else {
			tvNetworkStatus.setText("当前没有网络连接，落单功能将不可用");
			tvNetworkStatus.setVisibility(View.VISIBLE);
			btnSendOrder.setVisibility(View.INVISIBLE);
		}

	}

	private boolean isOnline() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

		if (cm != null) {
			NetworkInfo netInfo = cm.getActiveNetworkInfo();
			return netInfo != null && netInfo.isConnectedOrConnecting();
		}

		return false;
	}

	@Override
	public void onNetworkStatusChange() {
		showNetworkStatus();
	}

	@Override
	public void onLineAdded(OrderLine line) {
		((TextView) findViewById(R.id.orderCount)).setText(""
				+ Order.currentOrder().getItemCount());
	}

	@Override
	public void onLineRemoved(OrderLine line) {
		((TextView) findViewById(R.id.orderCount)).setText(""
				+ Order.currentOrder().getItemCount());
	}
}
