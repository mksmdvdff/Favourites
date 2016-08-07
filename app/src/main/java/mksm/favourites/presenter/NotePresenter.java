package mksm.favourites.presenter;

import mksm.favourites.view.BasicView;

/**
 * Created by mksm on 07.08.2016.
 */
public class NotePresenter extends BasicPresenter implements FavPresenter {

	public NotePresenter(BasicView view) {
		super(view);
	}

	@Override
	public void changeNoteFavorite(int id, boolean isFavorite) {
		new ChangeFavTask().execute(id, isFavorite);
	}
}
