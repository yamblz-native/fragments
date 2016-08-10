package ru.yandex.yamblz.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static ru.yandex.yamblz.model.ArtistContract.DB_NAME;

public class DbOpenHelper extends SQLiteOpenHelper {
    private static final int DB_VERSION = 2;

    public DbOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        addArtistTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.rawQuery("DROP TABLE " + ArtistContract.Artist.TABLE_NAME, null);
    }

    private void addArtistTable(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE " + ArtistContract.Artist.TABLE_NAME + "(" +
                        ArtistContract.Artist.ID + " INTEGER PRIMARY KEY, " +
                        ArtistContract.Artist.NAME + " TEXT NOT NULL, " +
                        ArtistContract.Artist.TRACKS + " INTEGER, " +
                        ArtistContract.Artist.ALBUMS + " INTEGER, " +
                        ArtistContract.Artist.GENRES + " TEXT, " +
                        ArtistContract.Artist.BIG_COVER + " TEXT, " +
                        ArtistContract.Artist.SMALL_COVER + " TEXT, " +
                        ArtistContract.Artist.URL + " TEXT, " +
                        ArtistContract.Artist.DESCRIPTION + " TEXT" +
                        ");"
        );
    }
}
