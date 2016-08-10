package ru.yandex.yamblz.euv.shared.contract;

public abstract class DbContract {
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS ";
    private static final String CREATE_TABLE = "CREATE TABLE ";


    public static abstract class ArtistTable {
        public static final String TABLE_NAME = "artist";

        public static final String _ID = "_id";
        public static final String NAME = "name";
        public static final String GENRES = "genres";
        public static final String TRACKS = "tracks";
        public static final String ALBUMS = "albums";
        public static final String LINK = "link";
        public static final String DESCRIPTION = "description";
        public static final String COVER_SMALL = "cover_small";
        public static final String COVER_BIG = "cover_big";

        public static final String[] PROJECTION = {_ID, NAME, GENRES, TRACKS, ALBUMS, LINK, DESCRIPTION, COVER_SMALL, COVER_BIG};

        public static final String SQL_DROP_TABLE = DROP_TABLE + TABLE_NAME;
        public static final String SQL_CREATE_TABLE = CREATE_TABLE + TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY," +
                NAME + " TEXT," +
                GENRES + " TEXT," +
                TRACKS + " INTEGER NOT NULL," +
                ALBUMS + " INTEGER NOT NULL," +
                LINK + " TEXT," +
                DESCRIPTION + " TEXT," +
                COVER_SMALL + " TEXT," +
                COVER_BIG + " TEXT)";
    }


    /**
     * TODO: Use separate tables for genres and covers (for now the following tables are unused)
     */
    public static abstract class GenreTable {
        public static final String TABLE_NAME = "genre";

        public static final String _ID = "_id";
        public static final String NAME = "name";

        public static final String[] PROJECTION = {_ID, NAME};

        public static final String SQL_DROP_TABLE = DROP_TABLE + TABLE_NAME;
        public static final String SQL_CREATE_TABLE = CREATE_TABLE + TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY," +
                NAME + " TEXT UNIQUE)";
    }


    public static abstract class ArtistsGenresTable {
        public static final String TABLE_NAME = "artists_genres";

        public static final String _ID = "_id";
        public static final String ARTIST_ID = "artist_id";
        public static final String GENRE_ID = "genre_id";

        public static final String[] PROJECTION = {ARTIST_ID, GENRE_ID};

        public static final String SQL_DROP_TABLE = DROP_TABLE + TABLE_NAME;
        public static final String SQL_CREATE_TABLE = CREATE_TABLE + TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY," +
                ARTIST_ID + " INTEGER NOT NULL," +
                GENRE_ID + " INTEGER NOT NULL)";
    }
}
