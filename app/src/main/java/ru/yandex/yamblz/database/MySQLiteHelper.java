package ru.yandex.yamblz.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

/**
 * Created by dan on 21.07.16.
 */
public class MySQLiteHelper extends SQLiteOpenHelper {

    /*
    Artist:
    private long id ;
    private String name = "";
    private long tracks;
    private long albums;
    private String link = "";
    private String description = "";
    private List<String> genres;
    private Cover cover;

    Cover:
    private String smallCoverImage = "";
    private String bigCoverImage = "";
    */


    public static final String TABLE_ARTISTS = "artists";
    public static final String ARTIST_ID = "ARTIST_ID";
    public static final String ARTIST_DESCRIPTION = "ARTIST_DESCRIPTION";
    public static final String ARTIST_NAME = "ARTIST_NAME";
    public static final String ARTIST_SMALL_IMAGE= "ARTIST_SMALL_IMAGE";
    public static final String ARTIST_BIG_IMAGE= "ARTIST_BIG_IMAGE";
    public static final String ARTIST_TRACKS= "ARTIST_TRACKS";
    public static final String ARTIST_ALBUMS= "ARTIST_ALBUMS";
    public static final String ARTIST_GENRES= "ARTIST_GENRES";





    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table " + TABLE_ARTISTS +
            " (ARTIST_ID integer primary key not null, ARTIST_DESCRIPTION text, ARTIST_NAME text not null, ARTIST_SMALL_IMAGE text, ARTIST_BIG_IMAGE text, ARTIST_TRACKS text, ARTIST_ALBUMS text, ARTIST_GENRES text);";

    public MySQLiteHelper(Context context) {
        super(context, "artists", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + "artists");
        onCreate(db);
    }
}
