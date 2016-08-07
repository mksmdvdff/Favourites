package mksm.favourites.view.activity;

import android.view.View;
import android.widget.ImageView;

import mksm.favourites.R;
import mksm.favourites.presenter.FavPresenter;

/**
 * Created by mksm on 07.08.2016.
 */
public class OnFavIconListener implements View.OnClickListener {

	private final int noteId;
	private final FavPresenter presenter;

	public OnFavIconListener(FavPresenter presenter, int noteId) {
		this.presenter = presenter;
		this.noteId = noteId;
	}

	@Override
	public void onClick(View v) {
		ImageView imageView = (ImageView) v;
		switch ((Integer) imageView.getTag()) {
			case R.drawable.fav:
				presenter.changeNoteFavorite(noteId, false);
				imageView.setImageResource(R.drawable.not_fav);
				imageView.setTag(R.drawable.not_fav);
				break;
			case R.drawable.not_fav:
				presenter.changeNoteFavorite(noteId, true);
				imageView.setImageResource(R.drawable.fav);
				imageView.setTag(R.drawable.fav);
				break;
			default:
				break;

		}
	}
}
