package mksm.favourites.view;

import java.util.List;

import mksm.favourites.model.Note;

/**
 * Created by mksm on 07.08.2016.
 */
public interface MainView extends BasicView {

	public void fillNotes(List<Note> notes);

}
