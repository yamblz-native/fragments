package ru.yandex.yamblz.euv.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;

import ru.yandex.yamblz.euv.provider.db.DbBackend;
import ru.yandex.yamblz.euv.shared.contract.ProviderContract;
import timber.log.Timber;

/**
 * TODO: In additional to inserting initial data into db from json asset,
 * TODO: perform sync operations via {@link android.content.AbstractThreadedSyncAdapter}
 */
public class ArtistProvider extends ContentProvider {
    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private static final int ALL_ARTISTS = 0;
    private static final int SINGLE_ARTIST = 1;

    static {
        uriMatcher.addURI(ProviderContract.AUTHORITY, ProviderContract.ARTISTS_PATH, ALL_ARTISTS);
        uriMatcher.addURI(ProviderContract.AUTHORITY, ProviderContract.ARTISTS_PATH + "/#", SINGLE_ARTIST);
    }

    private DbBackend dbBackend;

    @Override
    public boolean onCreate() {
        dbBackend = new DbBackend(getContext());
        return true;
    }

    @Override
    public String getType(@NonNull Uri uri) {
        Timber.d("getType(uri=%s)", uri);
        return null;
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        Timber.d("insert(uri=%s, values=%s)", uri, values);
        return null;
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Timber.d("query(uri=%s)", uri);

        switch (uriMatcher.match(uri)) {
            case ALL_ARTISTS:
                return dbBackend.getArtists();

            case SINGLE_ARTIST:
                return dbBackend.getArtist(uri.getLastPathSegment());

            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        Timber.d("update(uri=%s)", uri);
        return 0;
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        Timber.d("delete(uri=%s)", uri);
        return 0;
    }
}
