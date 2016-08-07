package mksm.favourites.view.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import mksm.favourites.R;
import mksm.favourites.presenter.NotePresenter;
import mksm.favourites.view.BasicView;

/**
 * Created by mksm on 07.08.2016.
 */
public class NoteActivity extends AppCompatActivity implements BasicView {

	private NotePresenter presenter;
	private TextView titleTV;
	private TextView bodyTV;
	private ImageView favIcon;
	private int noteId;

	@Override
	public Context getContext() {
		return this;
	}

	@Override
	public void makeToast(String text) {
		Toast.makeText(NoteActivity.this, text, Toast.LENGTH_SHORT).show();
	}

	@Override
	public NotePresenter getPresenter() {
		if (presenter == null) {
			this.presenter = new NotePresenter(this);
		}
		return this.presenter;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.note_layout);
		titleTV = (TextView) findViewById(R.id.note_title);
		bodyTV = (TextView) findViewById(R.id.note_body);
		favIcon = (ImageView) findViewById(R.id.note_fav);

		Bundle b = getIntent().getExtras();
		titleTV.setText(b.getString(NOTE_TITLE));
		bodyTV.setText(b.getString(NOTE_BODY));
		boolean isFavorite = b.getInt(NOTE_FAV) == 1;
		favIcon.setImageResource(isFavorite ? R.drawable.fav : R.drawable.not_fav);
		favIcon.setTag(isFavorite ? R.drawable.fav : R.drawable.not_fav);
		noteId = b.getInt(NOTE_ID);
		favIcon.setOnClickListener(new OnFavIconListener(getPresenter(), noteId));
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		presenter.unbindView();
	}
}
