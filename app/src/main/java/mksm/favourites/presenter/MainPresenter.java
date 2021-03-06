package mksm.favourites.presenter;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

import mksm.favourites.R;
import mksm.favourites.model.Note;
import mksm.favourites.model.NotesService;
import mksm.favourites.view.BasicView;
import mksm.favourites.view.FakeMainView;
import mksm.favourites.view.FakeView;
import mksm.favourites.view.MainView;

/**
 * Created by mksm on 07.08.2016.
 */
public class MainPresenter extends BasicPresenter implements FavPresenter {


	private static MainPresenter sInstance;

	private MainPresenter(BasicView view) {
		super(view);
	}

	public static synchronized MainPresenter getInstance(BasicView view) {

		if (sInstance == null) {
			sInstance = new MainPresenter(view);
		}
		if (sInstance.getView() instanceof FakeView) {
			sInstance.bindView(view);
		}
		return sInstance;
	}

	@Override
	public BasicView getView() {
		if (this.view == null) {
			this.view = new FakeMainView();
		}
		return view;
	}

	public void onCreate() {
		//getTasks(false);
	}

	public void onResume() {
		getTasks(false);
	}

	public void onDestroy() {
		unbindView();
	}

	public void onRefresh() {
		getTasks(true);
	}

	public void getTasks(boolean fromWeb) {
		DownloadNotesTask task = new DownloadNotesTask(fromWeb);
		task.execute();
	}

	public void changeNoteFavorite(int id, boolean isFavorite) {
		notesService.changeNoteFavorite(id, isFavorite); //уже не понадобится
	}

	@Override
	protected void replaceEventsFromFakeView(FakeView fakeView, BasicView view) {
		super.replaceEventsFromFakeView(fakeView, view);
		FakeMainView fakeMainView = (FakeMainView) fakeView;
		MainView mainView = (MainView) view;
		if (fakeMainView.isNotesWasDelivered()) {
			mainView.fillNotes(fakeMainView.getNotes());
		}
	}

	private class DownloadNotesTask extends AsyncTask<Void, Void, List<Note>> {

		private boolean fromWeb;
		private boolean exceptionCaught;
		private boolean noInternetExceptionCaught;

		public DownloadNotesTask(boolean fromWeb) {
			this.fromWeb = fromWeb;
		}

		@Override
		protected List<Note> doInBackground(final Void... params) {
			List<Note> result = new ArrayList<>();
			try {
				if (fromWeb) {
					result = notesService.getAllNotesFromWeb();
				}
			} catch (NotesService.NoInternetException ex) {
				noInternetExceptionCaught = true;
			} catch (Exception ex) {
				exceptionCaught = true;
			} finally {
				if (result == null || result.isEmpty()) {
					result = notesService.getAllNotesFromCache();
					if (result == null || result.isEmpty()) { //еще нет закэшированных значений - загрузим с вэба
						fromWeb = true;
						result = doInBackground();
					}
				}
			}
			return result;
		}

		@Override
		protected void onPostExecute(final List<Note> result) {
			if (noInternetExceptionCaught)
				getView().makeToast(getView().getContext().getResources()
						.getString(R.string.noInternetException));
			if (exceptionCaught) {
				getView().makeToast(getView().getContext().getResources()
						.getString(R.string.cantGetDataException));
			}
			((MainView) MainPresenter.this.getView()).fillNotes(result);
		}
	}
}
