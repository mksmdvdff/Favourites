package mksm.favourites.view;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import mksm.favourites.presenter.BasicPresenter;

/**
 * Created by mksm on 07.08.2016.
 * Вся идея FakeView строится на возможности потери данных из-за пересоздания Activity при смене,
 * апример, ориентации. Обрабатываем такую ситуацию.
 */

public class FakeView implements BasicView {

	List<String> toasts = new ArrayList<>();

	@Override
	public Context getContext() {
		return null;
	}

	@Override
	public void makeToast(String text) {
		toasts.add(text);
	}

	@Override
	public <T extends BasicPresenter> T getPresenter() {
		return null;
	}

	public List<String> getAllToasts() {
		return toasts;

	}


}
