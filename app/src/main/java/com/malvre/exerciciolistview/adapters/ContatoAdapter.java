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

public class ContatoAdapter extends ArrayAdapter<ContentValues> {
	private static class ViewHolder {
		TextView nome;
		TextView telefone;
	}

	public ContatoAdapter(Context context, List<ContentValues> itens) {
		super(context, R.layout.row_item, itens);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ContentValues contato = getItem(position);

		ViewHolder viewHolder;
		if (convertView == null) {

			LayoutInflater inflater = LayoutInflater.from(getContext());
			convertView = inflater.inflate(R.layout.row_item, parent, false);

			viewHolder = new ViewHolder();
			viewHolder.nome = (TextView) convertView.findViewById(R.id.textNome);
			viewHolder.telefone = (TextView) convertView.findViewById(R.id.textTelefone);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		viewHolder.nome.setText(contato.getAsString("nome"));
		viewHolder.telefone.setText(contato.getAsString("telefone"));

		return convertView;
	}
}
