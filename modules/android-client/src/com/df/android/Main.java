package com.df.android;

import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.app.Activity;
import android.content.Context;
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
import com.df.android.entity.ItemCategory;
import com.df.android.entity.Shop;
import com.df.android.entity.Table;
import com.df.android.menu.Menu;
import com.df.android.menu.MenuPagerAdapter;
import com.df.android.network.NetworkStatusChangeListener;
import com.df.android.network.NetworkStatusMonitor;
import com.df.android.order.OnsiteOrder;
import com.df.android.order.Order;
import com.df.android.order.OrderChangeListener;
import com.df.android.order.OrderLine;
import com.df.android.ui.CreateOrderDialog;
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

		findViewById(R.id.showHideOrder).setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View view) {
						LinearLayout leftPane = (LinearLayout) findViewById(R.id.leftPane);

						if (leftPane.getVisibility() == View.VISIBLE) {
							leftPane.setVisibility(View.GONE);
						} else {
							if (Order.currentOrder() == null)
								chooseTable(view.getContext(), shop);

							leftPane.setVisibility(View.VISIBLE);
						}
					}
				});

		NetworkStatusMonitor networkMonitor = new NetworkStatusMonitor();
		networkMonitor.registerListener(this);
		this.registerReceiver(networkMonitor, new IntentFilter(
				ConnectivityManager.CONNECTIVITY_ACTION));
	}

	private void chooseTable(Context cxt, final Shop shop) {
		new CreateOrderDialog(cxt, shop).show();
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

	private void createOrder(final Table table) {
		Order order = new OnsiteOrder("order id");
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

					OrderLine line = new OrderLine(item);
					line.setTable(Table.getCurrentTable());
					Order.currentOrder().addLine(line);
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

		Log.d(getClass().getName(), "Building menu from " + metaFile);

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
				String id = xmlItem.getAttribute("id");
				String name = xmlItem.getAttribute("name");
				Item item = new Dish(id, name);

				String image = xmlItem.getAttribute("image");
				float price = Float.parseFloat(xmlItem.getAttribute("price"));
				item.setPrice(price);
				item.setImage(image);

				NodeList xmlCategories = xmlItem
						.getElementsByTagName("category");
				for (int j = 0; j < xmlCategories.getLength(); j++) {
					item.addCategory(ItemCategory.valueOf(xmlCategories.item(j)
							.getTextContent()));
				}

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
				int capacity = Integer.parseInt(xmlItem.getAttribute("capacity"));

				tables.add(new Table(id, capacity));
			}
		} catch (Exception e) {
			Log.e(getClass().getName(),
					"Fail to load menu from meta file due to " + e);
		}

		return tables;
	}

	private void showNetworkStatus() {
		TextView tvNetworkStatus = (TextView) findViewById(R.id.tvNetworkStatus);
		Button btnSendOrder = (Button) findViewById(R.id.btnSendOrder);
		if (isOnline()) {
			tvNetworkStatus.setVisibility(View.GONE);
			btnSendOrder.setVisibility(View.VISIBLE);
		} else {
			tvNetworkStatus.setText(R.string.network_unavailable);
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
		((TextView) findViewById(R.id.orderTotal)).setText(""
				+ Order.currentOrder().getTotal());
	}

	@Override
	public void onLineRemoved(OrderLine line) {
		((TextView) findViewById(R.id.orderCount)).setText(""
				+ Order.currentOrder().getItemCount());
		((TextView) findViewById(R.id.orderTotal)).setText(""
				+ Order.currentOrder().getTotal());
	}
}
