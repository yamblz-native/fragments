package ru.yandex.yamblz.ui.fragments;

/**
 * Created by shmakova on 08.08.16.
 */

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;

import ru.yandex.yamblz.R;
import ru.yandex.yamblz.data.models.Artist;
import ru.yandex.yamblz.managers.DataManager;
import ru.yandex.yamblz.ui.activities.MainActivity;
import ru.yandex.yamblz.ui.adapters.ArtistsAdapter;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ArtistsListFragment extends BaseFragment {
    private DataManager dataManager;
    private Subscription subscription;

    @BindView(R.id.artists_list)
    RecyclerView recyclerView;

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_artists_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        updateToolBar();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        dataManager = DataManager.getInstance(getContext());
        loadArtists();
    }

    private void loadArtists() {
        subscription = Observable.from(dataManager.getArtistsListCursor())
                .map(Artist::getArtistFromCursor)
                .toList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(artistsList -> {
                    if (artistsList != null) {
                        ArtistsAdapter artistsAdapter = new ArtistsAdapter(artistsList, (position) -> {
                            Artist artist = artistsList.get(position);
                            Fragment artistFragment = new ArtistPageFragmentBuilder(artist).build();
                            getActivity().getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.detail_frame_layout, artistFragment)
                                    .addToBackStack(null)
                                    .commit();
                        });
                        recyclerView.setAdapter(artistsAdapter);
                    }
                });
    }

    @Override
    public void onDestroyView() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
        super.onDestroyView();
    }


    /**
     * Updates toolbar
     */
    private void updateToolBar() {
        ActionBar actionBar = ((MainActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.main_activity_name);
            actionBar.setDisplayHomeAsUpEnabled(false);
        }
    }
}