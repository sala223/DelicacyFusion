package com.df.android.ui;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.df.android.R;
import com.df.android.order.Order;
import com.df.android.order.OrderChangeListener;
import com.df.android.order.OrderLine;
import com.df.android.order.OrderMgr;

public class OrderListViewAdapter extends BaseAdapter implements
		OrderChangeListener {
	private Context context;
	private Order order;
	private LVOrder lvOrder;

	public OrderListViewAdapter(Context context, Order order) {
		this.context = context;
		this.order = order;
		rebuildList();
	}

	private void rebuildList() {
		if (order == null || order.getLines() == null)
			return;

		lvOrder = new LVOrder();
		for (OrderLine line : order.getLines()) {
			lvOrder.addItem(new LVOrderLine(line));
		}
	}

	@Override
	public int getCount() {
		return lvOrder.getCount();
	}

	@Override
	public Object getItem(int position) {
		return lvOrder.getItems().get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parentView) {
		View view = null;
		LayoutInflater li = LayoutInflater.from(context);

		LVItem lvItem = (LVItem) getItem(position);
		if (lvItem instanceof LVOrderLine) {
			if (convertView != null) {
				view = convertView;
			} else {
				view = li.inflate(R.layout.orderitem, null);
			}

			final TextView tvName = (TextView) view
					.findViewById(R.id.menuItemName);
			final TextView tvPrice = (TextView) view
					.findViewById(R.id.menuItemPrice);
			final TextView tvCopies = (TextView) view
					.findViewById(R.id.menuItemCopies);
			view.findViewById(R.id.menuItemRemove)
					.setVisibility(View.INVISIBLE);

			final OrderLine line = ((LVOrderLine) lvItem).getLine();
			tvName.setText(line.getItem().getName());
			tvPrice.setText("" + line.getPrice());
			tvCopies.setText("x" + line.getQuantity());

			view.setOnTouchListener(new OnTouchListener() {
				@Override
				public boolean onTouch(View view, MotionEvent event) {
					// if(event.getAction() == MotionEvent.ACTION_MOVE) {
					ImageButton btnDelete = (ImageButton) view
							.findViewById(R.id.menuItemRemove);
					btnDelete.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View view) {
							OrderMgr.removeOrderLine(order, line);
						}

					});
					btnDelete.setVisibility(View.VISIBLE);
					// }

					return false;
				}

			});
		}

		return view;
	}
	
	public void clear() {
		order = null;
		rebuildList();
	}

	abstract class LVItem {
		abstract public int compare(LVItem another);
	}

	class LVOrder {
		private List<LVItem> items = new ArrayList<LVItem>();

		public void addItem(LVItem lvItem) {
			if (lvItem instanceof LVOrderLine) {
				LVOrderLine another = (LVOrderLine) lvItem;
				int i = 0;
				for (LVItem item : items) {
					if (item.compare(another) > 0)
						break;

					i++;
				}

				items.add(i, lvItem);
			}
		}

		public List<LVItem> getItems() {
			return items;
		}

		int getCount() {
			return items.size();
		}
	}

	class LVOrderLine extends LVItem {
		private OrderLine line;

		public OrderLine getLine() {
			return line;
		}

		public LVOrderLine(OrderLine line) {
			this.line = line;
		}

		public BigDecimal getTotal() {
			return line.getTotalPayment();
		}

		public String toString() {
			String ret = "LVOrderLine{\n";

			ret += "line:" + line + "\n";
			ret += "}\n";

			return ret;
		}

		@Override
		public int compare(LVItem another) {
			return 0;
		}
	}

	@Override
	public void onLineAdded(OrderLine line) {
		rebuildList();
		notifyDataSetChanged();
	}

	@Override
	public void onLineRemoved(OrderLine line) {
		rebuildList();
		notifyDataSetChanged();
	}
}
