package com.example.vorona.server.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.example.vorona.server.db.DBHelper;
import com.example.vorona.server.db.DbContract;

import java.util.HashMap;

import static com.example.vorona.server.db.DbContract.Artist.ID;

/**
 * Created by vorona on 03.08.16.
 */

public class ArtistContentProvider extends ContentProvider implements DbContract{

    static final String PROVIDER_NAME = "ru.yandex.yamblz.artists";
    static final String URL = "content://" + PROVIDER_NAME + "/artists";
    static final Uri CONTENT_URI = Uri.parse(URL);

    static final int STUDENTS = 1;
    static final int STUDENT_ID = 2;

    static final UriMatcher uriMatcher;
    static{
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "students", STUDENTS);
        uriMatcher.addURI(PROVIDER_NAME, "students/#", STUDENT_ID);
    }

    private SQLiteDatabase db;


    @Override
    public boolean onCreate() {
        Context context = getContext();
        DBHelper dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
        return db != null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        /**
         * Add a new student record
         */
        long rowID = db.insert(	ARTISTS, "", values);

        /**
         * If record is added successfully
         */

        if (rowID > 0)
        {
            Uri _uri = ContentUris.withAppendedId(CONTENT_URI, rowID);
            getContext().getContentResolver().notifyChange(_uri, null);
            return _uri;
        }
        throw new SQLException("Failed to add a record into " + uri);
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(ARTISTS);

//        switch (uriMatcher.match(uri)) {
//            case STUDENTS:
//                qb.setProjectionMap(STUDENTS_PROJECTION_MAP);
//                break;
//
//            case STUDENT_ID:
//                qb.appendWhere( _ID + "=" + uri.getPathSegments().get(1));
//                break;
//
//            default:
//                throw new IllegalArgumentException("Unknown URI " + uri);
//        }

        Cursor c = qb.query(db,	projection,	selection, selectionArgs, null, null, sortOrder);

        /**
         * register to watch a content URI for changes
         */
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int count = 0;

        switch (uriMatcher.match(uri)){
            case STUDENTS:
                count = db.delete(ARTISTS, selection, selectionArgs);
                break;

            case STUDENT_ID:
                String id = uri.getPathSegments().get(1);
                count = db.delete( ARTISTS, ID +  " = " + id +
                        (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
                break;

            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int count = 0;

        switch (uriMatcher.match(uri)){
            case STUDENTS:
                count = db.update(ARTISTS, values, selection, selectionArgs);
                break;

            case STUDENT_ID:
                count = db.update(ARTISTS, values, ID + " = " + uri.getPathSegments().get(1) +
                        (!TextUtils.isEmpty(selection) ? " AND (" +selection + ')' : ""), selectionArgs);
                break;

            default:
                throw new IllegalArgumentException("Unknown URI " + uri );
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)){
            case STUDENTS:
                return "vnd.android.cursor.dir/vnd.example.students";
            case STUDENT_ID:
                return "vnd.android.cursor.item/vnd.example.students";

            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }
}