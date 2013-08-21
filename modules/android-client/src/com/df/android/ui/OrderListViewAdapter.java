package com.df.android.ui;

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
		// List<LVOrderLineCategory> categories = null;
		// if(order instanceof OnsiteOrder) {
		// lvOrder = new LVOnsiteOrder();
		// }else{
		// lvOrder = new LVOffsiteOrder();
		// categories = ((LVOffsiteOrder) lvOrder).getCategories();
		// }

		lvOrder = new LVOrder();
		for (OrderLine line : order.getLines()) {
			lvOrder.addItem(new LVOrderLine(line));
			// if (line instanceof OnsiteOrderLine) {
			// LVOrderTable lvTable = new LVOrderTable(
			// ((OnsiteOrderLine) line).getTable());
			// if (!((LVOnsiteOrder) lvOrder).getTables().contains(lvTable)) {
			// ((LVOnsiteOrder) lvOrder).getTables().add(lvTable);
			// } else {
			// lvTable = ((LVOnsiteOrder) lvOrder).getTables().get(
			// ((LVOnsiteOrder) lvOrder).getTables().indexOf(
			// lvTable));
			// }
			// categories = lvTable.getCategories();
			// }

			// boolean noCategory = true;
			// for (LVOrderLineCategory category : categories) {
			// Item item = line.getItem();
			// if (item.getCategories().contains(category.getCategory())) {
			// category.addLine(new LVOrderLine(line));
			// noCategory = false;
			// }
			//
			// if (noCategory
			// && category.getCategory().equals(ItemCategory.Other))
			// category.addLine(new LVOrderLine(line));
			// }
		}
	}

	@Override
	public int getCount() {
		return lvOrder.getCount();
	}

	@Override
	public Object getItem(int position) {
		return lvOrder.getItems().get(position);
//		int pos = position;
//		// if (lvOrder instanceof LVOnsiteOrder) {
//		List<LVOrderTable> tables = ((LVOnsiteOrder) lvOrder).getTables();
//		for (LVOrderTable table : tables) {
//			if (pos == 0)
//				return table;
//			pos--;
//			for (LVOrderLineCategory category : table.getCategories()) {
//				if (pos == 0)
//					return category;
//
//				pos--;
//				if (pos >= category.getLines().size()) {
//					pos -= category.getLines().size();
//				} else {
//					return category.getLines().get(pos);
//				}
//			}
//		}
//		// }
//
//		return null;
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
//		if (lvItem instanceof LVOrderTable) {
//			if (convertView != null
//					&& convertView.getTag() instanceof LVOrderTable) {
//				view = convertView;
//			} else {
//				view = li.inflate(R.layout.ordertable, null);
//			}
//
//			TextView tvTable = (TextView) view.findViewById(R.id.tvTable);
//			tvTable.setText(((LVOrderTable) lvItem).getTable().getId());
//			((TextView) view.findViewById(R.id.tvTotal)).setText(""
//					+ ((LVOrderTable) lvItem).getTotal());
//		} else if (lvItem instanceof LVOrderLineCategory) {
//			if (convertView != null
//					&& convertView.getTag() instanceof LVOrderLineCategory) {
//				view = convertView;
//			} else {
//				view = li.inflate(R.layout.orderlinecategory, null);
//			}
//
//			TextView tvOrderLineCategory = (TextView) view
//					.findViewById(R.id.tvOrderLineCategory);
//			tvOrderLineCategory.setText(((LVOrderLineCategory) lvItem)
//					.getCategory().toString());
//		} else 
		if (lvItem instanceof LVOrderLine) {
			if (convertView != null){
//					&& convertView.getTag() instanceof LVOrderLine) {
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

	// private class LVOnsiteOrder extends LVOrder {
	// private List<LVOrderTable> tables = new ArrayList<LVOrderTable>();
	//
	// public List<LVOrderTable> getTables() {
	// return tables;
	// }
	//
	// public int getCount() {
	// int count = 0;
	// for (LVOrderTable table : getTables()) {
	// count++;
	// for (LVOrderLineCategory category : table.getCategories()) {
	// count += category.getLines().size() + 1; // Additional one
	// // for category
	// }
	// }
	//
	// return count;
	// }
	//
	// public String toString() {
	// String ret = "LVOrder{\n";
	//
	// ret += "tables:" + "{\n";
	// for (LVOrderTable table : tables)
	// ret += table + "\n";
	// ret += "}\n";
	// ret += "}\n";
	//
	// return ret;
	// }
	// }
	//
	// private class LVOffsiteOrder extends LVOrder {
	// private List<LVOrderLineCategory> categories = new
	// ArrayList<LVOrderLineCategory>();
	//
	// public LVOffsiteOrder() {
	// for (int i = 0; i < DEF_CATEGORIES.length; i++)
	// categories.add(new LVOrderLineCategory(DEF_CATEGORIES[i]));
	//
	// categories.add(new LVOrderLineCategory(ItemCategory.Other));
	// }
	//
	// public int getCount() {
	// int count = 0;
	// for (LVOrderLineCategory category : categories) {
	// count += category.getLines().size() + 1; // Additional one for
	// // category
	// }
	//
	// return count;
	// }
	//
	// public List<LVOrderLineCategory> getCategories() {
	// return categories;
	// }
	// }

	class LVOrderLine extends LVItem {
		private OrderLine line;

		public OrderLine getLine() {
			return line;
		}

		public LVOrderLine(OrderLine line) {
			this.line = line;
		}

		public float getTotal() {
			return line.getTotal();
		}

		public String toString() {
			String ret = "LVOrderLine{\n";

			ret += "line:" + line + "\n";
			ret += "}\n";

			return ret;
		}

		@Override
		public int compare(LVItem another) {
			return line.compare(((LVOrderLine)another).getLine());
		}
	}

//	private class LVOrderTable extends LVItem {
//		private Table table;
//
//		public LVOrderTable(Table table) {
//			this.table = table;
//			for (int i = 0; i < DEF_CATEGORIES.length; i++)
//				categories.add(new LVOrderLineCategory(DEF_CATEGORIES[i]));
//
//			categories.add(new LVOrderLineCategory(ItemCategory.Other));
//		}
//
//		private List<LVOrderLineCategory> categories = new ArrayList<LVOrderLineCategory>();
//
//		public Table getTable() {
//			return table;
//		}
//
//		public float getTotal() {
//			float total = 0.0f;
//			for (LVOrderLineCategory category : categories)
//				total += category.getTotal();
//
//			return total;
//		}
//
//		public List<LVOrderLineCategory> getCategories() {
//			return categories;
//		}
//
//		@Override
//		public boolean equals(Object another) {
//			return table.equals(((LVOrderTable) another).getTable());
//		}
//
//		public String toString() {
//			String ret = "LVOrderTable{\n";
//
//			ret += "table:" + table + "\n";
//			ret += "categories:" + "{\n";
//			for (LVOrderLineCategory category : categories)
//				ret += category + "\n";
//			ret += "}\n";
//			ret += "}\n";
//
//			return ret;
//		}
//	}

//	private class LVOrderLineCategory extends LVItem {
//		private ItemCategory category;
//
//		public ItemCategory getCategory() {
//			return category;
//		}
//
//		public List<LVOrderLine> getLines() {
//			return lines;
//		}
//
//		public LVOrderLineCategory(ItemCategory category) {
//			this.category = category;
//		}
//
//		private List<LVOrderLine> lines = new ArrayList<LVOrderLine>();
//
//		public void addLine(LVOrderLine line) {
//			lines.add(line);
//		}
//
//		public float getTotal() {
//			float total = 0.0f;
//			for (LVOrderLine line : lines)
//				total += line.getTotal();
//
//			return total;
//		}
//
//		@Override
//		public boolean equals(Object another) {
//			return category.equals(((LVOrderLineCategory) another)
//					.getCategory());
//		}
//
//		public String toString() {
//			String ret = "LVOrderLineCategory{\n";
//
//			ret += "category:" + category + ",\n";
//			ret += "lines:" + "{\n";
//			for (LVOrderLine line : lines)
//				ret += line + "\n";
//			ret += "}\n";
//			ret += "}\n";
//
//			return ret;
//		}
//	}

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
