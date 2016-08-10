package ru.yandex.yamblz.provider;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.yandex.yamblz.singerscontracts.Singer;
import ru.yandex.yamblz.singerscontracts.SingersContract;

import static ru.yandex.yamblz.provider.DbContract.Genres;
import static ru.yandex.yamblz.provider.DbContract.Singers;
import static ru.yandex.yamblz.provider.DbContract.SingersGenres;

public class DbBackend {

    private DbOpenHelper mDbOpenHelper;

    public DbBackend(Context context) {
        mDbOpenHelper = new DbOpenHelper(context);
    }

    public DbBackend(DbOpenHelper dbOpenHelper) {
        mDbOpenHelper = dbOpenHelper;
    }

    /**
     * Returns {@link Cursor} of singers with the args applied to the query.
     * The columns names are taken from {@link ru.yandex.yamblz.singerscontracts.SingersContract}
     * The arguments should use {@link ru.yandex.yamblz.singerscontracts.SingersContract}
     * @param projection the columns needed
     * @param selection selection filter
     * @param selectionArgs args for the selection
     * @param sortOrder sorting order
     * @return cursor of singers
     */
    @Nullable
    public Cursor getSingers(@Nullable String[] projection, @Nullable String selection,
                             @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase database = mDbOpenHelper.getReadableDatabase();
        Cursor cursor = database.query(Singers.CONTRACT_VIEW, projection, selection, selectionArgs, null,
                null, sortOrder);

        if(cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    /**
     * Clears all tables from the previous data and inserts the new data.
     * This methods runs in a transaction.
     * @param singers the singers to insert
     * @return {@code true} if transaction was successful
     */
    public boolean forceSingersUpdate(List<Singer> singers) {
        SQLiteDatabase db = mDbOpenHelper.getWritableDatabase();
        boolean success = true;
        try {
            db.beginTransactionNonExclusive();

            truncateTable(Singers.TABLE_NAME);
            truncateTable(Genres.TABLE_NAME);
            truncateTable(SingersGenres.TABLE_NAME);

            success &= insertSingers(singers);
            success &= insertGenres(Singer.extractGenres(singers));
            insertArtistGenres(singers);
            if (success) {
                db.setTransactionSuccessful();
            }
        } catch (Exception e) {
            success = false;
        } finally {
            db.endTransaction();
        }
        return success;
    }

    /**
     * Clears table with the given name
     * @param tableName table name to clear
     */
    void truncateTable(String tableName) {
        SQLiteDatabase db = mDbOpenHelper.getWritableDatabase();
        db.execSQL("DELETE FROM " + tableName);
    }

    /**
     * Inserts genre to the genres table
     * @param genre genre to insert
     * @return {@code true} if successfully inserted
     */
    public boolean insertGenre(String genre) {
        SQLiteDatabase db = mDbOpenHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Genres.NAME, genre);
        return db.insert(Genres.TABLE_NAME, null, cv) != -1;
    }

    /**
     * Inserts genres to the genres table
     * @param genres genres to insert
     * @return {@code true} if all genres were inserted, {@code false} if at least one of them failed
     */
    public boolean insertGenres(List<String> genres) {
        boolean success = true;
        for(String genre : genres) {
            success &= insertGenre(genre);
        }
        return success;
    }

    /**
     * Inserts singer to the singers table
     * @param singer the singer to insert
     * @return {@code true} if the singer was inserted
     */
    public boolean insertSinger(Singer singer) {
        SQLiteDatabase db = mDbOpenHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Singers.ID, singer.getId());
        cv.put(Singers.NAME, singer.getName());
        cv.put(Singers.DESCRIPTION, singer.getDescription());
        cv.put(Singers.TRACKS, singer.getTracks());
        cv.put(Singers.ALBUMS, singer.getAlbums());
        cv.put(Singers.COVER_SMALL, singer.getCover().getSmall());
        cv.put(Singers.COVER_BIG, singer.getCover().getBig());
        return (db.insert(Singers.TABLE_NAME, null, cv) != -1);
    }

    /**
     * Inserts singers to the singers table
     * @param singers the singers to insert
     * @return {@code true} if all of the singers were inserted
     */
    public boolean insertSingers(List<Singer> singers) {
        boolean success = true;
        for(Singer singer : singers) {
            success &= insertSinger(singer);
        }
        return success;
    }

    /**
     * Fills artists_genres table from singers collection
     * NOTE: {@link DbContract.SingersGenres} contains foreign keys constraints, so singers and
     * genres should exist at the tables
     * @param singers the singers to insert
     */
    public void insertArtistGenres(List<Singer> singers) {
        List<String> genres = Singer.extractGenres(singers);
        List<Long> ids = selectGenresIds(genres);
        Map<String, Long> genre2id = new HashMap<>();
        for(int i = 0; i < genres.size(); i++) {
            genre2id.put(genres.get(i), ids.get(i));
        }

        SQLiteDatabase db = mDbOpenHelper.getWritableDatabase();
        for(Singer singer : singers) {
            for(String genre : singer.getGenres()) {
                ContentValues cv = new ContentValues();
                cv.put(SingersGenres.SINGER_ID, singer.getId());
                cv.put(SingersGenres.GENRE_ID, genre2id.get(genre));
                db.insert(SingersGenres.TABLE_NAME, null, cv);
            }
        }
    }

    /**
     * Select ids for the given list of genres
     * @param genres the genres
     * @return the ids, some ids can be {@code -1} if no rows were found
     */
    public List<Long> selectGenresIds(List<String> genres) {
        List<Long> ids = new ArrayList<>();
        for(String genre : genres) {
            ids.add(selectGenreId(genre));
        }
        return ids;
    }

    /**
     * Selects id for the given genre
     * @param genre the genre
     * @return the id of the given genre, {@code -1} if not found
     */
    public long selectGenreId(String genre) {
        SQLiteDatabase db = mDbOpenHelper.getReadableDatabase();
        Cursor cursor = db.query(Genres.TABLE_NAME, null, Genres.NAME + "=?",
                new String[] {genre}, null, null, null);
        if(cursor != null) {
            cursor.moveToFirst();
            return cursor.getLong(cursor.getColumnIndex(Genres.ID));
        }
        return -1;
    }
}
