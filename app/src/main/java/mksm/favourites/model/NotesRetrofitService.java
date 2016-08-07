package mksm.favourites.model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by mksm on 06.08.2016.
 */
public interface NotesRetrofitService {

	@GET("/posts")
	Call<List<Note>> getNotes();
}
