package com.df.android;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

public class OrderAdapter extends BaseAdapter {
	Activity activity;
	Order order;

	public OrderAdapter(Activity activity) {
		this.activity = activity;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	@Override
	public int getCount() {
		return order.getDistinctCount();
	}

	@Override
	public Object getItem(int index) {
		return order.getItem(index);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflator = activity.getLayoutInflater();

		View view = convertView;
		if (view == null)
			view = inflator.inflate(R.layout.orderitem, null);

		final TextView tvName = (TextView) view.findViewById(R.id.menuItemName);
		final TextView tvPrice = (TextView) view
				.findViewById(R.id.menuItemPrice);
		final TextView tvCopies = (TextView) view
				.findViewById(R.id.menuItemCopies);
		view.findViewById(R.id.menuItemRemove).setVisibility(View.INVISIBLE);

		final Order.MenuItemOrder item = (Order.MenuItemOrder) getItem(position);

		tvName.setText(item.getItem().getName());
		tvPrice.setText("" + item.getItem().getPrice());
		tvCopies.setText("x" + item.getCopies());

		view.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View view, MotionEvent event) {
				// if(event.getAction() == MotionEvent.ACTION_MOVE) {
				ImageButton btnDelete = (ImageButton) view
						.findViewById(R.id.menuItemRemove);
				btnDelete.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View view) {
						order.remove(item);
					}

				});
				btnDelete.setVisibility(View.VISIBLE);
				// }

				return false;
			}

		});

		return view;
	}

	@Override
	public long getItemId(int index) {
		return index;
	}

}
