package ru.yandex.yamblz.ui.background;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ru.yandex.yamblz.Contract;
import ru.yandex.yamblz.model.Artist;
import ru.yandex.yamblz.model.Cover;

/**
 * Created by Aleksandra on 07/08/16.
 */
public class MyLoader extends AsyncTaskLoader<List<Artist>> {
    private static final String BASE_URI = "content://com.bobrusha.android.yandex.content_provider_server.content_provider.MyProvider/artist";
    public static final String DEBUG_TAG = MyLoader.class.getName();

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    public MyLoader(Context context) {
        super(context);
    }

    @Override
    public List<Artist> loadInBackground() {
        Log.d(DEBUG_TAG, "in load background;");
        Uri uri = Uri.parse(BASE_URI);
        Cursor cursor = getContext().getContentResolver().query(uri, null, null, null, null);

        if (cursor != null) {
            Log.d(DEBUG_TAG, " Loaded " + cursor.getCount());
            cursor.moveToFirst();
            List<Artist> result = new ArrayList<>();
            while(cursor.moveToNext()) {
                result.add(getArtistFromCursor(cursor));
            }
            cursor.close();
            return result;
        }
        return null;
    }


    public static Artist getArtistFromCursor(Cursor c) {
        String[] genres = c.getString(c.getColumnIndex(Contract.ArtistEntry.COLUMN_NAME_GENRES)).split(", ", -1);

        Cover cover = Cover.builder()
                .smallCover(c.getString(c.getColumnIndex(Contract.ArtistEntry.COLUMN_NAME_COVER_SMALL)))
                .bigCover(c.getString(c.getColumnIndex(Contract.ArtistEntry.COLUMN_NAME_COVER_BIG)))
                .build();

        return Artist.builder()
                .id(c.getLong(c.getColumnIndex(Contract.ArtistEntry._ID)))
                .name(c.getString(c.getColumnIndex(Contract.ArtistEntry.COLUMN_NAME_ARTIST_NAME)))
                .genres(Arrays.asList(genres))
                .tracks(c.getInt(c.getColumnIndex(Contract.ArtistEntry.COLUMN_NAME_TRACKS)))
                .albums(c.getInt(c.getColumnIndex(Contract.ArtistEntry.COLUMN_NAME_ALBUMS)))
                .link(c.getString(c.getColumnIndex(Contract.ArtistEntry.COLUMN_NAME_LINK)))
                .description(c.getString(c.getColumnIndex(Contract.ArtistEntry.COLUMN_NAME_DESCRIPTION)))
                .cover(cover)
                .build();
    }
}
