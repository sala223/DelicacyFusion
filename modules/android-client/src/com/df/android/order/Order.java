package com.df.android.order;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class Order {
	protected String id;

	public String getId() {
		return id;
	}

	public Date getCreateTime() {
		return createTime;
	}

	protected Date createTime;
	protected List<OrderLine> lines = new ArrayList<OrderLine>();
	private static Order currentOrder;

	public Order(String id) {
		this.id = id;
		setCurrentOrder(this);
	}

	public static void setCurrentOrder(Order order) {
		currentOrder = order;
	}

	public static Order currentOrder() {
		return currentOrder;
	}

	public void addLine(OrderLine line) {
		int index = lines.indexOf(line);
		if(index >= 0) { 
			OrderLine l = lines.get(index);
			if(l != null)
				l.setQuantity(l.getQuantity() + 1);
		}
		else
			lines.add(line);
		
		onLineAdded(line);
	}

	public void removeLine(OrderLine line) {
		int index = lines.indexOf(line);
		if(index < 0)
			return;
		
		OrderLine l = lines.get(index);
		if(l == null || l.getQuantity() <= 1)
			lines.remove(line);
		else
			l.setQuantity(l.getQuantity() - 1);
		
		onLineRemoved(line);
	}

	public float getTotal() {
		float total = 0;
		for (OrderLine line : lines)
			total += line.getTotal();

		return total;
	}

	public int getLineCount() {
		return lines.size();
	}

	public int getItemCount() {
		int count = 0;
		for (OrderLine line : lines)
			count += line.getQuantity();

		return count;
	}

	public List<OrderLine> getLines() {
		return lines;
	}

	private List<OrderChangeListener> changeListeners = new ArrayList<OrderChangeListener>();

	public void registerChangeListener(OrderChangeListener listener) {
		changeListeners.add(listener);
	}

	private void onLineAdded(OrderLine line) {
		for (OrderChangeListener listener : changeListeners) {
			listener.onLineAdded(line);
		}
	}

	private void onLineRemoved(OrderLine line) {
		for (OrderChangeListener listener : changeListeners) {
			listener.onLineRemoved(line);
		}
	}

	public String toString() {
		String ret = "Order{";

		ret += "id:" + id + ",";
		ret += "lines:" + "{";
		for (OrderLine line : lines)
			ret += line;
		ret += "}";
		ret += "}";

		return ret;
	}
}
