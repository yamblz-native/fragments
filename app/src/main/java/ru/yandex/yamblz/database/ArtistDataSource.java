package ru.yandex.yamblz.database;

/**
 * Created by dan on 10.08.16.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;


import org.w3c.dom.Comment;

import java.util.ArrayList;
import java.util.List;

import ru.yandex.yamblz.Model.Artist;

/**
 * Created by dan on 21.07.16.
 */
public class ArtistDataSource {

    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;

    private String[] allColumns = new String[]{MySQLiteHelper.ARTIST_ID,
            MySQLiteHelper.ARTIST_DESCRIPTION, MySQLiteHelper.ARTIST_NAME, MySQLiteHelper.ARTIST_SMALL_IMAGE,
            MySQLiteHelper.ARTIST_BIG_IMAGE, MySQLiteHelper.ARTIST_TRACKS, MySQLiteHelper.ARTIST_ALBUMS,
            MySQLiteHelper.ARTIST_GENRES};

    public ArtistDataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Artist createArtist(String id, String description, String name, String smallImage,
                               String bigImage, String tracks, String albums, String genres) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.ARTIST_DESCRIPTION, description);
        values.put(MySQLiteHelper.ARTIST_NAME, name);
        values.put(MySQLiteHelper.ARTIST_SMALL_IMAGE, smallImage);
        values.put(MySQLiteHelper.ARTIST_BIG_IMAGE, bigImage);
        values.put(MySQLiteHelper.ARTIST_TRACKS, tracks);
        values.put(MySQLiteHelper.ARTIST_ALBUMS, albums);
        values.put(MySQLiteHelper.ARTIST_GENRES, genres);
        long insertId = database.insert(MySQLiteHelper.TABLE_ARTISTS, null,
                values);
        Cursor cursor = database.query(MySQLiteHelper.TABLE_ARTISTS,
                allColumns, MySQLiteHelper.ARTIST_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Artist artist= cursorToArtist(cursor);
        cursor.close();
        return artist;
    }

    public List<Artist> getAllArtists() {
        List<Artist> comments = new ArrayList<Artist>();


        Cursor cursor = database.query(MySQLiteHelper.TABLE_ARTISTS,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Artist artist = cursorToArtist(cursor);
            comments.add(artist);
            if (comments.size() == 317) {
                break;                    ////продолжает писать в бд по кругу?! wtf(поэтому здесь break)
            }
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return comments;
    }

    private Artist cursorToArtist(Cursor cursor) {
        Artist artist = new Artist();
        artist.setId(cursor.getLong(0));
        artist.setDescription(cursor.getString(1));
        artist.setName(cursor.getString(2));
        artist.getCover().setSmallCoverImage(cursor.getString(3));
        artist.getCover().setBigCoverImage(cursor.getString(4));
        artist.setTracks(Long.parseLong(cursor.getString(5)));
        artist.setAlbums(Long.parseLong(cursor.getString(6)));
        artist.setGenresString(cursor.getString(7));
        return artist;
    }


}
