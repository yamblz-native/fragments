package com.example.vorona.server.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.VisibleForTesting;

import com.example.vorona.server.model.Singer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by user on 31/07/2016.
 */
public class DbProvider implements DbContract {

    private final DBHelper mDbOpenHelper;

    public DbProvider(Context context) {
        mDbOpenHelper = new DBHelper(context);
    }

    public List<Singer> getSingers() {
        SQLiteDatabase db = mDbOpenHelper.getWritableDatabase();
        String table = ARTISTS;
        List<Singer> singerList = new ArrayList<>();
        Cursor c = db.query(table, null, null, null, null, null, null);
        if (c.moveToFirst()) {
            do {
                Singer singer = new Singer();
                singer.setId(c.getInt(c.getColumnIndex("id")));
                singer.setName(c.getString(c.getColumnIndex("name")));
                singer.setBio(c.getString(c.getColumnIndex("bio")));
                singer.setAlbums(c.getInt(c.getColumnIndex("albums")));
                singer.setTracks(c.getInt(c.getColumnIndex("tracks")));
                singer.setCover_big(c.getString(c.getColumnIndex("cover")));
                singer.setGenres(c.getString(c.getColumnIndex("genres")));
                singer.setCover_small(c.getString(c.getColumnIndex("cover_small")));
                singerList.add(singer);
            } while (c.moveToNext());
        }
        c.close();
        return singerList;
    }

    public void insertSinger(Singer singer) {
        SQLiteDatabase db = mDbOpenHelper.getWritableDatabase();
        ContentValues values = createCV(singer);
        db.beginTransaction();
        try {
            db.insertOrThrow(ARTISTS, null, values);
            db.beginTransaction();
        }
        finally {
            db.endTransaction();
        }

    }

    private ContentValues createCV(Singer singer) {
        ContentValues cv = new ContentValues();
        cv.put("id", singer.getId());
        cv.put("name", singer.getName());
        cv.put("bio", singer.getBio());
        cv.put("albums", singer.getAlbums());
        cv.put("tracks", singer.getTracks());
        cv.put("cover", singer.getCover_big());
        cv.put("genres", singer.getGenres());
        cv.put("cover_small", singer.getCover_small());
        return cv;
    }
}
