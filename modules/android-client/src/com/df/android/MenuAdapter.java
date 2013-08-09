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
 
    public static class ViewHolder
    {
        public ImageView menuItemImage;
        public TextView menuItemInfo;
    }
 
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder view;
        LayoutInflater inflator = activity.getLayoutInflater();
 
        if(convertView==null)
        {
            view = new ViewHolder();
            convertView = inflator.inflate(R.layout.menuitem, null);
 
            view.menuItemImage = (ImageView) convertView.findViewById(R.id.menuItemImage);
            view.menuItemInfo = (TextView) convertView.findViewById(R.id.menuItemInfo);
 
            convertView.setTag(view);
        }
        else
        {
            view = (ViewHolder) convertView.getTag();
        }
 
        view.menuItemInfo.setText(shop.getMenu().getItems().get(position).getName());
        String imageFile = shop.getMenu().getItems().get(position).getImage();
        try {
			view.menuItemImage.setImageBitmap(BitmapFactory.decodeStream(activity.getAssets().open("cache/" + shop.getId() + "/" + imageFile)));
		} catch (IOException e) {
			Log.e("MenuAdapter", "Fail to load image file '" + imageFile + "'");
		}
 
        return convertView;
    }
}