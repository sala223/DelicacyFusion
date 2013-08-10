package com.df.android;

import java.io.IOException;

import android.app.Activity;
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
    private Activity activity;
 
    public MenuAdapter(Activity activity, Shop shop) {
        super();
        this.shop = shop;
        this.activity = activity;
    }
 
    @Override
    public int getCount() {
        return shop.getMenu().getItems().size();
    }
 
    @Override
    public MenuItem getItem(int position) {
        return shop.getMenu().getItems().get(position);
    }
 
    @Override
    public long getItemId(int position) {
        return 0;
    }
 
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflator = activity.getLayoutInflater();
 
        View view = convertView;
        if(view == null)
            view = inflator.inflate(R.layout.menuitem, null);
        
        final TextView tvName = (TextView) view.findViewById(R.id.menuItemName);
        final ImageView ivImage = (ImageView) view.findViewById(R.id.menuItemImage);
        final TextView tvPrice = (TextView) view.findViewById(R.id.menuItemPrice);
        
        tvName.setText(shop.getMenu().getItems().get(position).getName());
        tvPrice.setText("" + shop.getMenu().getItems().get(position).getPrice());
        String imageFile = shop.getMenu().getItems().get(position).getImage();
        try {
        	ivImage.setImageBitmap(BitmapFactory.decodeStream(activity.getAssets().open("cache/" + shop.getId() + "/" + imageFile)));
		} catch (IOException e) {
			Log.e(getClass().getName(), "Fail to load image file '" + imageFile + "'");
		}
 
        return view;
    }
}