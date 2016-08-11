package ru.yandex.yamblz.database;

import android.app.Application;
import android.database.Cursor;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import ru.yandex.yamblz.ApplicationModule;
import ru.yandex.yamblz.model.Artist;
import rx.Observable;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class ArtistsProvider {
    public static final Uri ARTISTS_URI = Uri.parse("content://ru.aleien.yapplication.provider/Artists");

    private Application application;
    private ApplicationModule.Api api;

    @Inject
    public ArtistsProvider(Application application, ApplicationModule.Api api) {
        this.application = application;
        this.api = api;
    }

    // Делаем запрос к БД через ContentResolver, в случае ошибки - запрашиваем данные из сети
    public Observable<List<Artist>> getArtists() {
        return getArtistsFromDB().subscribeOn(Schedulers.io())
                .doOnError(t -> Timber.e("getArtistsFromContentProvider"))
                .onErrorResumeNext(api.getArtists());
    }

    private Observable<List<Artist>> getArtistsFromDB() {
        return Observable.fromCallable(this::readArtists);
    }

    // Не очень красиво (:
    private List<Artist> readArtists() {
        List<Artist> dbArtists = new ArrayList<>();
        Cursor cursor = application.getContentResolver().query(ARTISTS_URI, DBContract.allColumns, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                String artistName = cursor.getString(cursor.getColumnIndex(DBContract.Artists.NAME));
                long id = cursor.getLong(cursor.getColumnIndex(DBContract.Artists.ID));
                int tracks = cursor.getInt(cursor.getColumnIndex(DBContract.Artists.TRACKS));
                int albums = cursor.getInt(cursor.getColumnIndex(DBContract.Artists.ALBUMS));
                String link = cursor.getString(cursor.getColumnIndex(DBContract.Artists.LINK));
                String description = cursor.getString(cursor.getColumnIndex(DBContract.Artists.DESCRIPTION));
                String smallCover = cursor.getString(cursor.getColumnIndex(DBContract.Artists.SMALL_COVER));
                String bigCover = cursor.getString(cursor.getColumnIndex(DBContract.Artists.BIG_COVER));
                Artist artist = new Artist((int) id, artistName, new ArrayList<>(), tracks, albums, link, description, new Artist.Cover(smallCover, bigCover));
                dbArtists.add(artist);
                Timber.v(artistName);
            } while (cursor.moveToNext());
            cursor.close();
        }

        return dbArtists;
    }

}
