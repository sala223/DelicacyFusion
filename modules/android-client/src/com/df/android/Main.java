package com.df.android;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.df.android.MenuItem.MenuItemType;

public class Main extends Activity { 
	
    @Override 
    protected void onCreate(Bundle savedInstanceState) { 
        super.onCreate(savedInstanceState); 
        setContentView(R.layout.main);

        Shop shop = initShop("demo");
        
        GridView gvMenu = (GridView) findViewById(R.id.gvMenu); 
		final MenuAdapter menuAdapter = new MenuAdapter(this, shop);
		gvMenu.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int index,
					long arg3) {
				// Toast.makeText(view.getContext(), "You selected item " + index, Toast.LENGTH_SHORT).show();
				MenuItem item = (MenuItem)menuAdapter.getItem(index);
				Order.currentOrder().add(item);
			}
			
		});
        gvMenu.setAdapter(menuAdapter);
        
        ListView lstOrder = (ListView)findViewById(R.id.lstOrder);
        OrderAdapter orderAdapter = new OrderAdapter(this);
        orderAdapter.setOrder(new Order());
        lstOrder.setAdapter(orderAdapter);
        
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
            	Log.i(getClass().getName(), "Found item: name=" + name + ", type=" + type + ", image=" + image);
            	
            	MenuItem item = new MenuItem(name, type, image);
            	menu.addItem(item);
            }
		} catch (Exception e) {
			Log.e(getClass().getName(), "Fail to load menu from meta file due to " + e);
		}
		
		return menu;
    }
}


