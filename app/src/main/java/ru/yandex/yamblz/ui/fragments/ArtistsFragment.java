package ru.yandex.yamblz.ui.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import icepick.Icepick;
import icepick.State;
import ru.yandex.yamblz.App;
import ru.yandex.yamblz.ApplicationModule;
import ru.yandex.yamblz.model.Artist;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public abstract class ArtistsFragment extends BaseFragment implements ArtistClickHandler {
    private final CompositeSubscription subs = new CompositeSubscription();

    protected final List<Artist> artistsList = new ArrayList<>();
    @State protected Artist currentArtist;
    @State protected int currentPage;

    @Inject
    protected ApplicationModule.Api api;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((App) getActivity().getApplication()).applicationComponent().inject(this);
        Icepick.restoreInstanceState(this, savedInstanceState);

        // Здесь ничего не проичходит, мы просто берем данные, ничего такого (:
        subs.add(api.getArtists()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> showContent(list, this),
                        Throwable::printStackTrace
                ));
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Icepick.restoreInstanceState(this, savedInstanceState);
    }

    protected abstract void showContent(List<Artist> artists, ArtistClickHandler handler);

    @Override
    public void onDestroy() {
        super.onDestroy();
        subs.clear();
    }
}
