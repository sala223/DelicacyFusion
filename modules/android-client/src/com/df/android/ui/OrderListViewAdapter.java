package com.df.android.ui;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;
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
import com.df.android.entity.Item;
import com.df.android.entity.ItemCategory;
import com.df.android.entity.Table;
import com.df.android.order.OnsiteOrderLine;
import com.df.android.order.Order;
import com.df.android.order.OrderChangeListener;
import com.df.android.order.OrderLine;

public class OrderListViewAdapter extends BaseAdapter implements OrderChangeListener{
	private Context context;
	private Order order;
	private LVOrder lvOrder;
	private static ItemCategory DEF_CATEGORIES[] = { ItemCategory.CoolDish,
			ItemCategory.HotDish };

	public OrderListViewAdapter(Context context, Order order) {
		this.context = context;
		this.order = order;
		rebuildList();
	}

	private void rebuildList() {
		List<LVOrderLineCategory> categories = null;
//		if (lvOrder == null) {
			switch (order.getType()) {
			case Onsite:
				lvOrder = new LVOnsiteOrder();
				break;
			case Offsite:
				lvOrder = new LVOffsiteOrder();
				categories = ((LVOffsiteOrder) lvOrder).getCategories();
				break;
			}
//		}

		for (OrderLine line : order.getLines()) {
			if (line instanceof OnsiteOrderLine) {
				LVOrderTable lvTable = new LVOrderTable(
						((OnsiteOrderLine) line).getTable());
				// Log.d(getClass().getName(), "Checking if table " +
				// lvTable.getTable().getId() + " exists");
				if (!((LVOnsiteOrder) lvOrder).getTables().contains(lvTable)) {
					// Log.d(getClass().getName(), "Not exists");
					((LVOnsiteOrder) lvOrder).getTables().add(lvTable);
				} else {
					// Log.d(getClass().getName(), "Exists");
					lvTable = ((LVOnsiteOrder) lvOrder).getTables().get(
							((LVOnsiteOrder) lvOrder).getTables().indexOf(
									lvTable));
				}
				categories = lvTable.getCategories();
			}

			boolean noCategory = true;
			for (LVOrderLineCategory category : categories) {
				Item item = line.getItem();
				if (item.getCategories().contains(category.getCategory())) {
					category.addLine(new LVOrderLine(line));
					noCategory = false;
				}

				if (noCategory
						&& category.getCategory().equals(ItemCategory.Other))
					category.addLine(new LVOrderLine(line));
			}
		}

		Log.d(getClass().getName(), lvOrder.toString());
	}

	@Override
	public int getCount() {
		return lvOrder.getCount();
	}

