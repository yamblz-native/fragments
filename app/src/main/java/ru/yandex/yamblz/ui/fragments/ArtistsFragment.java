package ru.yandex.yamblz.ui.fragments;


import android.database.ContentObserver;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import icepick.Icepick;
import icepick.State;
import ru.yandex.yamblz.App;
import ru.yandex.yamblz.ApplicationModule;
import ru.yandex.yamblz.database.ArtistsProvider;
import ru.yandex.yamblz.database.DBContract;
import ru.yandex.yamblz.model.Artist;
import rx.android.schedulers.AndroidSchedulers;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

public abstract class ArtistsFragment extends BaseFragment implements ArtistClickHandler, DBContract {
    private final CompositeSubscription subs = new CompositeSubscription();

    protected final List<Artist> artistsList = new ArrayList<>();
    @State protected Artist currentArtist;
    @State protected int currentPage;

    @Inject
    protected ApplicationModule.Api api;
    @Inject
    protected ArtistsProvider artistsProvider;
    private ArtistsContentObserver contentObserver = new ArtistsContentObserver(new Handler(Looper.getMainLooper()));

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((App) getActivity().getApplication()).applicationComponent().inject(this);
        Icepick.restoreInstanceState(this, savedInstanceState);

        getContext().getContentResolver().registerContentObserver(ArtistsProvider.ARTISTS_URI, true, contentObserver);
        requestArtists();
    }

    private void requestArtists() {
        artistsProvider.getArtists()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(t -> Timber.e("getArtists"))
                .subscribe(artists -> showContent(artists, this),
                        t -> Timber.e(t + "getArtistsFromWeb"));
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
        getContext().getContentResolver().unregisterContentObserver(contentObserver);
        subs.clear();
    }

    private class ArtistsContentObserver extends ContentObserver {

        public ArtistsContentObserver(Handler handler) {
            super(handler);
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            requestArtists();
        }

        @Override
        public void onChange(boolean selfChange, Uri uri) {
            super.onChange(selfChange, uri);
            requestArtists();
        }
    }
}
