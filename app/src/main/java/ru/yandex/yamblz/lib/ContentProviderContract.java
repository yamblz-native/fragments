package ru.yandex.yamblz.lib;


public interface ContentProviderContract {
    String AUTHORITY = "ru.yandex.yamblz.db.MyContentProvider";
    String URL = "content://" + AUTHORITY + "/artists";
    String DB_NAME = "artists.sqlite";

    String ARTISTS = "artists";
    interface Artists {
        String ID = "id";
        String NAME = "name";
        String TRACKS = "tracks";
        String ALBUMS = "albums";
        String LINK = "link";
        String DESCRIPTION = "description";
        String IMAGE_BIG = "image_big";
        String IMAGE_SMALL = "image_small";
        String GENRES ="genres";
    }

    String GENRES = "genres";
    interface Genres {
        String ID = "rowid";
        String NAME = "name";
    }

    String ARTIST_GENRES ="artist_genres";
    interface ArtistGenres{
        String ID = "rowid";
        String ARTIST_ID = "artist_id";
        String GENRE_ID = "genre_id";
    }
}
