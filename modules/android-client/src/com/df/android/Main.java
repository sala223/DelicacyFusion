package com.df.android;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.df.android.MenuItem.MenuItemType;

public class Main extends Activity implements OrderChangeListener { 
    @Override  
    protected void onCreate(Bundle savedInstanceState) { 
        super.onCreate(savedInstanceState); 
        setContentView(R.layout.main);  

        Shop shop = initShop("demo");  
        
        ViewPager viewPager = (ViewPager) findViewById(R.id.menuPager);
        viewPager.setAdapter(new MenuPagerAdapter(this, shop));
        
        ((Button) findViewById(R.id.btnCreateOrder)).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
		        Order order = new Order("Table 1");
		        order.registerChangeListener(Main.this);

		        final ListView lstOrder = (ListView)findViewById(R.id.lstOrder);
		        OrderAdapter orderAdapter = new OrderAdapter(Main.this);
		        orderAdapter.setOrder(order);
		        lstOrder.setAdapter(orderAdapter);
		        
		        ((TextView)findViewById(R.id.orderId)).setText(order.getId());
			}
        	
        });
        
        findViewById(R.id.openClose).setOnClickListener(new OnClickListener()
        {  
			@Override
			public void onClick(View arg0) {
            	LinearLayout leftPane = (LinearLayout)findViewById(R.id.leftPane);
            	
				if(leftPane.getVisibility() == View.VISIBLE) {
					leftPane.setVisibility(View.GONE);
				}else {
					leftPane.setVisibility(View.VISIBLE);
				}
			}
        });
        
    }
    
    private Shop initShop(String shopId) {
    	Shop shop = new Shop(shopId, shopId);
    	
        String menuMetaFile = "cache/" + shopId + "/menu.xml";
        Menu menu = buildMenuFromMetaFile(menuMetaFile);
	        
        shop.setMenu(menu);
		
		return shop;
    }
    
    private Menu buildMenuFromMetaFile(String metaFile) {
    	Menu menu = new Menu();
    	
    	Log.i(getClass().getName(), "Building menu from " + metaFile);
    	
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document dom = builder.parse(this.getAssets().open(metaFile));
	        Element root = dom.getDocumentElement();
            NodeList xmlItems = root.getElementsByTagName("item");

        	Log.d(getClass().getName(), "Found " + xmlItems.getLength() + " items");
            
            for (int i=0; i<xmlItems.getLength(); i++){
            	Element xmlItem = (Element)xmlItems.item(i);
            	String name = xmlItem.getAttribute("name");
            	MenuItemType type = MenuItem.dishTypes[Integer.parseInt(xmlItem.getAttribute("type"))];
            	String image = xmlItem.getAttribute("image");
            	float price = Float.parseFloat(xmlItem.getAttribute("price"));
            	Log.i(getClass().getName(), "Found item: name=" + name + ", type=" + type + ", image=" + image);
            	
            	MenuItem item = new MenuItem(name, type, price, image);
            	menu.addItem(item);
            }
		} catch (Exception e) {
			Log.e(getClass().getName(), "Fail to load menu from meta file due to " + e);
		}
		
		return menu;
    }

    @Override
	public void onMenuItemAdded(MenuItem item) {
    	((OrderAdapter)((ListView)findViewById(R.id.lstOrder)).getAdapter()).notifyDataSetChanged();
    	((TextView)findViewById(R.id.orderCount)).setText("" + Order.currentOrder().getCount());
    } 
    

	@Override
	public void onMenuItemRemoved(MenuItem item) { 
    	((OrderAdapter)((ListView)findViewById(R.id.lstOrder)).getAdapter()).notifyDataSetChanged();
    	((TextView)findViewById(R.id.orderCount)).setText("" + Order.currentOrder().getCount());
	}
}


