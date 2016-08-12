package ru.yandex.yamblz.domain.datasource;

/**
 * Created by Александр on 12.08.2016.
 */

public interface ProviderContract {

    String AUTHORITY = "ru.yamblz.netizen.provider.contentprovider.BardContentProvider";
    String TABLE_BARD = "bard";
    String TABLE_GENRE_LIST = "genre_list";
    String BARD_URI = "content://" + AUTHORITY + "/" + TABLE_BARD;



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
}
