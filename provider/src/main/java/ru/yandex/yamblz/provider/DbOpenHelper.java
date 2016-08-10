package ru.yandex.yamblz.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import ru.yandex.yamblz.singerscontracts.SingersContract;

import static ru.yandex.yamblz.provider.DbContract.DB_NAME;
import static ru.yandex.yamblz.provider.DbContract.Genres;
import static ru.yandex.yamblz.provider.DbContract.Singers;
import static ru.yandex.yamblz.provider.DbContract.SingersGenres;

public class DbOpenHelper extends SQLiteOpenHelper {

    public static final int DB_VERSION = 2;

    public DbOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createSingersTable(db);
        createGenresTable(db);
        createSingersGenresTable(db);
        createSingersView(db);
        addIndexes(db);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        db.execSQL("PRAGMA foreign_keys=ON;");
        db.enableWriteAheadLogging();
    }

    private void createSingersTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + Singers.TABLE_NAME + "(" +
            Singers.ID + " INTEGER PRIMARY KEY, " +
            Singers.NAME + " TEXT NOT NULL, " +
            Singers.DESCRIPTION + " TEXT, " +
            Singers.TRACKS + " INTEGER, " +
            Singers.ALBUMS + " INTEGER, " +
            Singers.COVER_SMALL + " TEXT, " +
            Singers.COVER_BIG + " TEXT)");
    }

    private void createGenresTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + Genres.TABLE_NAME + "(" +
            Genres.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            Genres.NAME + " TEXT)");
    }

    private void createSingersGenresTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + SingersGenres.TABLE_NAME + "(" +
            SingersGenres.SINGER_ID + " INTEGER REFERENCES " + Singers.TABLE_NAME +
                "(" + Singers.ID + ") ON UPDATE CASCADE ON DELETE CASCADE, " +
            SingersGenres.GENRE_ID + " INTEGER REFERENCES " + Genres.TABLE_NAME +
                "(" + Genres.ID + ") ON UPDATE CASCADE ON DELETE CASCADE)");
    }

    private void createSingersView(SQLiteDatabase db) {
        /*
        (SELECT singers.id AS id, singers.name AS name, group_concat(genres.name, ',') AS genres,
               singers.tracks AS tracks, singers.albums AS albums, singers.description AS description,
               singers.cover_small AS cover_small, singers.cover_big AS cover_big FROM singers
               LEFT JOIN singers_genres on singers.id=singers_genres.singer_id LEFT JOIN genres
               ON singers_genres.genre_id=genres.id GROUP BY singers.id)
         */
        String table = String.format(
                "SELECT %s, %s, group_concat(%s, ',') AS %s, %s, %s, %s, %s, %s FROM %s LEFT JOIN " +
                "%s on %s=%s LEFT JOIN %s ON %s=%s GROUP BY %s",
                Singers.TABLE_NAME + "." + Singers.ID + " AS " + SingersContract.Singers.ID,
                Singers.TABLE_NAME + "." + Singers.NAME + " AS " + SingersContract.Singers.NAME,
                Genres.TABLE_NAME + "." + Genres.NAME,
                SingersContract.Singers.GENRES,
                Singers.TABLE_NAME + "." + Singers.TRACKS + " AS " + SingersContract.Singers.TRACKS,
                Singers.TABLE_NAME + "." + Singers.ALBUMS + " AS " + SingersContract.Singers.ALBUMS,
                Singers.TABLE_NAME + "." + Singers.DESCRIPTION + " AS " + SingersContract.Singers.DESCRIPTION,
                Singers.TABLE_NAME + "." + Singers.COVER_SMALL + " AS " + SingersContract.Singers.COVER_SMALL,
                Singers.TABLE_NAME + "." + Singers.COVER_BIG + " AS " + SingersContract.Singers.COVER_BIG,
                Singers.TABLE_NAME,
                SingersGenres.TABLE_NAME,
                Singers.TABLE_NAME + "." + Singers.ID,
                SingersGenres.TABLE_NAME + "." + SingersGenres.SINGER_ID,
                Genres.TABLE_NAME,
                SingersGenres.TABLE_NAME + "." + SingersGenres.GENRE_ID,
                Genres.TABLE_NAME + "." + Genres.ID,
                Singers.TABLE_NAME + "." + Singers.ID);
        db.execSQL("CREATE VIEW " + Singers.CONTRACT_VIEW + " AS " + table);
    }

    private void addIndexes(SQLiteDatabase db) {
        db.execSQL("CREATE INDEX " + Singers.NAME_INDEX + " ON " + Singers.TABLE_NAME +
                "(" + Singers.NAME + ")");
        db.execSQL("CREATE INDEX " + SingersGenres.SINGER_ID_INDEX + " ON " + SingersGenres.TABLE_NAME +
                "(" + SingersGenres.SINGER_ID + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(newVersion == 2) {
            createSingersView(db);
        }
    }
}
