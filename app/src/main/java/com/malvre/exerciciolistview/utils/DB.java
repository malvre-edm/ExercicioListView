package com.malvre.exerciciolistview.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DB extends SQLiteOpenHelper {

	private static String TAG = DB.class.getSimpleName();
	private static SQLiteDatabase mInstance = null;

	public static final String CREATE_TABLE_CONTATOS = "CREATE TABLE contatos (_id INTEGER PRIMARY KEY AUTOINCREMENT, nome TEXT, telefone TEXT)";

	public static SQLiteDatabase instance(Context ctx) {
		if (mInstance == null)
			mInstance = new DB(ctx.getApplicationContext()).getWritableDatabase();
		return mInstance;
	}

	private DB(Context ctx) {
		super(ctx, "banco", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.d(TAG, "Criando o banco de dados");
		db.execSQL(CREATE_TABLE_CONTATOS);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.d(TAG, "Atualizando o banco de dados");

		db.execSQL("DROP TABLE IF EXISTS itens");
		onCreate(db);
	}

	public static ArrayList<ContentValues> selectRows(Context ctx, String sql, String[] params) {
		Cursor c = DB.instance(ctx).rawQuery(sql, params);

		ArrayList<ContentValues> retVal = new ArrayList<ContentValues>();
		ContentValues map;
		if (c.moveToFirst()) {
			do {
				map = new ContentValues();
				DatabaseUtils.cursorRowToContentValues(c, map);
				retVal.add(map);
			} while (c.moveToNext());
		}
		c.close();
		return retVal;
	}

	public static void executeSQL(Context ctx, String sql, String[] params) {
		DB.instance(ctx).execSQL(sql, params);
	}

	public static long lastId(Context ctx, String tabela) {
		ArrayList<ContentValues> rows = DB.selectRows(ctx, "SELECT max(_id) as last_id FROM " + tabela, null);
		return rows.get(0).getAsLong("last_id");
	}
}
