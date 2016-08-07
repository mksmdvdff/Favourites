package mksm.favourites.model.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mksm on 07.08.2016.
 */
public class FavoriteTableHandler extends TableHandler {

	private static FavoriteTableHandler sInstance;
	private final String ID_COLUMN = "_id";

	private FavoriteTableHandler(Context context) {
		super(context);
	}

	//некрасивое экстренное решение. Конструктор, который понадобится только для считывания имени таблицы и колонок
	protected FavoriteTableHandler() {
		super();
		init();
	}

	public static synchronized FavoriteTableHandler getInstance(Context context) {

		if (sInstance == null) {
			sInstance = new FavoriteTableHandler(context.getApplicationContext());
		}
		return sInstance;
	}

	@Override
	public void init() {
		this.TABLE_NAME = "favorites";

		this.columns.put(ID_COLUMN, "INTEGER PRIMARY KEY"); //будем просто хранить здесь ключи избранных
	}

	public void makeFavorite(int id) {
		ContentValues content = new ContentValues();
		content.put(ID_COLUMN, id);
		mDb.insert(TABLE_NAME, null, content);
	}

	public void makeNotFavorite(int id) {
		mDb.delete(TABLE_NAME, "_id = ?", new String[]{String.valueOf(id)});
	}

	public List<Integer> getAllIds() {
		Cursor cursor = mDb.rawQuery(GET_ALL_STRING, null);
		List<Integer> result = new ArrayList<>();
		if (cursor.moveToFirst()) {

			while (!cursor.isAfterLast()) {
				int _id = cursor.getInt(cursor.getColumnIndex(ID_COLUMN));
				result.add(_id);
				cursor.moveToNext();
			}
		}

		return result;
	}

}
