package mksm.favourites.view.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
	private final Context context;

	public OnFavIconListener(Context context, FavPresenter presenter, int noteId) {
		this.context = context;
		this.presenter = presenter;
		this.noteId = noteId;
	}

	@Override
	public void onClick(View v) {
		final ImageView imageView = (ImageView) v;
		switch ((Integer) imageView.getTag()) {
			case R.drawable.fav:
				AlertDialog.Builder builder = new AlertDialog.Builder(context);
				builder.setMessage(R.string.unmake_favorite)
						.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								presenter.changeNoteFavorite(noteId, false);
								imageView.setImageResource(R.drawable.not_fav);
								imageView.setTag(R.drawable.not_fav);
							}
						})
						.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.cancel();
							}
						});
				AlertDialog alert = builder.create();
				alert.show();
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
