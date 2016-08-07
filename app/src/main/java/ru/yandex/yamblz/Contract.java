package ru.yandex.yamblz;

import android.provider.BaseColumns;

/**
 * Created by Aleksandra on 07/08/16.
 */
public interface Contract {

    interface Service {
        String ACTION = "com.bobrusha.android.yandex.content_provider_server.content_provider.StartService";
        String SERVICE_PACKAGE_NAME = "com.bobrusha.android.yandex.content_provider_server";
        String SERVICE_CLASS_NAME = SERVICE_PACKAGE_NAME + ".network.MyService";
    }

    interface ArtistEntry extends BaseColumns {
        String TABLE_NAME = "artist";
        String COLUMN_NAME_ARTIST_NAME = "artist_name";
        String COLUMN_NAME_GENRES = "genres";
        String COLUMN_NAME_TRACKS = "amount_of_tracks";
        String COLUMN_NAME_ALBUMS = "amount_of_albums";
        String COLUMN_NAME_LINK = "link";
        String COLUMN_NAME_DESCRIPTION = "description";
        String COLUMN_NAME_COVER_SMALL = "cover_small";
        String COLUMN_NAME_COVER_BIG = "cover_big";
    }
}
