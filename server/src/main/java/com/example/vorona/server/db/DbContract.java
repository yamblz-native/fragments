package com.example.vorona.server.db;

/**
 * Created by user on 31/07/2016.
 */
public interface DbContract {
    String DB_NAME = "main.sqlite";

    String ARTISTS = "atrists";
    interface Artist {
        String LOCAL_ID = "local_id";
        String ID = "id";
        String BIO = "bio";
        String ALBUM = "album";
        String TRACKS = "tracks";
        String NAME = "name";
        String COVER = "cover";
        String GENRES = "genres";
        String COVER_SMALL = "cover_small";
    }
}
