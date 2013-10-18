package com.df.android.menu;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.df.android.R;
import com.df.android.entity.Shop;

public class MenuPagerAdapter extends PagerAdapter {
    Context context;
    Shop shop;
    
    public MenuPagerAdapter(Context context, Shop shop) {
        this.context = context;
        this.shop = shop;
    }

    @Override
    public int getCount() {
        return shop.getNavigatableMenuItemCategories().size();
    }
 
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }
 
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
    	LayoutInflater inflator = LayoutInflater.from(context);
        View itemView = inflator.inflate(R.layout.menupage, container, false);
 
        GridView gvMenu = (GridView)itemView.findViewById(R.id.gvMenu);
        gvMenu.setAdapter(new MenuAdapter(context, shop, shop.getNavigatableMenuItemCategories().get(position)));

        container.addView(itemView);
 
        return itemView;
    }
 
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout)object);
 
    }
}

