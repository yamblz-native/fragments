package ru.yandex.provider.database;

import android.content.ContentValues;
import android.database.Cursor;

import ru.yandex.yamblz.artistmodel.Cover;

/**
 * Created by root on 8/9/16.
 *
 * It's util class that contains helpful methods to work with Artist model and database together
 *
 */
public class Artists implements ArtistDbContract {

    public static ContentValues artistToContentValues(ru.yandex.yamblz.artistmodel.Artist artist) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(Artist.NAME, artist.getName());
        contentValues.put(Artist.ALBUMS, artist.getAlbums());
        contentValues.put(Artist.TRACKS, artist.getTracks());
        contentValues.put(Artist.LINK, artist.getLink());
        contentValues.put(Artist.DESCRIPTION, artist.getDescription());
        contentValues.put(Artist.BIG_COVER_LINK, artist.getCover().getBig());
        contentValues.put(Artist.SMALL_COVER_LINK, artist.getCover().getSmall());

        return contentValues;
    }

    public static ContentValues genreToContentValues(String genre) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(Genre.GENRE_NAME, genre);

        return contentValues;
    }

    public static ContentValues artistGenreRelationToContentValues(long artistId, long genreId) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(ArtistGenre.ARTIST_ID, artistId);
        contentValues.put(ArtistGenre.GENRE_ID, genreId);

        return contentValues;
    }

    public static ru.yandex.yamblz.artistmodel.Artist cursorToArtist(Cursor cursor) {
        ru.yandex.yamblz.artistmodel.Artist artist = new ru.yandex.yamblz.artistmodel.Artist();

        artist.setName(cursor.getString(cursor.getColumnIndex(Artist.NAME)));
        artist.setTracks(cursor.getInt(cursor.getColumnIndex(Artist.TRACKS)));
        artist.setAlbums(cursor.getInt(cursor.getColumnIndex(Artist.ALBUMS)));
        artist.setLink(cursor.getString(cursor.getColumnIndex(Artist.LINK)));
        artist.setDescription(cursor.getString(cursor.getColumnIndex(Artist.DESCRIPTION)));
        artist.setGenres(cursor.getString(cursor.getColumnIndex(Genre.GENRE_NAME)).split(", "));

        Cover cover = new Cover();

        cover.setBig(cursor.getString(cursor.getColumnIndex(Artist.BIG_COVER_LINK)));
        cover.setSmall(cursor.getString(cursor.getColumnIndex(Artist.SMALL_COVER_LINK)));

        artist.setCover(cover);

        return artist;
    }

}
