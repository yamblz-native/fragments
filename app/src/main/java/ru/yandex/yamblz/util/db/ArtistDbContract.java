package ru.yandex.yamblz.util.db;

/**
 * Created by root on 8/9/16.
 */
public interface ArtistDbContract {
    String DB_NAME = "artists";

    String ARTIST = "artist";
    interface Artist {
        String ID = "rowid";
        String NAME = "name";
        String TRACKS = "tracks";
        String ALBUMS = "albums";
        String LINK = "link";
        String DESCRIPTION = "description";
        String BIG_COVER_LINK = "big_cover_link";
        String SMALL_COVER_LINK = "small_cover_link";
    }

    String GENRE = "genre";
    interface Genre {
        String ID = "rowid";
        String GENRE_NAME = "genre_name";
    }

    String ARTIST_GENRE = "artist_genre";
    interface ArtistGenre {
        String ARTIST_ID = "artist_id";
        String GENRE_ID = "genre_id";
    }

}
