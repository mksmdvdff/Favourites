package mksm.favourites.model.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by mksm on 06.08.2016.
 */
public abstract class TableHandler implements DatabaseTable {

	protected final DatabaseHelper mDbHelper;
	protected final SQLiteDatabase mDb;
	protected final String GET_ALL_STRING;
	protected final String DELETE_ALL_STRING;
	protected final String VACUUM = "VACUUM";
	protected String TABLE_NAME;
	protected Map<String, String> columns = new HashMap<>();

	protected TableHandler(Context context) {
		this.mDbHelper = DatabaseHelper.getInstance(context);
		this.mDb = mDbHelper.getWritableDatabase();
		this.init();
		GET_ALL_STRING = "SELECT * FROM " + getTableName();
		DELETE_ALL_STRING = "DELETE FROM " + getTableName();

	}

	//некрасивое экстренное решение. Конструктор, который понадобится только для считывания имени таблицы и колонок
	protected TableHandler() {
		mDbHelper = null;
		mDb = null;
		GET_ALL_STRING = null;
		DELETE_ALL_STRING = null;
	}

	public Map<String, String> getColumns() {
		return columns;
	}

	public Set<String> getColumnNames() {
		return columns.keySet();
	}

	//в этом методе будем инициализировать TableName и Columns
	public abstract void init();

	public String getTableName() {
		return TABLE_NAME;
	}
}
