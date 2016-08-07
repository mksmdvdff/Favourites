package mksm.favourites.view;

import android.content.Context;

import mksm.favourites.presenter.BasicPresenter;

/**
 * Created by mksm on 06.08.2016.
 */
public interface BasicView {

	public static final String NOTE_ID = "noteId";
	public static final String NOTE_TITLE = "noteTitle";
	public static final String NOTE_BODY = "noteBody";
	public static final String NOTE_FAV = "noteFav";


	public Context getContext();

	public void makeToast(String text);

	public <T extends BasicPresenter> T getPresenter();
}
