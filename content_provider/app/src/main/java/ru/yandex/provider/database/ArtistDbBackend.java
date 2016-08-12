package ru.yandex.provider.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.Closeable;

/**
 * Created by root on 8/9/16.
 */
public class ArtistDbBackend implements ArtistDbContract, Closeable {

    private ArtistDbOpenHelper dbOpenHelper;

    public ArtistDbBackend(Context context) {
        dbOpenHelper = new ArtistDbOpenHelper(context.getApplicationContext());
    }

    public Cursor getArtists() {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();

        String tables = ARTIST + " LEFT JOIN " + ARTIST_GENRE + " ON " +
                ARTIST + "." + Artist.ID + "=" + ARTIST_GENRE + "." + ArtistGenre.ARTIST_ID +
                " LEFT JOIN " + GENRE + " ON " +
                GENRE + "." + Genre.ID + "=" + ARTIST_GENRE + "." + ArtistGenre.GENRE_ID;

        String[] columns = new String[]{ARTIST + "." + Artist.NAME,
                ARTIST + "." + Artist.TRACKS,
                ARTIST + "." + Artist.ALBUMS,
                ARTIST + "." + Artist.LINK,
                ARTIST + "." + Artist.DESCRIPTION,
                ARTIST + "." + Artist.BIG_COVER_LINK,
                ARTIST + "." + Artist.SMALL_COVER_LINK,
                "group_concat" + "(" + GENRE + "." + Genre.GENRE_NAME + ", ', ') as " + Genre.GENRE_NAME};

        String orderBy = Artist.NAME + " DESC";

        String groupBy = Artist.NAME;

        Cursor c = db.query(tables, columns,
                null, null, groupBy, null, orderBy);

        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    public void insertArtist(ru.yandex.yamblz.artistmodel.Artist artist) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        db.beginTransactionNonExclusive();
        try {
            Long artistId = queryArtistByName(db, artist.getName());
            if (artistId == null) {
                artistId = createArtist(db, artist);
                insertGenres(db, artist.getGenres(), artistId);
            } else {
                updateArtist(db, artist);
            }

            db.setTransactionSuccessful();

        } finally {
            db.endTransaction();
        }
    }

    @Override
    public void close() {
        dbOpenHelper.close();
    }

    private void updateArtist(SQLiteDatabase db, ru.yandex.yamblz.artistmodel.Artist artist) {
        db.update(ARTIST, Artists.artistToContentValues(artist), Artist.NAME + "=?", new String[] { artist.getName() });
    }

    private void insertGenres(SQLiteDatabase db, String[] genres, Long artistId) {

        for (String genre : genres) {
            Long genreId = queryGenreByName(db, genre);

            if(genreId == null) {
                genreId = createGenre(db, genre);
            }
            Long artistGenreRelationId = queryArtistGenreRelation(db, artistId, genreId);

            if(artistGenreRelationId == null) {
                createArtistGenreRelation(db, artistId, genreId);
            }

        }
    }

    private Long queryArtistGenreRelation(SQLiteDatabase db, Long artistId, Long genreId) {
        Cursor c = db.query(
                ARTIST_GENRE, new String[] { Genre.ID },
                ArtistGenre.ARTIST_ID + "=?" + " AND " +
                        ArtistGenre.GENRE_ID + "=?",
                new String[] { String.valueOf(artistId), String.valueOf(genreId) },
                null, null, null);
        return DbUtils.getResultLongAndClose(c);
    }

    private Long createArtistGenreRelation(SQLiteDatabase db, Long artistId, Long genreId) {
        return db.insert(ARTIST_GENRE, null, Artists.artistGenreRelationToContentValues(artistId, genreId));
    }

    private Long createGenre(SQLiteDatabase db, String genre) {
        return db.insert(GENRE, null, Artists.genreToContentValues(genre));
    }

    private Long createArtist(SQLiteDatabase db, ru.yandex.yamblz.artistmodel.Artist artist) {
        return db.insert(ARTIST, null, Artists.artistToContentValues(artist));
    }

    private Long queryGenreByName(SQLiteDatabase db, String genre) {
        Cursor c = db.query(
                GENRE, new String[] { Genre.ID },
                Genre.GENRE_NAME + "=?", new String[] { genre },
                null, null, null);
        return DbUtils.getResultLongAndClose(c);
    }

    private Long queryArtistByName(SQLiteDatabase db, String name) {
        Cursor c = db.query(
                ARTIST, new String[] { Artist.ID },
                Artist.NAME + "=?", new String[] { name },
                null, null, null);
        return DbUtils.getResultLongAndClose(c);
    }

}
