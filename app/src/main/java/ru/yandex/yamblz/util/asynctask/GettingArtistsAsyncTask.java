package ru.yandex.yamblz.util.asynctask;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ru.yandex.yamblz.artistmodel.Artist;
import ru.yandex.yamblz.util.db.Artists;
import ru.yandex.yamblz.util.function.Consumer;

/**
 * Created by root on 8/9/16.
 */
public class GettingArtistsAsyncTask extends AsyncTask<Void, Void, Artist[]> {

    private static final String AUTHORITY = "ru.yandex.yamblz.artistprovider";
    private static final String ARTIST_PATH = "artist";
    private static final String SCHEME = "content";

    private final Runnable before;
    private final Consumer<Artist[]> after;
    private Context context;

    public GettingArtistsAsyncTask(Runnable before, Consumer<Artist[]> after, Context context) {

        this.before = before;
        this.after = after;
        this.context = context.getApplicationContext();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if(before != null) {
            before.run();
        }
    }

    @Override
    protected Artist[] doInBackground(Void... params) {
        Uri uri = new Uri.Builder().scheme(SCHEME).authority(AUTHORITY).appendPath(ARTIST_PATH).build();
        List<Artist> artists = new ArrayList<>();

        Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                artists.add(Artists.cursorToArtist(cursor));
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        return artists.toArray(new Artist[artists.size()]);
    }

    @Override
    protected void onPostExecute(Artist[] artists) {
        super.onPostExecute(artists);

        if(after != null) {
            after.apply(artists);
        }
    }
}
