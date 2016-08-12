package ru.yandex.yamblz.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import butterknife.BindView;
import icepick.State;
import ru.yandex.yamblz.R;
import ru.yandex.yamblz.artistmodel.Artist;
import ru.yandex.yamblz.util.asynctask.GettingArtistsAsyncTask;

public class ArtistsFragment extends BaseFragment {

    @BindView(R.id.view_pager) ViewPager viewPager;
    @BindView(R.id.tab_layout) TabLayout tabLayout;
    @BindView(R.id.progress) ProgressBar progressBar;

    private PagerAdapter adapter;

    private GettingArtistsAsyncTask gettingArtistsAsyncTask;

    @State Artist[] artists;

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_artists, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
    }

    private void showProgress() {
        Log.d("contentproviderapp", "from progress");
        viewPager.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

    private void setupPager(Artist[] artists) {

        Log.d("contentproviderapp", "from setup");

        this.artists = artists;

        adapter = new ru.yandex.yamblz.ui.adapters.PagerAdapter(getFragmentManager(), artists);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        viewPager.setVisibility(View.VISIBLE);
        hideProgress();

    }

    private void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    private void onErrorLoading() {
        Toast.makeText(getContext(), getContext().getResources().getString(R.string.nodata), Toast.LENGTH_SHORT).show();
        hideProgress();
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    private void cancelLoading() {
        if(gettingArtistsAsyncTask != null) {
            gettingArtistsAsyncTask.cancel(true);
        }
    }

    private void loadData() {

        if(artists == null) {
            cancelLoading();
            gettingArtistsAsyncTask = new GettingArtistsAsyncTask(this::showProgress, this::setupPager, this::onErrorLoading, getContext());
            gettingArtistsAsyncTask.execute();
        } else {
            setupPager(artists);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        cancelLoading();
    }
}
