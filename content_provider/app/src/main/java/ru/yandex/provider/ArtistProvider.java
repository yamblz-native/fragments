package ru.yandex.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.IOException;
import java.util.List;

import retrofit2.Retrofit;
import ru.yandex.provider.database.ArtistDbBackend;
import ru.yandex.provider.database.ArtistDbContract;
import ru.yandex.provider.database.ArtistDbProvider;
import ru.yandex.provider.network.ArtistService;
import ru.yandex.provider.network.ContentLoader;
import ru.yandex.yamblz.artistmodel.Artist;

/**
 * Created by root on 8/8/16.
 */
public class ArtistProvider extends ContentProvider {

    private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
    private static final String AUTHORITY = "ru.yandex.yamblz.artistprovider";
    private static final int ALL_ARTISTS_CODE = 1;

    private ArtistDbBackend dbBackend;

    static {
        URI_MATCHER.addURI(AUTHORITY, "artist", ALL_ARTISTS_CODE);
    }


    @Override
    public boolean onCreate() {
        dbBackend = new ArtistDbBackend(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        Cursor cursor = null;

        switch (URI_MATCHER.match(uri)) {
            case ALL_ARTISTS_CODE:

                cursor = dbBackend.getArtists();

                if(cursor.getCount() == 0) {
                    cursor = loadFromNetwork();
                }

                break;
            default:
                throw new IllegalArgumentException("Wrong URI: " + uri.toString());
        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        Log.d("contentproviderapp", "" + cursor.getColumnIndex(ArtistDbContract.Genre.GENRE_NAME));

        return cursor;

    }

    private Cursor loadFromNetwork() {
        Artist[] artists = ContentLoader.loadArtists();
        for (Artist artist : artists) {
            dbBackend.insertArtist(artist);
        }
        return dbBackend.getArtists();
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
