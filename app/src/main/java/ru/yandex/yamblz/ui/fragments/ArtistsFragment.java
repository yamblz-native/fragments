package ru.yandex.yamblz.ui.fragments;


import android.database.Cursor;
import android.net.Uri;
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
import ru.yandex.yamblz.database.DBContract;
import ru.yandex.yamblz.model.Artist;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

public abstract class ArtistsFragment extends BaseFragment implements ArtistClickHandler, DBContract {
    private final CompositeSubscription subs = new CompositeSubscription();

    protected final List<Artist> artistsList = new ArrayList<>();
    @State protected Artist currentArtist;
    @State protected int currentPage;

    @Inject
    protected ApplicationModule.Api api;

    final Uri ARTISTS_URI = Uri.parse("content://ru.aleien.yapplication.provider/Artists");

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((App) getActivity().getApplication()).applicationComponent().inject(this);
        Icepick.restoreInstanceState(this, savedInstanceState);

        // Здесь ничего не проичходит, мы просто берем данные, ничего такого (:
        loadObservable(getArtistsFromDB(), this::logAndLoadFromNet);
    }

    private void logAndLoadFromNet(Throwable t) {
        Timber.e(t, "Error loading from DB, ERROR ERROR");
        loadArtistsFromWeb();
    }

    private void loadArtistsFromWeb() {
        loadObservable(api.getArtists(), Throwable::printStackTrace);
    }

    private void loadObservable(Observable<List<Artist>> observable,
                                Action1<Throwable> errorHandler) {
        subs.add(observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(t -> Timber.e("loadArtists", t))
                .subscribe(list -> showContent(list, this),
                        errorHandler
                ));
    }

    private Observable<List<Artist>> getArtistsFromDB() {
        return Observable.fromCallable(this::readArtists);
    }

    private List<Artist> readArtists() {
        List<Artist> dbArtists = new ArrayList<>();
        Cursor cursor = getActivity().getContentResolver().query(ARTISTS_URI, DBContract.allColumns, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                String artistName = cursor.getString(cursor.getColumnIndex(Artists.NAME));
                long id = cursor.getLong(cursor.getColumnIndex(Artists.ID));
                int tracks = cursor.getInt(cursor.getColumnIndex(Artists.TRACKS));
                int albums = cursor.getInt(cursor.getColumnIndex(Artists.ALBUMS));
                String link = cursor.getString(cursor.getColumnIndex(Artists.LINK));
                String description = cursor.getString(cursor.getColumnIndex(Artists.DESCRIPTION));
                String smallCover = cursor.getString(cursor.getColumnIndex(Artists.SMALL_COVER));
                String bigCover = cursor.getString(cursor.getColumnIndex(Artists.BIG_COVER));
                Artist artist = new Artist((int) id, artistName, new ArrayList<>(), tracks, albums, link, description, new Artist.Cover(smallCover, bigCover));
                dbArtists.add(artist);
                Timber.v(artistName);
            } while (cursor.moveToNext());
            cursor.close();
        }

        return dbArtists;
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
