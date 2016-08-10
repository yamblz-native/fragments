package ru.yandex.yamblz.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import ru.aleien.yapplication.model.Artist;
import rx.Observable;

import static ru.aleien.yapplication.database.DBContract.GenreToArtist.ARTIST_ID;
import static ru.aleien.yapplication.database.DBContract.GenreToArtist.GENRE_ID;
import static ru.aleien.yapplication.database.DBContract.GenreToArtist.TABLE;
import static ru.aleien.yapplication.database.DBContract.allColumns;

public class DBBackend {

    private DBHelper dbOpenHelper;

    @Inject
    public DBBackend(DBHelper helper) {
        dbOpenHelper = helper;
    }

    public void insertArtist(Artist artist) {
        insertArtist(artist.id,
                artist.name,
                artist.tracks,
                artist.albums,
                artist.link,
                artist.description,
                artist.cover.small,
                artist.cover.big,
                artist.genres);
    }

    void insertArtist(int id,
                      String name,
                      int tracks,
                      int albums,
                      String link,
                      String description,
                      String small_cover,
                      String big_cover,
                      List<String> genres) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        db.beginTransaction();

        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put(DBContract.Artists.ID, id);
            contentValues.put(DBContract.Artists.NAME, name);
            contentValues.put(DBContract.Artists.TRACKS, tracks);
            contentValues.put(DBContract.Artists.ALBUMS, albums);
            contentValues.put(DBContract.Artists.LINK, link);
            contentValues.put(DBContract.Artists.DESCRIPTION, description);
            contentValues.put(DBContract.Artists.SMALL_COVER, small_cover);
            contentValues.put(DBContract.Artists.BIG_COVER, big_cover);

            long artistId = db.insert(DBContract.Artists.TABLE, null, contentValues);
            List<Long> genresIds = insertGenres(genres);

            insertRelation(artistId, genresIds);

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    void insertRelation(long artistId, List<Long> genresIds) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        for (Long genreId : genresIds) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(ARTIST_ID, artistId);
            contentValues.put(GENRE_ID, genreId);
            db.insert(TABLE, null, contentValues);
        }
    }

    List<Long> insertGenres(List<String> genres) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        List<Long> rowIds = new ArrayList<>();
        ContentValues contentValues = new ContentValues();
        for (String genre : genres) {
            contentValues.put(DBContract.Genres.NAME, genre);
            long rowId = db.insertWithOnConflict(DBContract.Genres.TABLE, null, contentValues, SQLiteDatabase.CONFLICT_IGNORE);
            rowIds.add(rowId);
        }

        return rowIds;
    }

    public Observable<List<Artist>> getAllArtists() {
        return Observable.fromCallable(this::loadArtists);
    }

    List<Artist> loadArtists() {
        List<Artist> artists = new ArrayList<>();
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        Cursor cursor = db.query(DBContract.Artists.TABLE,
                allColumns, null, null, null, null, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            artists.add(cursorToArtist(cursor));
            cursor.moveToNext();
        }

        cursor.close();

        return artists;
    }

    public void clearArtists() {
        dbOpenHelper.getWritableDatabase().execSQL("DELETE FROM " + DBContract.Artists.TABLE);
    }

    private Artist cursorToArtist(Cursor cursor) {
        return new Artist(cursor.getInt(cursor.getColumnIndex(DBContract.Artists.ID)),
                cursor.getString(cursor.getColumnIndex(DBContract.Artists.NAME)),
                new ArrayList<>(),
                cursor.getInt(cursor.getColumnIndex(DBContract.Artists.TRACKS)),
                cursor.getInt(cursor.getColumnIndex(DBContract.Artists.ALBUMS)),
                cursor.getString(cursor.getColumnIndex(DBContract.Artists.LINK)),
                cursor.getString(cursor.getColumnIndex(DBContract.Artists.DESCRIPTION)),
                new Artist.Cover(cursor.getString(cursor.getColumnIndex(DBContract.Artists.SMALL_COVER)),
                        cursor.getString(cursor.getColumnIndex(DBContract.Artists.BIG_COVER))));
    }
}
