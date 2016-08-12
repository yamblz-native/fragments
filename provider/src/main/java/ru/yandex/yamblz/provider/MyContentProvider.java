package ru.yandex.yamblz.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;
import java.util.concurrent.ExecutionException;

import ru.yandex.yamblz.data_base.DbProvider;
import ru.yandex.yamblz.model.Artist;
import ru.yandex.yamblz.service.ServiceFactory;
import ru.yandex.yamblz.service.YandexArtistApi;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by SerG3z on 06.08.16.
 */

public class MyContentProvider extends ContentProvider {
    private static final String AUTHORITY = "ru.yandex.yamblz";
    private static final String CONTENT = "content://";
    private static final String ARTISTS_PATH = "artists";
    private static final int ARTISTS_URI_CODE = 1;
    private static final UriMatcher uriMatcher;
    private DbProvider provider;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, ARTISTS_PATH, ARTISTS_URI_CODE);
    }

    public Cursor query(@NonNull Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        switch (uriMatcher.match(uri)) {
            case ARTISTS_URI_CODE:
                Cursor cursor = null;
                try {
                    cursor = provider.getCursorArtistList();
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
                return cursor;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
    }

    @Override
    public boolean onCreate() {
        provider = new DbProvider(getContext());
        saveArtistsInDataBase();
        return false;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (uriMatcher.match(uri)) {
            case ARTISTS_URI_CODE:
                return "vnd.android.cursor.dir/vnd.ru.yandex.yamblz.provider.artists";
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
    }

    //вроде не нужны для данного задания
    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }

    //скачиваю через rx и пишу в базу
    public void saveArtistsInDataBase() {
        YandexArtistApi yandexArtistApi = ServiceFactory.createService(YandexArtistApi.class);
        yandexArtistApi
                .getListArtist()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Artist>>() {
                    @Override
                    public void onCompleted() {
                        Timber.d("Данные добавлены");
                        getContext().getContentResolver().notifyChange(Uri.parse(CONTENT + AUTHORITY + "/" + ARTISTS_PATH), null);
                        Timber.d("Кинул нотификацию");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.d("Произошла ошибка при загрузке с сервера" + e.getMessage());
                    }

                    @Override
                    public void onNext(List<Artist> artists) {
                        provider.addArtistsList(artists);
                        Timber.d("Данные получены");
                    }
                });
    }
}
