package ru.yandex.provider.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by root on 8/9/16.
 */
public class ArtistDbOpenHelper extends SQLiteOpenHelper implements ArtistDbContract {

    private static final int DB_VERSION = 1;

    public ArtistDbOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);

        db.setForeignKeyConstraintsEnabled(true);
        db.enableWriteAheadLogging();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + ARTIST + " (" +
            Artist.NAME + " TEXT UNIQUE NOT NULL, " +
            Artist.TRACKS + " INTEGER, " +
            Artist.ALBUMS + " INTEGER, " +
            Artist.LINK + " TEXT, " +
            Artist.DESCRIPTION + " TEXT, " +
            Artist.BIG_COVER_LINK + " TEXT, " +
            Artist.SMALL_COVER_LINK + " TEXT " + ")");

        db.execSQL("CREATE TABLE " + GENRE + " ( " +
            Genre.GENRE_NAME + " TEXT NOT NULL " + ")");

        db.execSQL("CREATE TABLE " + ARTIST_GENRE + " ( " +
            ArtistGenre.ARTIST_ID + " INTEGER REFERENCES " + ARTIST + "(" + Artist.ID + "), " +
            ArtistGenre.GENRE_ID + " INTEGER REFERENCES " + GENRE + "(" + Genre.ID + ") " + ")");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        throw new IllegalStateException("onUpgrade() method is not implemented!");
    }
}
