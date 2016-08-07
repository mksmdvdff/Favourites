package mksm.favourites.model.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by mksm on 06.08.2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "FavBase";
	private static final int DATABASE_VERSION = 1;

	private static DatabaseHelper sInstance;
	private final Context appContext;
	private List<DatabaseTable> databaseTables = new ArrayList<>();

	private DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.appContext = context;
	}

	public static synchronized DatabaseHelper getInstance(Context context) {

		if (sInstance == null) {
			sInstance = new DatabaseHelper(context.getApplicationContext());
		}
		return sInstance;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		for (DatabaseTable databaseTable : getDatabaseTables()) {
			StringBuilder queryBuilder = new StringBuilder("CREATE TABLE ");
			queryBuilder.append(databaseTable.getTableName()).append(" (");
			for (Map.Entry<String, String> columns : databaseTable.getColumns().entrySet()) {
				queryBuilder.append(columns.getKey()).append(" ").append(columns.getValue()).append(",");
			}
			queryBuilder.deleteCharAt(queryBuilder.lastIndexOf(","));
			queryBuilder.append(")");
			db.execSQL(queryBuilder.toString());
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		for (DatabaseTable databaseTable : getDatabaseTables()) {
			db.execSQL("DROP TABLE IF EXISTS " + databaseTable.getTableName());
		}
		this.onCreate(db);
	}

	@Override
	public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		for (DatabaseTable databaseTable : getDatabaseTables()) {
			db.execSQL("DROP TABLE IF EXISTS " + databaseTable.getTableName());
		}
		this.onCreate(db);
	}

	public List<DatabaseTable> getDatabaseTables() {
		if (databaseTables.isEmpty()) {
			databaseTables.add(new CacheTableHandler());
			databaseTables.add(new FavoriteTableHandler());
		}
		return databaseTables;
	}
}
