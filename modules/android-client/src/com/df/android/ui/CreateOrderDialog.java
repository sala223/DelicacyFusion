package com.df.android.ui;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.NumberPicker;

import com.df.android.R;
import com.df.android.entity.Shop;
import com.df.android.entity.Table;
import com.df.android.order.TableAdapter;

public class CreateOrderDialog extends Dialog {
	private Shop shop;
	private Table selectedTable;
	private boolean isOk = false;

	public CreateOrderDialog(final Context cxt, final Shop shop) {
		super(cxt);
		this.shop = shop;
		init(cxt);
	}

	private void init(final Context cxt) {
		setContentView(R.layout.choosetable);
		setTitle(R.string.chooseTable);

		final GridView gvTables = (GridView) findViewById(R.id.gvTables); 
		gvTables.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position,
					long arg3) {
				selectedTable = (Table)gvTables.getAdapter().getItem(position);
			}
			
		});

		gvTables.setAdapter(new TableAdapter(cxt, shop)); 

		final NumberPicker npHeadCount = (NumberPicker) findViewById(R.id.npHeadCount);
		npHeadCount.setMinValue(1);
		npHeadCount.setMaxValue(30);
		
		((Button) findViewById(R.id.btnConfirmTable))
				.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View view) {
						isOk = true;
						dismiss();
					}
				});

	}

	public Table getSelectedTable() {
		return isOk ? selectedTable : null;
	}
	
	public int getHeadCount() {
		return ((NumberPicker) findViewById(R.id.npHeadCount)).getValue();
	}
}
