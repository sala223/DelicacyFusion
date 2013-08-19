package com.df.android.menu;

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

import com.df.android.R;
import com.df.android.entity.ItemCategory;
import com.df.android.entity.Shop;

public class MenuPagerAdapter extends PagerAdapter {
    Context context;
    Shop shop;
    
    private static final ItemCategory dishTypes[] = {
    	ItemCategory.CoolDish,
    	ItemCategory.HotDish,
    	ItemCategory.Soup,
    	ItemCategory.Desert
    };
 
    public MenuPagerAdapter(Context context, Shop shop) {
        this.context = context;
        this.shop = shop;
    }

    @Override
    public int getCount() {
        return dishTypes.length + 1; // First one is all
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
        ItemCategory menuItemType = ItemCategory.All; 
		if(position > 0)  
			menuItemType = dishTypes[position-1];

        gvMenu.setAdapter(new MenuAdapter(context, shop, menuItemType));
        
        TextView tvPrevCategory = (TextView)itemView.findViewById(R.id.tvPrevCategory);
        ImageView imgPrevCategory = (ImageView)itemView.findViewById(R.id.imgPrevCategory);
        if(position > 0) {
        	String prev = getMenuItemTypeName(position == 1 ? ItemCategory.All : dishTypes[position-2]);
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
        	String next = getMenuItemTypeName(dishTypes[position]);
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
    
    private String getMenuItemTypeName(ItemCategory type) {
    	String name = "";
    	
    	switch(type) {
    	case All:
    		name = "全部";
    		break;
    	case CoolDish:
    		name = "凉菜";
    		break;
    	case HotDish:
    		name = "热菜";
    		break;
    	case Soup:
    		name = "汤";
    		break;
    	case Desert:
    		name = "点心";
    		break;
    	default:
    		name = "其它";
		}
    	
    	return name;
    }
    
}

