package com.malvre.exerciciolistview.adapters;


import android.content.ContentValues;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.malvre.exerciciolistview.R;

import java.util.List;

public class ItemAdapter extends ArrayAdapter<ContentValues> {
	private static class ViewHolder {
		TextView listItem;
	}

	public ItemAdapter(Context context, List<ContentValues> itens) {
		super(context, R.layout.row_item, itens);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ContentValues item = getItem(position);

		ViewHolder viewHolder;
		if (convertView == null) {

			LayoutInflater inflater = LayoutInflater.from(getContext());
			convertView = inflater.inflate(R.layout.row_item, parent, false);

			viewHolder = new ViewHolder();
			viewHolder.listItem = (TextView) convertView.findViewById(R.id.list_item);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		viewHolder.listItem.setText(item.getAsString("item"));

		return convertView;
	}
}
