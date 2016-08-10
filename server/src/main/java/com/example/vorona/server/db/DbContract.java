package com.example.vorona.server.db;

/**
 * Created by user on 31/07/2016.
 */
public interface DbContract {
    String DB_NAME = "main.sqlite";

    String ARTISTS = "artists";
    String GENRES = "genres";
    String ARTIST_GENRES = "artists_genres";
    interface Artist {
        String LOCAL_ID = "local_id";
        String ID = "id";
        String BIO = "bio";
        String ALBUM = "albums";
        String TRACKS = "tracks";
        String NAME = "name";
        String COVER = "cover";
        String COVER_SMALL = "cover_small";
    }

    interface Genre{
        String ID = "id";
        String GENRE = "genre";
    }

    interface ArtistGenre{
        String ARTIST_ID = "artist_id";
        String GENRE_ID = "genre_id";
    }
}
