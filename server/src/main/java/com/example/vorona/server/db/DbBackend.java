package com.example.vorona.server.db;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.VisibleForTesting;
import android.util.Log;

import com.example.vorona.server.model.Singer;

import java.util.ArrayList;
import java.util.List;
import java.util.NavigableMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by user on 31/07/2016.
 */
public class DbBackend implements DbContract {

    private final DBHelper mDbOpenHelper;

    public DbBackend(Context context) {
        mDbOpenHelper = new DBHelper(context);
    }

    public List<Singer> getSingers() {
        SQLiteDatabase db = mDbOpenHelper.getWritableDatabase();
        List<Singer> singerList = new ArrayList<>();
        Cursor c = db.query(ARTISTS, null, null, null, null, null, null);
        if (c.moveToFirst()) {
            do {
                Singer singer = new Singer();
                setSinger(singer, c, db);
                singerList.add(singer);
            } while (c.moveToNext());
        }
        c.close();
        return singerList;
    }

    private void setSinger(Singer singer, Cursor c, SQLiteDatabase db) {
        singer.setId(c.getInt(c.getColumnIndex(Artist.ID)));
        singer.setName(c.getString(c.getColumnIndex(Artist.NAME)));
        singer.setBio(c.getString(c.getColumnIndex(Artist.BIO)));
        singer.setAlbums(c.getInt(c.getColumnIndex(Artist.ALBUM)));
        singer.setTracks(c.getInt(c.getColumnIndex(Artist.TRACKS)));
        singer.setCover_big(c.getString(c.getColumnIndex(Artist.COVER)));
        singer.setCover_small(c.getString(c.getColumnIndex(Artist.COVER_SMALL)));

        int relativeID = c.getInt(c.getColumnIndex(Artist.LOCAL_ID));
        List<String> initGenres = new ArrayList<>();
        Cursor genres = db.rawQuery("SELECT * FROM " + ARTIST_GENRES +
                        " LEFT JOIN " + GENRES + " ON " + ARTIST_GENRES + "." + ArtistGenre.GENRE_ID + " = " +
                        GENRES + "." + Genre.ID + " WHERE " + ARTIST_GENRES + "." + ArtistGenre.ARTIST_ID + " = ?",
                new String[]{Long.toString(relativeID)});
        if (genres.moveToFirst()) {
            do {
                initGenres.add(genres.getString(genres.getColumnIndex(Genre.GENRE)));
            } while (genres.moveToNext());
        }
        genres.close();
        singer.setGenres(initGenres);
    }

    public Singer getSinger(int id) {
        SQLiteDatabase db = mDbOpenHelper.getWritableDatabase();
        Singer singer = new Singer();
        Cursor c = db.query(ARTISTS, null, Artist.ID + " = ?",
                new String[]{Integer.toString(id)}, null, null, null);
        if (c.moveToFirst()) {
            setSinger(singer, c, db);
        }
        c.close();

        return singer;
    }

    public void insertSinger(Singer singer) {
        SQLiteDatabase db = mDbOpenHelper.getWritableDatabase();
        ContentValues values = createCV(singer);
        db.insert(ARTISTS, null, values);
        //inserting genres
        for (String g : singer.getGenres()) {
            ContentValues genre = new ContentValues();
            genre.put(Genre.GENRE, g);
            db.insert(GENRES, null, genre);
        }

        //lets take artist from table by its id and get his local id
        Cursor artist = db.query(ARTISTS, null, Artist.ID + " = ?",
                new String[]{Long.toString(singer.getId())}, null, null, null);

        if (artist.moveToFirst()) {
            int relative_id = artist.getInt(artist.getColumnIndex(Artist.LOCAL_ID));

            for (String g : singer.getGenres()) {
                //take genre's id
                Cursor genre = db.query(GENRES, null, Genre.GENRE + " = ?",
                        new String[]{g}, null, null, null);
                if (genre.moveToFirst()) {
                    //insert pair of artist and genre
                    int genre_id = genre.getInt(genre.getColumnIndex(Genre.ID));
                    ContentValues artistGenre = new ContentValues();
                    artistGenre.put(ArtistGenre.ARTIST_ID, relative_id);
                    artistGenre.put(ArtistGenre.GENRE_ID, genre_id);
                    db.insert(ARTIST_GENRES, null, artistGenre);
                }
                genre.close();
            }
        } else {
            Log.w("DbBackend", "Inserted but didn't find later");
        }
        artist.close();
    }

    public void insertSingerUnique(Singer singer) {
        SQLiteDatabase db = mDbOpenHelper.getWritableDatabase();
        ContentValues values = createCV(singer);
        Cursor presence = db.query(ARTISTS, null, Artist.NAME + " = ?",
                new String[]{singer.getName()}, null, null, null);
        if (presence.moveToFirst()) {
            presence.close();
            return;
        }
        db.insert(ARTISTS, null, values);
    }

    public void insertList(List<Singer> singers) {
        for (Singer singer : singers) {
            insertSinger(singer);
        }
    }

    public void insertListUnique(List<Singer> singers) {
        for (Singer singer : singers) {
            insertSingerUnique(singer);
        }
    }

    private ContentValues createCV(Singer singer) {
        ContentValues cv = new ContentValues();
        cv.put(Artist.ID, singer.getId());
        cv.put(Artist.NAME, singer.getName());
        cv.put(Artist.BIO, singer.getBio());
        cv.put(Artist.ALBUM, singer.getAlbums());
        cv.put(Artist.TRACKS, singer.getTracks());
        cv.put(Artist.COVER, singer.getCover_big());
        cv.put(Artist.COVER_SMALL, singer.getCover_small());
        return cv;
    }
}
