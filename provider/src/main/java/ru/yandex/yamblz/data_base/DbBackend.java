package ru.yandex.yamblz.data_base;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.VisibleForTesting;

import java.util.List;

import ru.yandex.yamblz.model.Artist;

class DbBackend implements DbContract {

    private final DbOpenHelper mDbOpenHelper;

    DbBackend(Context context) {
        mDbOpenHelper = new DbOpenHelper(context);
    }

    @VisibleForTesting
    DbBackend(DbOpenHelper dbOpenHelper) {
        mDbOpenHelper = dbOpenHelper;
    }

    public Cursor getArtistsList() {
        SQLiteDatabase db = mDbOpenHelper.getWritableDatabase();
        String tables = ARTISTS + " LEFT JOIN " + ARTISTS_GENRES + " ON " +
                ARTISTS + "." + Artists.ID + "=" + ARTISTS_GENRES + "." + ArtistsGenres.ARTIST_ID +
                " LEFT JOIN " + GENRES + " ON " +
                ARTISTS_GENRES + "." + ArtistsGenres.GENRE_ID + "=" + GENRES + "." + Genres.ID;

        String[] columns = new String[]{
                ARTISTS + "." + Artists.ID,
                ARTISTS + "." + Artists.NAME,
                ARTISTS + "." + Artists.LINK,
                ARTISTS + "." + Artists.TRACKS,
                ARTISTS + "." + Artists.ALBUMS,
                ARTISTS + "." + Artists.COVER_BIG,
                ARTISTS + "." + Artists.COVER_SMALL,
                ARTISTS + "." + Artists.DESCRIPTION,
                "GROUP_CONCAT(" + GENRES + "." + Genres.NAME + ") AS " + Artists.GENRES_LIST
        };

        String groupBy = ARTISTS + "." + Artists.NAME;
        Cursor c = db.query(tables, columns, null, null, groupBy, null, null);

        if (c != null) {
            c.moveToFirst();
        }

        return c;
    }

    public void addArtistsList(List<Artist> artistList) {
        SQLiteDatabase db = mDbOpenHelper.getWritableDatabase();
        db.beginTransaction();
        try {
            Long artistId;
            for (Artist artist : artistList) {
                artistId = queryArtistByName(db, artist.getName());

                if (artistId == null) {
                    artistId = addArtist(db, artist);
                    addGenres(db, artistId, artist.getGenres());
                }
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    private Long queryArtistByName(SQLiteDatabase db, String artistName) {
        Cursor c = db.query(ARTISTS, new String[]{Artists.ID}, Artists.NAME + "=?",
                new String[]{artistName}, null, null, null);
        return DbUtils.getResultLongAndClose(c);
    }

    private long addArtist(SQLiteDatabase db, Artist artist) {
        ContentValues values = new ContentValues();
        values.put(Artists.NAME, artist.getName());
        values.put(Artists.LINK, artist.getLink());
        values.put(Artists.ALBUMS, artist.getAlbums());
        values.put(Artists.TRACKS, artist.getTracks());
        values.put(Artists.COVER_BIG, artist.getCover().getBig());
        values.put(Artists.COVER_SMALL, artist.getCover().getSmall());
        values.put(Artists.DESCRIPTION, artist.getDescription());
        return db.insert(ARTISTS, null, values);
    }

    private void addGenres(SQLiteDatabase db, Long artistId, List<String> genres) {
        Long genreId;
        Long artistGenreId;
        for (String genre : genres) {
            genreId = queryGenreByName(db, genre);
            if (genreId == null) {
                genreId = addGenre(db, genre);
            }
            artistGenreId = queryArtistGenreByIds(db, artistId, genreId);
            if (artistGenreId == null) {
                addArtistGenre(db, artistId, genreId);
            }
        }
    }

    private long addGenre(SQLiteDatabase db, String genre) {
        ContentValues values = new ContentValues();
        values.put(Genres.NAME, genre);
        return db.insert(GENRES, null, values);
    }

    private long addArtistGenre(SQLiteDatabase db, Long artistId, Long genreId) {
        ContentValues values = new ContentValues();
        values.put(ArtistsGenres.ARTIST_ID, artistId);
        values.put(ArtistsGenres.GENRE_ID, genreId);
        return db.insert(ARTISTS_GENRES, null, values);
    }

    private Long queryArtistGenreByIds(SQLiteDatabase db, Long artistId, Long genreId) {
        Cursor c = db.query(ARTISTS_GENRES, new String[]{ArtistsGenres.ID},
                ArtistsGenres.ARTIST_ID + "=? and " + ArtistsGenres.GENRE_ID + "=?",
                new String[]{String.valueOf(artistId), String.valueOf(genreId)}, null, null, null);
        return DbUtils.getResultLongAndClose(c);
    }

    private Long queryGenreByName(SQLiteDatabase db, String genre) {
        Cursor c = db.query(GENRES, new String[]{Genres.ID},
                Genres.NAME + "=?", new String[]{genre}, null, null, null);
        return DbUtils.getResultLongAndClose(c);
    }
}
