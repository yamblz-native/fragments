package ru.yandex.yamblz.managers;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;


import ru.yandex.yamblz.data.IterableCursor;
import ru.yandex.yamblz.utils.AppConfig;


/**
 * Created by shmakova on 16.07.16.
 */

public class DataManager {
    private static final String ARTISTS_URI = "content://" + AppConfig.AUTHORITY + "/artists";
    private ContentResolver resolver;
    private static DataManager INSTANCE = null;

    private DataManager(Context context) {
        resolver = context.getContentResolver();
    }

    public static DataManager getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new DataManager(context.getApplicationContext());
        }

        return INSTANCE;
    }

    public IterableCursor getArtistsListCursor() {
        Cursor cursor = resolver.query(
                Uri.parse(ARTISTS_URI),
                null,
                null,
                null,
                null);
        return new IterableCursor(cursor);
    }
}
