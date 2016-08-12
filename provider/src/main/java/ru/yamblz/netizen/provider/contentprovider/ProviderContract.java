package ru.yamblz.netizen.provider.contentprovider;

/**
 * Created by Александр on 12.08.2016.
 */

public interface ProviderContract {

    String AUTHORITY = "ru.yamblz.netizen.provider.contentprovider.BardContentProvider";

    String TABLE_BARD = "bard";
    String TABLE_GENRE = "genres";
    String TABLE_BARD_GANRE = "bard_genre";
    String TABLE_GENRE_LIST = "genre_list";
    String VIEW_BARD_WITH_GENRES = "view_with_genres";

    String BARD_URI = "content://" + AUTHORITY + "/" + TABLE_BARD;

    String CREATE_TABLE_BARD = "CREATE TABLE " + TABLE_BARD + "(" +
            Bard.FIELD_NAME + " TEXT UNIQUE NOT NULL, " +
            Bard.FIELD_TRACKS + " INTEGER NOT NULL, " +
            Bard.FIELD_ALBUMS + " INTEGER NOT NULL, " +
            Bard.FIELD_DESCRIPTION + " TEXT, " +
            Bard.FIELD_LINK + " TEXT, " +
            Bard.FIELD_SMALL_PHOTO + " TEXT, " +
            Bard.FIELD_BIG_PHOTO + " TEXT);";
    String CREATE_TABLE_GENRE = "CREATE TABLE " + TABLE_GENRE + "(" +
            Genres.FIELD_NAME + " TEXT NOT NULL UNIQUE);";
    String CREATE_TABLE_BARD_GENRE = "CREATE TABLE " + TABLE_BARD_GANRE + "(" +
            BardGanre.FIELD_BARD_ID + " INTEGER NOT NULL, " +
            BardGanre.FIELD_GENRE_ID + " INTEGER NOT NULL" +");";
    String CREATE_VIEW = String.format("CREATE VIEW %s AS  select %s, %s, group_concat(%s) as %s, %s, %s, %s, %s, %s, %s from %s left join %s on %s = %s left join %s on %s = %s group by %s",
            VIEW_BARD_WITH_GENRES,
            TABLE_BARD + "." + Bard.FIELD_ID,
            TABLE_BARD + "." + Bard.FIELD_NAME,
            TABLE_GENRE + "." + Genres.FIELD_NAME, TABLE_GENRE_LIST,
            Bard.FIELD_TRACKS,
            Bard.FIELD_ALBUMS,
            Bard.FIELD_LINK,
            Bard.FIELD_DESCRIPTION,
            Bard.FIELD_SMALL_PHOTO,
            Bard.FIELD_BIG_PHOTO,
            TABLE_BARD,
            TABLE_BARD_GANRE, TABLE_BARD + "." + Bard.FIELD_ID, BardGanre.FIELD_BARD_ID,
            TABLE_GENRE, TABLE_GENRE + "." + Genres.FIELD_ID, BardGanre.FIELD_GENRE_ID,
            TABLE_BARD + "." + Bard.FIELD_ID);

    String DROP_TABLE_BARD = "DROP TABLE " + TABLE_BARD;
    String DROP_TABLE_GENRE = "DROP TABLE " + TABLE_GENRE;
    String DROP_TABLE_BARD_GENRE = "DROP TABLE " + TABLE_BARD_GANRE;
    String DROP_VIEW_BARD_WITH_GENRE = "DROP TABLE " + VIEW_BARD_WITH_GENRES;
    String IS_EMPTY_BARD = "SELECT count(*) FROM " + TABLE_BARD;

    interface Bard {
        String FIELD_ID = "rowid";
        String FIELD_NAME = "name";
        String FIELD_TRACKS = "tracks";
        String FIELD_ALBUMS = "albums";
        String FIELD_LINK = "link";
        String FIELD_DESCRIPTION = "description";
        String FIELD_SMALL_PHOTO = "small";
        String FIELD_BIG_PHOTO = "big";
    }

    interface Genres {
        String FIELD_ID = "rowid";
        String FIELD_NAME = "name";
    }

    interface BardGanre {
        String FIELD_ID = "rowid";
        String FIELD_BARD_ID = "artistId";
        String FIELD_GENRE_ID = "genreId";
    }
}
