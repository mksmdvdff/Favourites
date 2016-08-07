package mksm.favourites.model;

import android.content.Context;

import java.io.IOException;
import java.util.List;

import mksm.favourites.model.db.CacheTableHandler;
import mksm.favourites.model.db.FavoriteTableHandler;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by mksm on 06.08.2016.
 */
public class NotesService {

	private static NotesService sInstance;
	private final NotesRetrofitService retrofitService;
	private final CacheTableHandler cacheTableHandler;
	private final FavoriteTableHandler favoriteTableHandler;
	private final List<Integer> favoriteIds;
	private List<Note> cachedNotes;

	private NotesService(Context context) {
		Retrofit retrofit = new Retrofit.Builder()
				.baseUrl("http://jsonplaceholder.typicode.com/")
				.addConverterFactory(GsonConverterFactory.create())
				.build();

		retrofitService = retrofit.create(NotesRetrofitService.class);
		cacheTableHandler = CacheTableHandler.getInstance(context);
		favoriteTableHandler = FavoriteTableHandler.getInstance(context);
		favoriteIds = favoriteTableHandler.getAllIds();
	}

	public static synchronized NotesService getInstance(Context context) {

		if (sInstance == null) {
			sInstance = new NotesService(context);
		}
		return sInstance;
	}

	public List<Note> getAllNotesFromWeb() {
		List<Note> result = null;
		try {
			result = retrofitService.getNotes().execute().body();
			if (result == null) {
				throw new RuntimeException("Empty resultList");
			} else {
				for (Note note : result) {
					if (favoriteIds.contains(note.getId())) {
						note.setFavorite(true);
					}
				}
				cacheTableHandler.replaceAllNotes(result);
				cachedNotes = result;
			}

			return result;
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		} finally {
			cachedNotes = cacheTableHandler.getAllNotes();
		}
	}

	public List<Note> getAllNotesFromCache() {
		if (cachedNotes == null || cachedNotes.isEmpty()) {
			cachedNotes = cacheTableHandler.getAllNotes();
			for (Note note : cachedNotes) {
				if (favoriteIds.contains(note.getId())) {
					note.setFavorite(true);
				}
			}
		}

		return cachedNotes;
	}

	public void changeNoteFavorite(int id, boolean favorite) {
		if (favorite) {
			favoriteIds.add(id);
			favoriteTableHandler.makeFavorite(id);
		} else {
			favoriteIds.remove((Integer) id);  //здесь без приведения вызывался не тот remove()
			favoriteTableHandler.makeNotFavorite(id);
		}
		for (Note note : cachedNotes) {
			if (favoriteIds.contains(note.getId())) {
				note.setFavorite(favorite);
			}
		}
	}


}
