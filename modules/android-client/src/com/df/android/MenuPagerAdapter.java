package com.df.android;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MenuPagerAdapter extends PagerAdapter {
    Context context;
    Shop shop;
 
    public MenuPagerAdapter(Context context, Shop shop) {
        this.context = context;
        this.shop = shop;
    }

    @Override
    public int getCount() {
        return MenuItem.dishTypes.length + 1; // First one is all
    }
 
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }
 
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
    	Log.d(getClass().getName(), "Current page: " + position);
    	
    	LayoutInflater inflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflator.inflate(R.layout.menupage, container, false);
 
        GridView gvMenu = (GridView)itemView.findViewById(R.id.gvMenu);
		MenuItem.MenuItemType menuItemType = MenuItem.MenuItemType.MIT_ALL; 
		if(position > 0)  
			menuItemType = MenuItem.dishTypes[position-1];

        gvMenu.setAdapter(new MenuAdapter(context, shop, menuItemType));
        
        TextView tvPrevCategory = (TextView)itemView.findViewById(R.id.tvPrevCategory);
        ImageView imgPrevCategory = (ImageView)itemView.findViewById(R.id.imgPrevCategory);
        if(position > 0) {
        	String prev = getMenuItemTypeName(position == 1 ? MenuItem.MenuItemType.MIT_ALL : MenuItem.dishTypes[position-2]);
        	tvPrevCategory.setText(prev);
        	tvPrevCategory.setVisibility(View.VISIBLE);
        	imgPrevCategory.setVisibility(View.VISIBLE);
        }
        else {
        	tvPrevCategory.setVisibility(View.INVISIBLE);
        	imgPrevCategory.setVisibility(View.INVISIBLE);
        }

        ((TextView)itemView.findViewById(R.id.tvCategory)).setText("" + getMenuItemTypeName(menuItemType));

        TextView tvNextCategory = (TextView)itemView.findViewById(R.id.tvNextCategory);
        ImageView imgNextCategory = (ImageView)itemView.findViewById(R.id.imgNextCategory);
        if(position < getCount() - 1) {
        	String next = getMenuItemTypeName(MenuItem.dishTypes[position]);
        	tvNextCategory.setText(next); 
        	tvNextCategory.setVisibility(View.VISIBLE);
        	imgNextCategory.setVisibility(View.VISIBLE);
        }
        else {
        	tvNextCategory.setVisibility(View.INVISIBLE);
        	imgNextCategory.setVisibility(View.INVISIBLE);
        }
        
        container.addView(itemView);
 
        return itemView;
    }
 
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout)object);
 
    }
    
    private String getMenuItemTypeName(MenuItem.MenuItemType type) {
    	String name = "";
    	
    	switch(type) {
    	case MIT_ALL:
    		name = "全部";
    		break;
    	case MIT_COLDDISH:
    		name = "凉菜";
    		break;
    	case MIT_HOTDISH:
    		name = "热菜";
    		break;
    	case MIT_DRINK:
    		name = "酒水饮料";
    		break;
    	case MIT_DESERT:
    		name = "甜点";
    		break;
		}
    	
    	return name;
    }
    
}

