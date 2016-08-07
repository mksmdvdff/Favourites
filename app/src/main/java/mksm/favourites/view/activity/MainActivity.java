package mksm.favourites.view.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import mksm.favourites.R;
import mksm.favourites.model.Note;
import mksm.favourites.presenter.MainPresenter;
import mksm.favourites.view.MainView;

public class MainActivity extends AppCompatActivity implements MainView, SwipeRefreshLayout.OnRefreshListener {

	private MainPresenter presenter;
	private SwipeRefreshLayout refreshLayout;
	//private ProgressBar progressBar;
	private ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_layout);
		refreshLayout = (SwipeRefreshLayout) findViewById(R.id.main_swiperefresh);
		refreshLayout.setOnRefreshListener(this);
		//progressBar = (ProgressBar) findViewById(R.id.main_progress);
		listView = (ListView) findViewById(R.id.main_list);
		showLoading(true);
		getPresenter().onCreate();
	}

	@Override
	public void fillNotes(List<Note> notes) {
		refreshLayout.setRefreshing(false);
		showLoading(false);
		ListAdapter adapter = ListAdapter.getInstance(this, notes);
		listView.setAdapter(adapter);

	}

	@Override
	public Context getContext() {
		return this;
	}

	@Override
	public void makeToast(String text) {
		Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT).show();
	}

	@Override
	public MainPresenter getPresenter() {
		if (presenter == null) {
			this.presenter = MainPresenter.getInstance(this);
		}
		return this.presenter;
	}

	private void showLoading(final boolean show) {
		//progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
	}

	@Override
	public void onRefresh() {
		presenter.getTasks(true);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		presenter.unbindView();
	}

	@Override
	protected void onResume() {
		super.onResume();
		getPresenter().getTasks(false); //обновим список из кэша после возвращения
	}
}
