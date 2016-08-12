package ru.yandex.yamblz.data_base;

/**
 * Created by SerG3z on 31/07/2016.
 */
interface DbContract {

    String DB_NAME = "artist.db";

    String ARTISTS = "artists";

    interface Artists {
        String ID = "rowid";
        String NAME = "name";
        String LINK = "link";
        String TRACKS = "tracks";
        String ALBUMS = "albums";
        String COVER_BIG = "cover_big";
        String COVER_SMALL = "cover_small";
        String DESCRIPTION = "description";
        String GENRES_LIST = "genres_list";
    }

    String ARTISTS_GENRES = "artists_genres";

    interface ArtistsGenres {
        String ID = "rowid";
        String GENRE_ID = "genre_id";
        String ARTIST_ID = "artist_id";
    }

    String GENRES = "genres";

    interface Genres {
        String ID = "rowid";
        String NAME = "name";
    }
}
