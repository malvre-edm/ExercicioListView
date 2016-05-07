package com.malvre.exerciciolistview;

import android.content.ContentValues;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.malvre.exerciciolistview.adapters.ContatoAdapter;
import com.malvre.exerciciolistview.utils.DB;
import com.malvre.exerciciolistview.utils.Dialogs;
import com.malvre.exerciciolistview.utils.ErrorHandler;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

	private static String TAG = MainActivity.class.getSimpleName();

	EditText editNome, editTelefone;
	Button btnAdicionar;
	ListView listview;
	TextView emptyView;
	ArrayList<ContentValues> contatos;
	ContatoAdapter adapter;

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
		editNome = (EditText) findViewById(R.id.editNome);
		editTelefone = (EditText) findViewById(R.id.editTelefone);

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
				long itemID = contatos.get(position).getAsLong("_id");
				deleteItem(itemID);
			}
		});
	}

	private void initData() {
		contatos = DB.selectRows(this, "SELECT * FROM contatos ORDER BY nome ASC LIMIT 300", null);
		adapter = new ContatoAdapter(this, contatos);
		listview.setAdapter(adapter);
		listview.setEmptyView(emptyView);
	}

	private void adicionaItem() {
		String nome = editNome.getText().toString();
		String telefone = editTelefone.getText().toString();

		ErrorHandler error = new ErrorHandler();
		if (TextUtils.isEmpty(editNome.getText())) {
			error.add("Informe o nome");
		}
		if (TextUtils.isEmpty(editTelefone.getText())) {
			error.add("Informe o telefone");
		}

		if (error.hasError()) {
			Dialogs.error(this, error.getMessage());
		} else {
			DB.executeSQL(this, "INSERT INTO contatos (nome, telefone) VALUES (?, ?)", new String[]{nome, telefone});
			editNome.setText("");
			editTelefone.setText("");
			editNome.requestFocus();
			initData();
		}
	}

	private void deleteItem(long itemID) {
		DB.executeSQL(this, "DELETE FROM contatos WHERE _id = ?", new String[]{String.valueOf(itemID)});
		Dialogs.toast(this, "Item excluído");
		initData();
	}


}
