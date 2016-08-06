package com.example.vorona.server.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static com.example.vorona.server.db.DbContract.Artist.*;

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
                + ID +" integer,"
                + NAME + " text,"
                + BIO + " text,"
                + ALBUM + " integer,"
                + TRACKS + " integer,"
                + COVER + " text,"
                + GENRES + " text,"
                + COVER_SMALL + " text"
                +");");

        db.execSQL("CREATE INDEX idx_" + ID +
                " ON " + ARTISTS + "(" + ID + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE " + ARTISTS);
        db.setVersion(newVersion);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        Cursor cursor = db.rawQuery("PRAGMA journal_mode = WAL;", null);
        cursor.close();
    }
}
