package com.df.android.order;

import java.io.IOException;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.df.android.R;
import com.df.android.entity.Shop;
import com.df.android.entity.Table;

public class TableAdapter extends BaseAdapter {
	private Shop shop;
	private Context context;

	public TableAdapter(Context context, Shop shop) {
		super();
		this.shop = shop;
		this.context = context;
	}

	@Override
	public int getCount() {
		return shop.getTables().size();
	}

	@Override
	public Object getItem(int position) {
		return shop.getTables().get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflator = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View view = convertView;
		if (view == null)
			view = inflator.inflate(R.layout.tableitem, null);

		final TextView tvTableId = (TextView) view.findViewById(R.id.tvTableId);
		final ImageView ivImage = (ImageView) view.findViewById(R.id.imgTable);
		final TextView tvTableCapacity = (TextView) view
				.findViewById(R.id.tvTableCapacity);

		final Table table = (Table) getItem(position);
		tvTableId.setText(table.getId());
		tvTableCapacity.setText("" + table.getCapacity());
		String imageFile = "cache/" + shop.getId() + "/image/table.png";
		try {
			ivImage.setImageBitmap(BitmapFactory.decodeStream(context.getAssets().open(imageFile)));
		} catch (IOException e) {
			Log.e(getClass().getName(), "Fail to load table image file '" + imageFile + "'");
		}

		return view;
	}

}