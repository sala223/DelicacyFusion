package com.df.android.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.df.android.GlobalSettings;
import com.df.android.R;
import com.df.android.entity.Item;
import com.df.android.menu.ItemOrderListener;
import com.df.android.menu.MenuEventDispatcher;
import com.df.android.order.Order;
import com.df.android.order.OrderChangeListener;
import com.df.android.order.OrderCreateListener;
import com.df.android.order.OrderLine;
import com.df.android.order.OrderMgr;
import com.df.android.utils.WebTask;
import com.df.client.rs.resource.OrderResource;

public class OrderView extends LinearLayout implements ItemOrderListener,
		OrderCreateListener, OrderChangeListener {
	private RelativeLayout orderListView = null;
	private FrameLayout controlPanelView = null;

	public OrderView(Context context) {
		super(context);
		initView();
	}

	public OrderView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();
	}

	private void initView() {
		LayoutInflater li = LayoutInflater.from(this.getContext());

		// Add order list view
		if (orderListView == null) {
			orderListView = (RelativeLayout) li.inflate(R.layout.orderlistview,
					this, false);
			final View leftContentPane = orderListView
					.findViewById(R.id.leftContentPane);
			findViewById(R.id.orderView).setOnTouchListener(
					new OnTouchListener() {
						float x1;

						@Override
						public boolean onTouch(View view, MotionEvent event) {
							switch (event.getAction()) {
							case MotionEvent.ACTION_DOWN: {
								x1 = event.getX();
								break;
							}
							case MotionEvent.ACTION_MOVE: {
								leftContentPane.setVisibility(View.VISIBLE);
								LinearLayout.LayoutParams p = (LinearLayout.LayoutParams) leftContentPane
										.getLayoutParams();
								p.width = (int) event.getX();
								leftContentPane.setLayoutParams(p);
								break;
							}

							case MotionEvent.ACTION_UP: {
								if (event.getX() > x1) {
									leftContentPane.setVisibility(View.VISIBLE);
									LinearLayout.LayoutParams p = (LinearLayout.LayoutParams) leftContentPane
											.getLayoutParams();
									p.width = 300;
									leftContentPane.setLayoutParams(p);
								} else {
									leftContentPane.setVisibility(View.GONE);
								}

								break;
							}

							}

							return true;
						}
					});
			orderListView.findViewById(R.id.btnSendOrder).setOnClickListener(
					new OnClickListener() {
						@Override
						public void onClick(View view) {
							sendOrder(OrderMgr.currentOrder());
						}
					});

			this.addView(orderListView);
		}

		// Add control panel view
		if (controlPanelView == null) {
			controlPanelView = (FrameLayout) li.inflate(R.layout.controlpanel,
					this, false);
			((TextView) controlPanelView.findViewById(R.id.orderCount))
					.setText("0");
			this.addView(controlPanelView);
		}

		MenuEventDispatcher.registerItemOrderListener(this);
		OrderMgr.registerOrderCreateListener(this);
	}

	private void sendOrder(final Order order) {
		final OrderResource oResource = GlobalSettings.instance().getClient()
				.getResource(OrderResource.class);

		new WebTask<com.df.client.rs.model.Order>() {
			@Override
			protected com.df.client.rs.model.Order doInBackground(
					String... arg0) {
				try {
					com.df.client.rs.model.Order o2 = new com.df.client.rs.model.Order();
					for(OrderLine line : order.getLines()) {
						com.df.client.rs.model.OrderLine sLine = new com.df.client.rs.model.OrderLine(line.getItem().getCode());
						sLine.setPrice(line.getPrice());
						sLine.setQuantity(line.getQuantity());
						o2.getLines().add(sLine);
					}
					com.df.client.rs.model.Order o3 = oResource.createOrder(o2);
					Log.d(getClass().getName(), "Return order: " + o3);
					return o3;
				} catch (Exception ex) {
					Log.e(getClass().getName(), "Fail to create order due to "
							+ ex);
				}
				OrderMgr.clearCurrentOrder();
				return null;
			}

			@Override
			protected void onPostExecute(com.df.client.rs.model.Order o) {
				clearOrderView();
			}
		}.execute();

	}

	private void clearOrderView() {
		final View leftContentPane = findViewById(R.id.leftContentPane);
		if (leftContentPane.getVisibility() == View.VISIBLE)
			leftContentPane.setVisibility(View.GONE);

		((TextView) findViewById(R.id.orderCount)).setText("0");
		final ListView lstOrder = (ListView) orderListView
				.findViewById(R.id.lstOrder);
		((OrderListViewAdapter)lstOrder.getAdapter()).clear();
	}

	@Override
	public void onLineAdded(OrderLine line) {
		Log.d(getClass().getName(), "Line " + line + " added");
		((TextView) findViewById(R.id.orderCount)).setText(""
				+ OrderMgr.currentOrder().getItemCount());
		((TextView) findViewById(R.id.orderTotal)).setText(""
				+ OrderMgr.currentOrder().getTotalPayment());
	}

	@Override
	public void onLineRemoved(OrderLine line) {
		((TextView) findViewById(R.id.orderCount)).setText(""
				+ OrderMgr.currentOrder().getItemCount());
		((TextView) findViewById(R.id.orderTotal)).setText(""
				+ OrderMgr.currentOrder().getTotalPayment());
	}

	@Override
	public void onOrderCreated(Order order) {
		Log.d(getClass().getName(), "onOrderCreated");

		OrderMgr.registerChangeListener(this);

		final ListView lstOrder = (ListView) orderListView
				.findViewById(R.id.lstOrder);
		OrderListViewAdapter orderAdapter = new OrderListViewAdapter(
				this.getContext(), order);
		OrderMgr.registerChangeListener(orderAdapter);

		lstOrder.setAdapter(orderAdapter);

		((TextView) orderListView.findViewById(R.id.orderId)).setText(""
				+ order.getId());
		// ((TextView) orderListView.findViewById(R.id.headCount)).setText("" +
		// order.getHeadCount());
		// if(order.getTable() != null)
		// ((TextView)
		// orderListView.findViewById(R.id.tableId)).setText(order.get);
	}

	@Override
	public void onItemOrdered(final Item item) {
		Order order = OrderMgr.currentOrder();
		if (order == null) {
			Log.d(getClass().getName(), "Creating a new order");
			order = OrderMgr.createOrder();
		}

		OrderLine line = new OrderLine(item);
		line.setTableNumber("001");
		line.setPrice(item.getPrice());
		OrderMgr.addOrderLine(order, line);
	}
}
