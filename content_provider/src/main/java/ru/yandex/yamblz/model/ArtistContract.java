package ru.yandex.yamblz.model;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

public final class ArtistContract {
    public static final String CONTENT_AUTHORITY = "ru.yandex.yamblz";
    public static final String DB_NAME = "artists.sqlite";

    private static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_ARTIST = "artist";

    public static final class Artist implements BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_ARTIST).build();

        public static final String CONTENT_TYPE =
                "vnd.android.cursor.dir/" + CONTENT_URI + "/" + PATH_ARTIST;
        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/" + CONTENT_URI + "/" + PATH_ARTIST;

        public static final String TABLE_NAME = "artist";
        public static final String ID = "id_artist";
        public static final String NAME = "name";
        public static final String TRACKS = "count_of_tracks";
        public static final String ALBUMS = "count_of_albums";
        public static final String GENRES = "genres";
        public static final String BIG_COVER = "big_cover";
        public static final String SMALL_COVER = "small_cover";
        public static final String URL = "url";
        public static final String DESCRIPTION = "description";

        public static Uri buildArtistUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
}