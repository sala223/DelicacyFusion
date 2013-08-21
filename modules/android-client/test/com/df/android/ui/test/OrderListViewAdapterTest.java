package com.df.android.ui.test;

import junit.framework.TestCase;

import org.junit.Test;

import com.df.android.entity.Dish;
import com.df.android.entity.Item;
import com.df.android.entity.ItemCategory;
import com.df.android.entity.Table;
import com.df.android.order.OnsiteOrder;
import com.df.android.order.Order;

public class OrderListViewAdapterTest extends TestCase {
	@Test
	public void testCreate() {
		Order order = new OnsiteOrder("001");
		
		Table table = new Table("T001");
		for(int i = 0; i < 10; i ++) {
			Item item = new Dish("" + i, "Dish " + i);
			item.addCategory(ItemCategory.HotDish);
//			order.addLine(new OnsiteOrderLine(item, 10.00f*(i+1), table));
		}
		
		for(int i = 10; i < 15; i ++) {
			Item item = new Dish("" + i, "Dish " + i);
			item.addCategory(ItemCategory.CoolDish);
//			order.addLine(new OnsiteOrderLine(item, 10.00f*(i+1), table));
		}
		
		for(int i = 15; i < 18; i ++) {
			Item item = new Dish("" + i, "Dish " + i);
			item.addCategory(ItemCategory.Desert);
//			order.addLine(new OnsiteOrderLine(item, 10.00f*(i+1), table));
		}
		
//		Log.d(getClass().getName(), order.toString());
		
//		OrderListViewAdapter adapter = new OrderListViewAdapter(order);
	}
	
}
