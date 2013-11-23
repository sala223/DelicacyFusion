package com.df.android.table;

import java.util.List;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.GridView;

import com.df.android.R;
import com.df.android.entity.Store;
import com.df.android.ui.SettingsDialog;
import com.df.client.rs.model.DiningTable;

public class TableLayoutPagerAdapter extends PagerAdapter {
    Context context;
    Store store;
    
    public TableLayoutPagerAdapter(Context context, Store store) {
        this.context = context;
        this.store = store;
    }

    @Override
    public int getCount() {
        return store.getFloors().size();
    }
 
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((View) object);
    }
 
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
    	LayoutInflater inflator = LayoutInflater.from(context);
        View itemView = inflator.inflate(R.layout.tablelayoutpage, container, false);
 
        GridView gvTables = (GridView)itemView.findViewById(R.id.gvTables);
        List<DiningTable> tables = store.getFloors().get(position).getTables();
        gvTables.setAdapter(new TableAdapter(context, tables));
        gvTables.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> av, View view,
					int position, long arg3) {
				new SettingsDialog(view.getContext()).show();
				return false;
			}
        	
        });

        container.addView(itemView);
 
        return itemView;
    }
 
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
 
    }
}