	@Override
	public Object getItem(int position) {
		int pos = position;
		if (lvOrder instanceof LVOnsiteOrder) {
			List<LVOrderTable> tables = ((LVOnsiteOrder) lvOrder).getTables();
			for (LVOrderTable table : tables) {
				if (pos == 0)
					return table;
				pos--;
				for (LVOrderLineCategory category : table.getCategories()) {
					if (pos == 0)
						return category;

					pos--;
					if (pos >= category.getLines().size()) {
						pos -= category.getLines().size();
					} else {
						return category.getLines().get(pos);
					}
				}
			}
		}

		return null;
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
		if (lvItem instanceof LVOrderTable) {
			if (convertView != null
					&& convertView.getTag() instanceof LVOrderTable) {
				view = convertView;
			} else {
				view = li.inflate(R.layout.ordertable, null);
			}

			TextView tvTable = (TextView) view.findViewById(R.id.tvTable);
			tvTable.setText(((LVOrderTable) lvItem).getTable().getId());
		} else if (lvItem instanceof LVOrderLineCategory) {
			if (convertView != null
					&& convertView.getTag() instanceof LVOrderLineCategory) {
				view = convertView;
			} else {
				view = li.inflate(R.layout.orderlinecategory, null);
			}

			TextView tvOrderLineCategory = (TextView) view
					.findViewById(R.id.tvOrderLineCategory);
			tvOrderLineCategory.setText(((LVOrderLineCategory) lvItem)
					.getCategory().toString());
		} else if (lvItem instanceof LVOrderLine) {
			if (convertView != null
					&& convertView.getTag() instanceof LVOrderLine) {
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

			final OrderLine line = ((LVOrderLine)lvItem).getLine();
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
							order.removeLine(line);
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

	private class LVItem {
	}

	private abstract class LVOrder extends LVItem {
		abstract int getCount();
	}

	private class LVOnsiteOrder extends LVOrder {
		private List<LVOrderTable> tables = new ArrayList<LVOrderTable>();

		public List<LVOrderTable> getTables() {
			return tables;
		}

		public int getCount() {
			int count = 0;
			for (LVOrderTable table : getTables()) {
				count++;
				for (LVOrderLineCategory category : table.getCategories()) {
					count += category.getLines().size() + 1; // Additional one
																// for category
				}
			}

			return count;
		}

		public String toString() {
			String ret = "LVOrder{\n";

			ret += "tables:" + "{\n";
			for (LVOrderTable table : tables)
				ret += table + "\n";
			ret += "}\n";
			ret += "}\n";

			return ret;
		}
	}

	private class LVOffsiteOrder extends LVOrder {
		private List<LVOrderLineCategory> categories = new ArrayList<LVOrderLineCategory>();

		public LVOffsiteOrder() {
			for (int i = 0; i < DEF_CATEGORIES.length; i++)
				categories.add(new LVOrderLineCategory(DEF_CATEGORIES[i]));

			categories.add(new LVOrderLineCategory(ItemCategory.Other));
		}

		public int getCount() {
			int count = 0;
			for (LVOrderLineCategory category : categories) {
				count += category.getLines().size() + 1; // Additional one for
															// category
			}

			return count;
		}

		public List<LVOrderLineCategory> getCategories() {
			return categories;
		}
	}

	private class LVOrderLine extends LVItem {
		private OrderLine line;

		public OrderLine getLine() {
			return line;
		}

		public LVOrderLine(OrderLine line) {
			this.line = line;
		}

		public String toString() {
			String ret = "LVOrderLine{\n";

			ret += "line:" + line + "\n";
			ret += "}\n";

			return ret;
		}
	}

	private class LVOrderTable extends LVItem {
		private Table table;

		public LVOrderTable(Table table) {
			this.table = table;
			for (int i = 0; i < DEF_CATEGORIES.length; i++)
				categories.add(new LVOrderLineCategory(DEF_CATEGORIES[i]));

			categories.add(new LVOrderLineCategory(ItemCategory.Other));
		}

		private List<LVOrderLineCategory> categories = new ArrayList<LVOrderLineCategory>();

		public Table getTable() {
			return table;
		}

		public List<LVOrderLineCategory> getCategories() {
			return categories;
		}

		@Override
		public boolean equals(Object another) {
			// Log.d(getClass().getName(), "comparing " + table + " with " +
			// ((LVOrderTable)another).getTable());
			return table.equals(((LVOrderTable) another).getTable());
		}

		public String toString() {
			String ret = "LVOrderTable{\n";

			ret += "table:" + table + "\n";
			ret += "categories:" + "{\n";
			for (LVOrderLineCategory category : categories)
				ret += category + "\n";
			ret += "}\n";
			ret += "}\n";

			return ret;
		}
	}

	private class LVOrderLineCategory extends LVItem {
		private ItemCategory category;

		public ItemCategory getCategory() {
			return category;
		}

		public List<LVOrderLine> getLines() {
			return lines;
		}

		public LVOrderLineCategory(ItemCategory category) {
			this.category = category;
		}

		private List<LVOrderLine> lines = new ArrayList<LVOrderLine>();

		public void addLine(LVOrderLine line) {
			lines.add(line);
		}

		@Override
		public boolean equals(Object another) {
			return category.equals(((LVOrderLineCategory) another)
					.getCategory());
		}

		public String toString() {
			String ret = "LVOrderLineCategory{\n";

			ret += "category:" + category + ",\n";
			ret += "lines:" + "{\n";
			for (LVOrderLine line : lines)
				ret += line + "\n";
			ret += "}\n";
			ret += "}\n";

			return ret;
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
