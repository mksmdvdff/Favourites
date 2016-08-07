package mksm.favourites.view;

import java.util.ArrayList;
import java.util.List;

import mksm.favourites.model.Note;

/**
 * Created by mksm on 07.08.2016.
 */
public class FakeMainView extends FakeView implements MainView {

	private List<Note> notes = new ArrayList<>();
	private boolean notesWasDelivered = false;

	@Override
	public void fillNotes(List<Note> notes) {
		this.notesWasDelivered = true;
		this.notes = notes;
	}

	public List<Note> getNotes() {
		return notes;
	}

	public boolean isNotesWasDelivered() {
		return notesWasDelivered;
	}
}
