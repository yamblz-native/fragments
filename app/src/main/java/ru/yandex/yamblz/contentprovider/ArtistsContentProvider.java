package ru.yandex.yamblz.contentprovider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import ru.aleien.yapplication.database.DBContract;
import ru.aleien.yapplication.database.DBHelper;
import ru.aleien.yapplication.model.Artist;
import timber.log.Timber;

import static ru.aleien.yapplication.database.DBContract.allColumns;

/**
 * Created by aleien on 10.08.16.
 */

public class ArtistsContentProvider extends ContentProvider {
    static final String AUTHORITY = "ru.aleien.yapplication.provider";

    static final String ARTISTS_PATH = "Artists";
    static final int URI_ARTISTS = 1;
    static final int URI_ARTISTS_ID = 2;

    private static final UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, ARTISTS_PATH, URI_ARTISTS);
        uriMatcher.addURI(AUTHORITY, ARTISTS_PATH + "/#", URI_ARTISTS_ID);
    }

    DBHelper dbHelper;
    SQLiteDatabase db;

    @Override
    public boolean onCreate() {
        Timber.d("Content provider created");
        dbHelper = new DBHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Timber.v("Making query to content provider");
        Timber.d("Uri: " + uri.toString());
        switch (uriMatcher.match(uri)) {
            case URI_ARTISTS:
                Timber.v("Resolving query to ARTISTS");
                break;
            case URI_ARTISTS_ID:
                Timber.v("Resolving query to specific ARTIST");
                String id = uri.getLastPathSegment();

                if (TextUtils.isEmpty(selection)) {
                    selection = DBContract.Artists.ID + " = " + id;
                } else {
                    selection = selection + " AND " + DBContract.Artists.ID + " = " + id;
                }
                break;
            default:
                throw new IllegalArgumentException("Wrong URI: " + uri);
        }

        db = dbHelper.getReadableDatabase();
        return db.query(DBContract.Artists.TABLE,
                allColumns, selection, null, null, null, null);
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
