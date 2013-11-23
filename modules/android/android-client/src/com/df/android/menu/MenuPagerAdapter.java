package com.df.android.menu;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.df.android.R;
import com.df.android.entity.Store;

public class MenuPagerAdapter extends PagerAdapter {
    Context context;
    Store store;
    
    public MenuPagerAdapter(Context context, Store store) {
        this.context = context;
        this.store = store;
    }

    @Override
    public int getCount() {
        return store.getNavigatableMenuItemCategories().size();
    }
 
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((View) object);
    }
 
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
    	LayoutInflater inflator = LayoutInflater.from(context);
        View itemView = inflator.inflate(R.layout.menupage, container, false);
 
        GridView gvMenu = (GridView)itemView.findViewById(R.id.gvMenu);
        gvMenu.setAdapter(new MenuAdapter(context, store, store.getNavigatableMenuItemCategories().get(position)));

        container.addView(itemView);
 
        return itemView;
    }
 
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
 
    }
}

