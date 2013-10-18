package com.df.android.entity.test;

import junit.framework.TestCase;

import org.junit.Test;

import android.util.Log;

import com.df.android.entity.Dish;
import com.df.android.entity.Item;
import com.df.android.order.OnsiteOrder;
import com.df.android.order.Order;
import com.df.android.order.OrderLine;

public class OrderTest extends TestCase {
	@Test
	public void testCreate() {
		Order order = new OnsiteOrder("001");
		
		for(int i = 0; i < 10; i ++) {
			Item item = new Dish("" + i, "Dish " + i);
			order.addLine(new OrderLine(item, 10.00f*(i+1)));
		}
		
		Log.d(getClass().getName(), order.toString());
	}
	
}
