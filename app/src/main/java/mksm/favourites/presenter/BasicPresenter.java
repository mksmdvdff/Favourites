package mksm.favourites.presenter;

import android.content.Context;
import android.os.AsyncTask;

import mksm.favourites.model.NotesService;
import mksm.favourites.view.BasicView;
import mksm.favourites.view.FakeView;

/**
 * Created by mksm on 06.08.2016.
 */
public class BasicPresenter {

	protected final NotesService notesService;
	protected BasicView view;

	protected BasicPresenter(BasicView view) {
		bindView(view);
		this.notesService = NotesService.getInstance(view.getContext());
	}

	public BasicView getView() {
		if (this.view == null) {
			this.view = new FakeView();
		}
		return view;
	}

	public Context getContext() {
		return view.getContext();
	}

	public void bindView(BasicView view) {
		if (this.view instanceof FakeView) replaceEventsFromFakeView((FakeView) this.view, view);
		this.view = view;
	}

	public void unbindView() {
		this.view = null;
	}

	protected void replaceEventsFromFakeView(FakeView fakeView, BasicView view) {
		for (String toast : fakeView.getAllToasts()) {
			view.makeToast(toast);
		}
	}

	protected class ChangeFavTask extends AsyncTask<Object, Void, Void> {

		@Override
		protected Void doInBackground(Object[] params) {
			notesService.changeNoteFavorite((int) params[0], (boolean) params[1]);
			return null;
		}
	}


}
