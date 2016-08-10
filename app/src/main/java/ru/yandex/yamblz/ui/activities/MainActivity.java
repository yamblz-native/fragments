package ru.yandex.yamblz.ui.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import javax.inject.Inject;

import ru.yandex.yamblz.App;
import ru.yandex.yamblz.R;
import ru.yandex.yamblz.domain.DataManager;
import ru.yandex.yamblz.domain.interactors.LoadArtistsListInteractor;
import ru.yandex.yamblz.domain.interactors.listeners.OnArtistsUpdateListener;
import ru.yandex.yamblz.ui.adapters.ArtistsListAdapter;
import ru.yandex.yamblz.ui.fragments.ArtistDetailsFragment;
import ru.yandex.yamblz.ui.fragments.ArtistPreviewFragment;
import ru.yandex.yamblz.ui.fragments.ArtistsTabsFragment;
import ru.yandex.yamblz.ui.interfaces.ArtistPreviewNavigator;
import ru.yandex.yamblz.ui.interfaces.OnItemClickListener;

public class MainActivity extends BaseActivity
        implements OnArtistsUpdateListener, OnItemClickListener, ArtistPreviewNavigator {

    View llLoading;
    RecyclerView rvArtists;
    ArtistsListAdapter recyclerAdapter;

    @Inject
    DataManager dataManager;
    @Inject
    LoadArtistsListInteractor interactor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        App.get(this).applicationComponent().inject(this);

        initViews();

        if (savedInstanceState == null) {
            loadArtists();
        } else {
            if (rvArtists != null) showArtistsList();
        }
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onArtistsLoadingSuccess() {
        if (rvArtists != null) {
            showArtistsList();
        } else {
            showArtistsTabs();
        }
        hideProgress();
        interactor.removeListener();
    }

    @Override
    public void onArtistsLoadingFailure() {
        interactor.removeListener();
        hideProgress();
        Toast.makeText(this, "Ошибка при загрузке списка исполнителей", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onItemClick(int position) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fl_fragment_container, ArtistPreviewFragment.newInstance(position), ArtistPreviewFragment.TAG)
                .commit();
    }

    @Override
    public void navigateToArtistDetails(int position) {
        if (rvArtists == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fl_fragment_container, ArtistDetailsFragment.newInstance(position), ArtistDetailsFragment.TAG)
                    .addToBackStack("")
                    .commit();
        } else {
            ArtistDetailsFragment.newInstance(position).show(getSupportFragmentManager(), ArtistDetailsFragment.TAG);
        }
    }

    private void initViews() {
        llLoading = findViewById(R.id.ll_loading);
        rvArtists = (RecyclerView) findViewById(R.id.rv_artists);
    }

    private void loadArtists() {
        interactor.setListener(this);
        interactor.execute();
        showProgress();
    }

    private void showProgress() {
        llLoading.setVisibility(View.VISIBLE);
    }

    private void hideProgress() {
        llLoading.setVisibility(View.GONE);
    }

    private void showArtistsList() {
        if (recyclerAdapter == null) {
            recyclerAdapter = new ArtistsListAdapter(this, dataManager.getArtists(), this);
            rvArtists.setAdapter(recyclerAdapter);
        } else {
            recyclerAdapter.setData(dataManager.getArtists());
        }
    }

    private void showArtistsTabs() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fl_fragment_container, ArtistsTabsFragment.newInstance())
                .commit();
    }

}
