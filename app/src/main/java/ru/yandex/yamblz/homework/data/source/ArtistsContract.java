package ru.yandex.yamblz.homework.data.source;

import android.content.ContentResolver;
import android.net.Uri;

/**
 * Created by platon on 06.08.2016.
 */
public interface ArtistsContract
{
    String CONTENT_AUTHORITY = "ru.yandex.yamblz.artists_provider";
    String PATH_ARTISTS = "artists";

    Uri ARTISTS_URI = Uri.parse("content://ru.yandex.yamblz.artists_provider/artists");

    String TYPE_ARTISTS_DIR = ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd." + CONTENT_AUTHORITY + "." + PATH_ARTISTS;
    String TYPE_ARTISTS_ITEM = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd." + CONTENT_AUTHORITY + "." + PATH_ARTISTS;

    String COLUMN_ARTIST_ID = "artist_id";
    String COLUMN_ARTIST_NAME = "name";
    String COLUMN_GENRES = "genres";
    String COLUMN_TRACKS = "tracks";
    String COLUMN_ALBUMS = "albums";
    String COLUMN_DESC = "description";
    String COLUMN_COVER_SMALL = "cover_small";
    String COLUMN_COVER_BIG = "cover_big";
}
