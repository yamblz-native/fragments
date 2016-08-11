package ru.yandex.yamblz.database;


public interface DBContract {
    interface Artists {
        String TABLE = "artists";
        String ID = "id";
        String NAME = "name";
        String TRACKS = "tracks";
        String ALBUMS = "albums";
        String LINK = "link";
        String DESCRIPTION = "description";
        String SMALL_COVER = "small_cover";
        String BIG_COVER = "big_cover";
    }

    interface Genres {
        String TABLE = "genres";
        String NAME = "genre";
    }

    interface GenreToArtist {
        String TABLE = "genre_to_artist";
        String ARTIST_ID = "artist_id";
        String GENRE_ID = "genre_id";
    }

    String[] allColumns = new String[]{
            Artists.ID,
            Artists.NAME,
            Artists.TRACKS,
            Artists.ALBUMS,
            Artists.LINK,
            Artists.DESCRIPTION,
            Artists.SMALL_COVER,
            Artists.BIG_COVER
    };
}
