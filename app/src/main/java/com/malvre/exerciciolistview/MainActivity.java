package com.malvre.exerciciolistview;

import android.content.ContentValues;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.malvre.exerciciolistview.adapters.ItemAdapter;
import com.malvre.exerciciolistview.utils.DB;
import com.malvre.exerciciolistview.utils.Dialogs;
import com.malvre.exerciciolistview.utils.ErrorHandler;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

	private static String TAG = MainActivity.class.getSimpleName();

	EditText editItem;
	Button btnAdicionar;
	ListView listview;
	TextView emptyView;
	ArrayList<ContentValues> itens;
	ItemAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initView();
		initEvents();
		initData();
	}

	private void initView() {
		emptyView = (TextView) findViewById(R.id.empty);
		editItem = (EditText) findViewById(R.id.editItem);
		listview = (ListView) findViewById(R.id.listView);
		btnAdicionar = (Button) findViewById(R.id.btnAdicionar);
	}

	private void initEvents() {
		btnAdicionar.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				adicionaItem();
			}
		});

		listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				long itemID = itens.get(position).getAsLong("_id");
				deleteItem(itemID);
			}
		});
	}

	private void initData() {
		itens = DB.selectRows(this, "SELECT * FROM itens ORDER BY _id DESC LIMIT 300", null);
		adapter = new ItemAdapter(this, itens);
		listview.setAdapter(adapter);
		listview.setEmptyView(emptyView);
	}

	private void adicionaItem() {
		String item = editItem.getText().toString();

		ErrorHandler error = new ErrorHandler();
		if (TextUtils.isEmpty(editItem.getText())) {
			error.add("Informe um item");
		}

		if (error.hasError()) {
			Dialogs.error(this, error.getMessage());
		} else {
			DB.executeSQL(this, "INSERT INTO itens (item) VALUES (?)", new String[]{item});
			editItem.setText("");
			initData();
		}
	}

	private void deleteItem(long itemID) {
		DB.executeSQL(this, "DELETE FROM itens WHERE _id = ?", new String[]{String.valueOf(itemID)});
		Dialogs.toast(this, "Item excluído");
		initData();
	}


}
