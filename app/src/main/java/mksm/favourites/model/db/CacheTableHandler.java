package mksm.favourites.model.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import mksm.favourites.model.Note;

/**
 * Created by mksm on 06.08.2016.
 */
public class CacheTableHandler extends TableHandler {

	private static CacheTableHandler sInstance;
	private final String ID_COLUMN = "_id";
	private final String USER_ID_COLUMN = "user_id";
	private final String TITLE_COLUMN = "title";
	private final String BODY_COLUMN = "body";

	private CacheTableHandler(Context context) {
		super(context);
	}

	protected CacheTableHandler() {
		super();
		init();
	}

	public static synchronized CacheTableHandler getInstance(Context context) {

		if (sInstance == null) {
			sInstance = new CacheTableHandler(context.getApplicationContext());
		}
		return sInstance;
	}

	@Override
	public void init() {
		this.TABLE_NAME = " cache "; //с пробелами лучше переборщить, чем недоставить

		this.columns.put(ID_COLUMN, "INTEGER PRIMARY KEY "); //конечно, в json может быть два одинаковых id, и закешируется только один, но C'est la vie
		this.columns.put(USER_ID_COLUMN, " INTEGER ");
		this.columns.put(TITLE_COLUMN, " TEXT ");
		this.columns.put(BODY_COLUMN, " TEXT ");
	}

	public List<Note> getAllNotes() {
		Cursor cursor = mDb.rawQuery(GET_ALL_STRING, null);
		List<Note> result = new ArrayList<>();
		if (cursor.moveToFirst()) {

			while (!cursor.isAfterLast()) {
				int _id = cursor.getInt(cursor.getColumnIndex(ID_COLUMN));
				int userId = cursor.getInt(cursor.getColumnIndex(USER_ID_COLUMN));
				String title = cursor.getString(cursor.getColumnIndex(TITLE_COLUMN));
				String body = cursor.getString(cursor.getColumnIndex(BODY_COLUMN));


				result.add(new Note(userId, _id, title, body));

				cursor.moveToNext();
			}
		}

		return result;
	}

	public void replaceAllNotes(List<Note> notes) {
		mDb.execSQL(DELETE_ALL_STRING);
		mDb.execSQL(VACUUM);
		for (Note note : notes) {
			ContentValues content = new ContentValues();
			content.put(ID_COLUMN, note.getId());
			content.put(USER_ID_COLUMN, note.getUserId());
			content.put(TITLE_COLUMN, note.getTitle());
			content.put(BODY_COLUMN, note.getBody());
			mDb.insert(TABLE_NAME, null, content);
		}
	}    //некрасивое экстренное решение. Конструктор, который понадобится только для считывания имени таблицы и колонок
}
