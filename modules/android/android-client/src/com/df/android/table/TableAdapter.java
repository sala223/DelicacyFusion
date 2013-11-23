package com.df.android.table;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.df.android.R;
import com.df.client.rs.model.DiningTable;

public class TableAdapter extends BaseAdapter {
	private Context context;
	private List<DiningTable> tables;

	public TableAdapter(Context context, List<DiningTable> tables) {
		super();
		this.context = context;
		this.tables = tables;
	}

	@Override
	public int getCount() {
		return tables.size();
	}

	@Override
	public Object getItem(int position) {
		return tables.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflator = LayoutInflater.from(context);

		View view = convertView;
		if (view == null) {
			view = inflator.inflate(R.layout.tableitem, parent, false);
			final TextView tvTableId = (TextView) view.findViewById(R.id.tvTableId);
			final ImageView ivImage = (ImageView) view.findViewById(R.id.imgTable);
			final TextView tvTableCapacity = (TextView) view
					.findViewById(R.id.tvTableCapacity);

			final DiningTable table = (DiningTable) getItem(position);
			tvTableId.setText(table.getCode());
			tvTableCapacity.setText("" + table.getCapacity());
				ivImage.setImageResource(R.drawable.table);
		}
		

		return view;
	}

}