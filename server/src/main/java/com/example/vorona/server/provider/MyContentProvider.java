package com.example.vorona.server.provider;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.example.vorona.server.db.DBHelper;
import com.example.vorona.server.db.DbContract;
import com.example.vorona.server.model.Singer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.example.vorona.server.db.DbContract.ARTISTS;
import static com.example.vorona.server.db.DbContract.Artist.ALBUM;
import static com.example.vorona.server.db.DbContract.Artist.BIO;
import static com.example.vorona.server.db.DbContract.Artist.COVER;
import static com.example.vorona.server.db.DbContract.Artist.COVER_SMALL;
import static com.example.vorona.server.db.DbContract.Artist.GENRES;
import static com.example.vorona.server.db.DbContract.Artist.ID;
import static com.example.vorona.server.db.DbContract.Artist.LOCAL_ID;
import static com.example.vorona.server.db.DbContract.Artist.NAME;
import static com.example.vorona.server.db.DbContract.Artist.TRACKS;

public class MyContentProvider extends ContentProvider implements DbContract {

    static final String PROVIDER_NAME = "ru.yandex.yamblz.database";
    static final String URL = "content://" + PROVIDER_NAME + "/" + ARTISTS;
    static final Uri CONTENT_URI = Uri.parse(URL);
    static final int ART = 1;
    static final int ART_ID = 2;

    private DBHelper dbHelper;

    static final UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, ARTISTS, ART);
        uriMatcher.addURI(PROVIDER_NAME, ARTISTS+"/#", ART_ID);
    }

    @Override
    public boolean onCreate() {
        Log.w("ContentProvider", "onCreate");
        Context context = getContext();
        dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        return db != null;
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        int count = 0;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        switch (uriMatcher.match(uri)) {
            case ART:
                count = db.delete(ARTISTS, selection, selectionArgs);
                break;

            case ART_ID:
                String id = uri.getPathSegments().get(1);
                count = db.delete(ARTISTS, ID + " = " + id +
                        (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
                break;

            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public String getType(@NonNull Uri uri) {
        switch (uriMatcher.match(uri)) {
            case ART:
                return "vnd.android.cursor.dir/vnd.ru.yandex.yamblz.database.artists";
            case ART_ID:
                return "vnd.android.cursor.item/vnd.ru.yandex.yamblz.database.artists";

            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor presence = db.query(ARTISTS, null, NAME + " = ?", new String[]{values.get(NAME).toString()}, null, null, null);
        if (presence.moveToFirst()) {
            presence.close();
            return null;
        }
        presence.close();
        long rowID = db.insert(ARTISTS, "", values);
        if (rowID > 0) {
            Uri _uri = ContentUris.withAppendedId(CONTENT_URI, rowID);
            getContext().getContentResolver().notifyChange(_uri, null);
            return _uri;
        }
        throw new SQLException("Failed to add a record into " + uri);
    }

    public Cursor query(@NonNull Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        Log.d("Provider", "query, " + uri.toString());

        switch (uriMatcher.match(uri)) {
            case ART:
                Log.d("Provider", "URI_CONTACTS");
                if (TextUtils.isEmpty(sortOrder)) {
                    sortOrder = NAME + " ASC";
                }
                break;
            case ART_ID:
                String id = uri.getLastPathSegment();
                Log.d("Provider", "URI_CONTACTS_ID, " + id);
                if (TextUtils.isEmpty(selection)) {
                    selection = ID + " = " + id;
                } else {
                    selection = selection + " AND " + ID + " = " + id;
                }
                break;
            default:
                throw new IllegalArgumentException("Wrong URI: " + uri);
        }
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query(ARTISTS, projection, selection,
                selectionArgs, null, null, sortOrder);

        cursor.setNotificationUri(getContext().getContentResolver(),
                CONTENT_URI);
        System.out.println(cursor.getCount());
        return cursor;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        int count;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        switch (uriMatcher.match(uri)) {
            case ART:
                count = db.update(ARTISTS, values, selection, selectionArgs);
                break;

            case ART_ID:
                count = db.update(ARTISTS, values, ID + " = " + uri.getPathSegments().get(1) +
                        (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
                break;

            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }


}
