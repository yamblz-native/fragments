package ru.yandex.yamblz.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import butterknife.BindView;
import ru.yandex.yamblz.R;
import ru.yandex.yamblz.artistmodel.Artist;
import ru.yandex.yamblz.ui.adapters.RecycleViewAdapter;
import ru.yandex.yamblz.util.asynctask.GettingArtistsAsyncTask;

/**
 * Created by root on 8/10/16.
 */
public class ArtistsLargeFragment extends BaseFragment {

    @BindView(R.id.artist_list) RecyclerView recyclerView;
    @BindView(R.id.progress) ProgressBar progressBar;

    private RecyclerView.Adapter adapter;

    private GettingArtistsAsyncTask gettingArtistsAsyncTask;

    private Artist[] artists;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_artists, container, false);
    }

    @Override
    public void onPause() {
        super.onPause();
        cancelLoading();
    }

    private void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    private void onErrorLoading() {
        progressBar.setVisibility(View.GONE);
        Toast.makeText(getContext(), getContext().getResources().getString(R.string.nodata), Toast.LENGTH_SHORT).show();
    }

    private void cancelLoading() {
        if(gettingArtistsAsyncTask != null) {
            gettingArtistsAsyncTask.cancel(true);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData() {

        if(artists == null) {
            cancelLoading();
            gettingArtistsAsyncTask = new GettingArtistsAsyncTask(this::showProgress, this::setupRecycler, this::onErrorLoading, getContext());
            gettingArtistsAsyncTask.execute();
        } else {
            setupRecycler(artists);
        }
    }

    private void setupRecycler(Artist[] artists) {

        this.artists = artists;

        adapter = new RecycleViewAdapter(artists, (artist) -> {

            Fragment fragment = new ArtistTabFragment();

            Bundle args = new Bundle();

            args.putSerializable(Artist.class.getCanonicalName(), artist);

            getFragmentManager().beginTransaction().replace(R.id.artist_layout, fragment)
            .commit();
        });

        recyclerView.setAdapter(adapter);

        progressBar.setVisibility(View.GONE);
    }

}
