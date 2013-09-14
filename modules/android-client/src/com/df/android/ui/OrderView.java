package com.df.android.ui;

import java.util.UUID;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
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
import android.widget.Toast;

import com.df.android.R;
import com.df.android.entity.Item;
import com.df.android.entity.Shop;
import com.df.android.entity.Table;
import com.df.android.menu.ItemOrderListener;
import com.df.android.menu.MenuEventDispatcher;
import com.df.android.order.Order;
import com.df.android.order.OrderChangeListener;
import com.df.android.order.OrderCreateListener;
import com.df.android.order.OrderFactory;
import com.df.android.order.OrderLine;

public class OrderView extends LinearLayout implements ItemOrderListener,
		OrderCreateListener, OrderChangeListener {
	private RelativeLayout orderListView = null;
	private FrameLayout controlPanelView = null;
	private Shop shop;

	public OrderView(Context context) {
		super(context);
		initView();
	}

	public OrderView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();
	}

	public void setShop(final Shop shop) {
		this.shop = shop;
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
							sendOrder(OrderFactory.currentOrder());
							OrderFactory.clearCurrentOrder();
						}
					});

			this.addView(orderListView);
		}

		// Add control panel view
		if (controlPanelView == null) {
			controlPanelView = (FrameLayout) li.inflate(R.layout.controlpanel,
					this, false);
			((TextView) controlPanelView.findViewById(R.id.orderCount)).setText("0");
			this.addView(controlPanelView);
		}

		MenuEventDispatcher.registerItemOrderListener(this);
		OrderFactory.registerOrderCreateListener(this);
	}

	private void sendOrder(final Order order) {
		final View leftContentPane = findViewById(R.id.leftContentPane);
		if (leftContentPane.getVisibility() == View.VISIBLE)
			leftContentPane.setVisibility(View.GONE);

		((TextView) findViewById(R.id.orderCount)).setText("0");
	}

	@Override
	public void onLineAdded(OrderLine line) {
		Log.d(getClass().getName(), "Line " + line + " added");
		((TextView) findViewById(R.id.orderCount)).setText(""
				+ OrderFactory.currentOrder().getItemCount());
		((TextView) findViewById(R.id.orderTotal)).setText(""
				+ OrderFactory.currentOrder().getTotal());
	}

	@Override
	public void onLineRemoved(OrderLine line) {
		((TextView) findViewById(R.id.orderCount)).setText(""
				+ OrderFactory.currentOrder().getItemCount());
		((TextView) findViewById(R.id.orderTotal)).setText(""
				+ OrderFactory.currentOrder().getTotal());
	}

	@Override
	public void onOrderCreated(Order order) {
		Log.d(getClass().getName(), "onOrderCreated");

		order.registerChangeListener(this);

		final ListView lstOrder = (ListView) orderListView
				.findViewById(R.id.lstOrder);
		OrderListViewAdapter orderAdapter = new OrderListViewAdapter(
				this.getContext(), order);
		order.registerChangeListener(orderAdapter);

		lstOrder.setAdapter(orderAdapter);

		((TextView) orderListView.findViewById(R.id.orderId)).setText(order
				.getId());
	}

	@Override
	public void onItemOrdered(final Item item) {
		if (OrderFactory.currentOrder() != null) {
			OrderLine line = new OrderLine(item);
			line.setTable(OrderFactory.currentOrder().getTable());
			OrderFactory.currentOrder().addLine(line);
			return;
		}

		final Context cxt = this.getContext();
		final CreateOrderDialog chooseTableDialog = new CreateOrderDialog(cxt,
				shop);
		chooseTableDialog.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss(DialogInterface dialog) {
				final Table table = chooseTableDialog.getSelectedTable();
				if (table == null) {
					Toast.makeText(
							cxt,
							cxt.getResources()
									.getString(
											cxt.getResources()
													.getIdentifier(
															cxt.getPackageName()
																	+ ":string/user_cancel_create_order",
															null, null)),
							Toast.LENGTH_LONG).show();
					return;
				}

				Log.d(getClass().getName(), "Creating a new order");

				Order order = OrderFactory.createOnsiteOrder(UUID.randomUUID()
						.toString().substring(0, 10), table,
						chooseTableDialog.getHeadCount());
				
				OrderLine line = new OrderLine(item);
				line.setTable(table);
				order.addLine(line);
			}

		});
		chooseTableDialog.show();
	}
}
