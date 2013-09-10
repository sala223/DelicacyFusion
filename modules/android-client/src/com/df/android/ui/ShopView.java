package com.df.android.ui;

import java.util.UUID;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.AttributeSet;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.df.android.GlobalSettings;
import com.df.android.R;
import com.df.android.entity.Item;
import com.df.android.entity.Shop;
import com.df.android.entity.Table;
import com.df.android.network.NetworkStatusChangeListener;
import com.df.android.network.NetworkStatusMonitor;
import com.df.android.order.Order;
import com.df.android.order.OrderChangeListener;
import com.df.android.order.OrderFactory;
import com.df.android.order.OrderLine;

public class ShopView extends FrameLayout implements
		NetworkStatusChangeListener, OrderChangeListener {
	private Context cxt;
	private View shopView = null;

	public ShopView(Context context) {
		super(context);
		this.cxt = context;
		initView();
	}

	public ShopView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.cxt = context;
		initView();
	}

	private void initView() {
		LayoutInflater li = LayoutInflater.from(cxt);

		if (GlobalSettings.getCurrentShop() == null)
			this.addView(li.inflate(R.layout.initshopview, this, false));
		else
			refreshShopView(GlobalSettings.getCurrentShop());

		new ShopViewAdapter(cxt)
				.setOnShopChangeListener(new ShopChangeListener() {

					@Override
					public void onChange(Shop shop) {
						Log.d(getClass().getName(),
								"Received shop change message");

						refreshShopView(shop);
					}
				});

	}

	private void refreshShopView(final Shop shop) {
		Log.d(getClass().getName(), "Refreshing shop view");
		LayoutInflater li = LayoutInflater.from(cxt);
		if (shopView == null) {
			this.removeAllViews();

			shopView = li.inflate(R.layout.shopview, this, false);

			shopView.findViewById(R.id.showHideOrder).setOnClickListener(
					new OnClickListener() {
						@Override
						public void onClick(View view) {
							LinearLayout leftPane = (LinearLayout) shopView
									.findViewById(R.id.leftPane);

							if (leftPane.getVisibility() == View.VISIBLE) {
								leftPane.setVisibility(View.GONE);
							} else {
								if (OrderFactory.currentOrder() == null)
									chooseTable(view.getContext(), shop);
								else
									leftPane.setVisibility(View.VISIBLE);
							}
						}
					});

			shopView.findViewById(R.id.btnSendOrder).setOnClickListener(
					new OnClickListener() {
						@Override
						public void onClick(View view) {
							sendOrder(OrderFactory.currentOrder());
							OrderFactory.clearCurrentOrder();
						}
					});

			NetworkStatusMonitor networkMonitor = new NetworkStatusMonitor();
			networkMonitor.registerListener(this);
			cxt.registerReceiver(networkMonitor, new IntentFilter(
					ConnectivityManager.CONNECTIVITY_ACTION));
			
			this.addView(shopView);
		}

		((MenuView) shopView.findViewById(R.id.menuView)).setShop(shop);
	}

	private void chooseTable(Context cxt, final Shop shop) {
		final CreateOrderDialog chooseTableDialog = new CreateOrderDialog(cxt,
				shop);
		chooseTableDialog.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss(DialogInterface dialog) {
				if (chooseTableDialog.getSelectedTable() != null) {
					createOrder(chooseTableDialog.getSelectedTable(),
							chooseTableDialog.getHeadCount());
				}
			}

		});
		chooseTableDialog.show();
	}

	private void createOrder(final Table table, int headCount) {
		Order order = OrderFactory.createOnsiteOrder(UUID.randomUUID()
				.toString().substring(0, 10), table, headCount);
		order.registerChangeListener(this);

		final ListView lstOrder = (ListView) shopView
				.findViewById(R.id.lstOrder);
		OrderListViewAdapter orderAdapter = new OrderListViewAdapter(cxt, order);
		order.registerChangeListener(orderAdapter);

		initOrder(order);

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
					OrderFactory.currentOrder().addLine(line);
				default:
					break;
				}
				return true;
			}
		});

		((TextView) shopView.findViewById(R.id.orderId)).setText(order.getId());
	}

	private void initOrder(final Order order) {

	}

	private void sendOrder(final Order order) {
		final LinearLayout leftPane = (LinearLayout) shopView
				.findViewById(R.id.leftPane);
		if (leftPane.getVisibility() == View.VISIBLE)
			leftPane.setVisibility(View.GONE);

		((TextView) shopView.findViewById(R.id.orderCount)).setText("0");
	}

	private void showNetworkStatus() {
		TextView tvNetworkStatus = (TextView) shopView
				.findViewById(R.id.tvNetworkStatus);
		Button btnSendOrder = (Button) shopView.findViewById(R.id.btnSendOrder);
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
		ConnectivityManager cm = (ConnectivityManager) cxt
				.getSystemService(Context.CONNECTIVITY_SERVICE);

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
		((TextView) shopView.findViewById(R.id.orderCount)).setText(""
				+ OrderFactory.currentOrder().getItemCount());
		((TextView) shopView.findViewById(R.id.orderTotal)).setText(""
				+ OrderFactory.currentOrder().getTotal());
	}

	@Override
	public void onLineRemoved(OrderLine line) {
		((TextView) shopView.findViewById(R.id.orderCount)).setText(""
				+ OrderFactory.currentOrder().getItemCount());
		((TextView) shopView.findViewById(R.id.orderTotal)).setText(""
				+ OrderFactory.currentOrder().getTotal());
	}
}
