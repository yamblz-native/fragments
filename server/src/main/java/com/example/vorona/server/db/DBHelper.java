package com.example.vorona.server.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static com.example.vorona.server.db.DbContract.Artist.*;
import static com.example.vorona.server.db.DbContract.Genre.*;
import static com.example.vorona.server.db.DbContract.ArtistGenre.*;

/**
 * Class for database access.
 */
public class DBHelper extends SQLiteOpenHelper implements DbContract{
    private static final int DB_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.w("DBHelper", "Creating table");
        db.execSQL("create table " + ARTISTS + " ("
                + LOCAL_ID + " integer primary key autoincrement,"
                + Artist.ID +" integer,"
                + NAME + " text,"
                + BIO + " text,"
                + ALBUM + " integer,"
                + TRACKS + " integer,"
                + COVER + " text,"
                + COVER_SMALL + " text"
                +");");

        db.execSQL("CREATE INDEX idx_" + Artist.ID +
                " ON " + ARTISTS + "(" + Artist.ID + ")");

        db.execSQL("create table " + GENRES + " ("
                + Genre.ID +" integer primary key autoincrement,"
                + GENRE + " text"
                +");");

        db.execSQL("CREATE INDEX idx_genres_" + Genre.ID +
                " ON " + GENRES + "(" + Genre.ID + ")");

        db.execSQL("create table " + ARTIST_GENRES + " ("
                + ARTIST_ID + " integer,"
                + GENRE_ID + " integer"
                +");");

        db.execSQL("CREATE INDEX idx_artist_" + ARTIST_ID +
                " ON " + ARTIST_GENRES + "(" + ARTIST_ID + ")");
        db.execSQL("CREATE INDEX idx_genre_" + GENRE_ID +
                " ON " + ARTIST_GENRES + "(" + GENRE_ID + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE " + ARTISTS);
        db.execSQL("DROP TABLE " + GENRES);
        db.execSQL("DROP TABLE " + ARTIST_GENRES);
        db.setVersion(newVersion);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        Cursor cursor = db.rawQuery("PRAGMA journal_mode = WAL;", null);
        cursor.close();
    }
}
