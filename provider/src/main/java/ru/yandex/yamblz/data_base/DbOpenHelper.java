package ru.yandex.yamblz.data_base;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class DbOpenHelper extends SQLiteOpenHelper implements DbContract {

    private static final int DB_VERSION = 12;
    private static final String SQL_CREATE_ARTISTS = "CREATE TABLE " + ARTISTS + "(" +
            Artists.NAME + " TEXT UNIQUE NOT NULL, " +
            Artists.LINK + " TEXT, " +
            Artists.TRACKS + " INTEGER NOT NULL, " +
            Artists.ALBUMS + " INTEGER NOT NULL, " +
            Artists.COVER_BIG + " TEXT, " +
            Artists.COVER_SMALL + " TEXT, " +
            Artists.DESCRIPTION + " TEXT" + ")";
    private static final String SQL_CREATE_GENRES = "CREATE TABLE " + ARTISTS_GENRES + "(" +
            ArtistsGenres.ARTIST_ID + " INTEGER NOT NULL, " +
            ArtistsGenres.GENRE_ID + " INTEGER NOT NULL" + ")";
    private static final String SQL_CREATE_ARTIST_GENRE = "CREATE TABLE " + GENRES + "(" +
            Genres.NAME + " TEXT NOT NULL UNIQUE" + ")";

    public DbOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ARTISTS);
        db.execSQL(SQL_CREATE_ARTIST_GENRE);
        db.execSQL(SQL_CREATE_GENRES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE " + ARTISTS);
        db.execSQL("DROP TABLE " + GENRES);
        db.execSQL("DROP TABLE " + ARTISTS_GENRES);
        onCreate(db);
    }
}
