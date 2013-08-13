package com.df.android;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MenuAdapter extends BaseAdapter
{
    private Shop shop;
    private Context context;
    private List<MenuItem> items = new ArrayList<MenuItem>();
    private MenuItem.MenuItemType menuItemType;
 
    public MenuAdapter(Context context, Shop shop, MenuItem.MenuItemType type) {
        super();
        this.shop = shop;
        this.context = context;
        this.menuItemType = type;
        
        if(type == MenuItem.MenuItemType.MIT_ALL)
        	items = shop.getMenu().getItems();
        else {
	        for(MenuItem item : shop.getMenu().getItems())
	        	if(item.getType() == type)
	        		items.add(item);
        }
    }
    
    public MenuItem.MenuItemType getMenuItemType() {
    	return menuItemType;
    }
    
    @Override
    public int getCount() {
        return items.size();
    }
 
    @Override
    public MenuItem getItem(int position) {
        return items.get(position);
    }
 
    @Override
    public long getItemId(int position) {
        return 0;
    }
 
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
 
        View view = convertView; 
        if(view == null)
            view = inflator.inflate(R.layout.menuitem, null);
        
        final TextView tvName = (TextView) view.findViewById(R.id.menuItemName);
        final ImageView ivImage = (ImageView) view.findViewById(R.id.menuItemImage);
        final TextView tvPrice = (TextView) view.findViewById(R.id.menuItemPrice);
        
        MenuItem item = items.get(position);
        tvName.setText(item.getName());
        tvPrice.setText("" + item.getPrice());
        String imageFile = item.getImage();
        try {
        	ivImage.setImageBitmap(BitmapFactory.decodeStream(context.getAssets().open("cache/" + shop.getId() + "/" + imageFile)));
		} catch (IOException e) {
			Log.e(getClass().getName(), "Fail to load image file '" + imageFile + "'");
		}
 
        return view;
    }
}