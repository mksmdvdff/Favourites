package mksm.favourites.model;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

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
	private final NotesRetrofitLoader retrofitService;
	private final CacheTableHandler cacheTableHandler;
	private final FavoriteTableHandler favoriteTableHandler;
	private final List<Integer> favoriteIds;
	private final ConnectivityManager cm;
	private List<Note> cachedNotes;

	private NotesService(Context context) {
		Retrofit retrofit = new Retrofit.Builder()
				.baseUrl("http://jsonplaceholder.typicode.com/")
				.addConverterFactory(GsonConverterFactory.create())
				.build();

		retrofitService = retrofit.create(NotesRetrofitLoader.class);
		cacheTableHandler = CacheTableHandler.getInstance(context);
		favoriteTableHandler = FavoriteTableHandler.getInstance(context);
		favoriteIds = favoriteTableHandler.getAllIds();
		cm = (ConnectivityManager) context.getApplicationContext().getSystemService(
				Context.CONNECTIVITY_SERVICE);
	}

	public static synchronized NotesService getInstance(Context context) {

		if (sInstance == null) {
			sInstance = new NotesService(context);
		}
		return sInstance;
	}

	public List<Note> getAllNotesFromWeb() throws NoInternetException {
		List<Note> result;
		try {
			if (!isOnline()) {
				throw new NoInternetException();
			}
			result = retrofitService.getNotes().execute().body();
			if (result == null) {
				throw new RuntimeException();
			} else {
				fillFavorites(result);
				cacheTableHandler.replaceAllNotes(result);
				cachedNotes = result;
			}

			return result;
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public List<Note> getAllNotesFromCache() {
		if (cachedNotes == null || cachedNotes.isEmpty()) {
			cachedNotes = cacheTableHandler.getAllNotes();
			fillFavorites(cachedNotes);
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
		fillFavorites(cachedNotes);
	}

	private void fillFavorites(List<Note> notes) {
		for (Note note : notes) {
			if (favoriteIds.contains(note.getId())) {
				note.setFavorite(true);
			} else {
				note.setFavorite(false);
			}
		}
	}

	private boolean isOnline() {
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		return netInfo != null && netInfo.isConnectedOrConnecting();
	}

	public static class NoInternetException extends Exception {
	}


}
