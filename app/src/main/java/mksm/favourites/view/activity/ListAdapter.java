package mksm.favourites.view.activity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import mksm.favourites.R;
import mksm.favourites.model.Note;
import mksm.favourites.view.BasicView;

/**
 * Created by mksm on 07.08.2016.
 */
public class ListAdapter extends ArrayAdapter<Note> {

	private final static int layout = R.layout.item_layout;
	private static ListAdapter sInstance;
	private Context context;
	private List<Note> notes;

	public ListAdapter(BasicView view, List<Note> notes) {
		super(view.getContext(), layout, notes);
		this.context = view.getContext();
		this.notes = notes;
	}

	public static synchronized ListAdapter getInstance(BasicView view, List<Note> notes) {

		if (sInstance == null) {
			sInstance = new ListAdapter(view, notes);
		} else {
			sInstance.context = view.getContext();
			sInstance.notes = notes;
		}
		return sInstance;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = convertView;
		if (view == null) {
			view = inflater.inflate(R.layout.item_layout, parent, false);
		}

		final Note note = notes.get(position);

		((TextView) view.findViewById(R.id.item_title)).setText(note.getTitle());
		((TextView) view.findViewById(R.id.item_body)).setText(note.getBody());
		ImageView fav = ((ImageView) view.findViewById(R.id.item_fav));
		fav.setImageResource(note.isFavorite() ? R.drawable.fav : R.drawable.not_fav);
		fav.setTag(note.isFavorite() ? R.drawable.fav : R.drawable.not_fav);

		//fav.setOnClickListener(new OnFavIconListener(mainPresenter, note));
		//а я и к ListView onClickListener прикрутил)
		view.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, NoteActivity.class);
				intent.putExtra(BasicView.NOTE_ID, note.getId());
				intent.putExtra(BasicView.NOTE_BODY, note.getBody());
				intent.putExtra(BasicView.NOTE_TITLE, note.getTitle());
				intent.putExtra(BasicView.NOTE_FAV, note.isFavorite() ? 1 : 0);
				context.startActivity(intent);
			}
		});

		return view;
	}
}
