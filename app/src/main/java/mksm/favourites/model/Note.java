package mksm.favourites.model;

/**
 * Created by mksm on 06.08.2016.
 */
public class Note {

	private final int userId;
	private final int id;
	private final String title;
	private final String body;
	private boolean favorite;

	public Note(int userId, int id, String title, String body, boolean favorite) {
		this.userId = userId;
		this.id = id;
		this.title = title;
		this.body = body;
		this.favorite = favorite;
	}

	public Note(int userId, int id, String title, String body) {
		this(userId, id, title, body, false);
	}

	public int getUserId() {
		return userId;
	}

	public int getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getBody() {
		return body;
	}

	public boolean isFavorite() {
		return favorite;
	}

	public void setFavorite(boolean favorite) {
		this.favorite = favorite;
	}
}
