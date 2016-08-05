package ru.yandex.yamblz.loaders;

import android.content.AsyncTaskLoader;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.JsonReader;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import ru.yandex.yamblz.model.Singer;

/**
 * Created by vorona on 19.07.16.
 */

public class ArtistLoader extends AsyncTaskLoader<List<Singer>> {

    static final String PROVIDER_NAME = "ru.yandex.yamblz.database";
    static final String URL = "content://" + PROVIDER_NAME + "/artists";
    static final Uri CONTENT_URI = Uri.parse(URL);
    private List<Singer> singers;

    public ArtistLoader(Context context) {
        super(context);

    }

    @Override
    public List<Singer> loadInBackground() {
        try {
            Log.w("Loader", "Loading from the content provider");
            singers = new ArrayList<>();
            Cursor c = getContext().getContentResolver().query(CONTENT_URI, null, null, null, null);
            if (c != null && c.moveToFirst()) {
                do {
                    Singer singer = new Singer();
                    singer.setId(c.getInt(c.getColumnIndex("id")));
                    singer.setName(c.getString(c.getColumnIndex("name")));
                    singer.setBio(c.getString(c.getColumnIndex("bio")));
                    singer.setAlbums(c.getInt(c.getColumnIndex("albums")));
                    singer.setTracks(c.getInt(c.getColumnIndex("tracks")));
                    singer.setCover_big(c.getString(c.getColumnIndex("cover")));
                    singer.setGenres(c.getString(c.getColumnIndex("genres")));
                    singer.setCover_small(c.getString(c.getColumnIndex("cover_small")));
                    singers.add(singer);
                } while (c.moveToNext());
                c.close();
            } else {
                Log.w("Loader", "got null cursor");
            }

        } catch (Exception e) {
            e.printStackTrace();
            Log.w("Loader", "We got exception during download");
            return null;
        }
        return singers;
    }


    /**
     * Called when there is new data to deliver to the client.  The
     * super class will take care of delivering it; the implementation
     * here just adds a little more logic.
     */
    @Override
    public void deliverResult(List<Singer> sin) {
        if (isReset()) {
            // An async query came in while the loader is stopped.  We
            // don't need the result.
            if (sin != null) {
                onReleaseResources(sin);
            }
        }
        singers = sin;

        if (isStarted()) {
            // If the Loader is currently started, we can immediately
            // deliver its results.
            super.deliverResult(sin);
        }
    }

    /**
     * Handles a request to start the Loader.
     */
    @Override
    protected void onStartLoading() {
        if (singers != null) {
            // If we currently have a result available, deliver it
            // immediately.
            deliverResult(singers);
        }

        if (takeContentChanged() || singers == null) {
            // If the data has changed since the last time it was loaded
            // or is not currently available, start a load.
            forceLoad();
        }
    }

    /**
     * Handles a request to stop the Loader.
     */
    @Override
    protected void onStopLoading() {
        // Attempt to cancel the current load task if possible.
        cancelLoad();
    }

    /**
     * Handles a request to cancel a load.
     */
    @Override
    public void onCanceled(List<Singer> singers) {
        super.onCanceled(singers);

        // At this point we can release the resources associated with 'apps'
        // if needed.
        onReleaseResources(singers);
    }

    /**
     * Handles a request to completely reset the Loader.
     */
    @Override
    protected void onReset() {
        super.onReset();

        // Ensure the loader is stopped
        onStopLoading();

        // At this point we can release the resources associated with 'apps'
        // if needed.
        if (singers != null) {
            onReleaseResources(singers);
            singers = null;
        }
    }

    /**
     * Helper function to take care of releasing resources associated
     * with an actively loaded data set.
     *
     * @param singers
     */
    protected void onReleaseResources(List<Singer> singers) {
        // For a simple List<> there is nothing to do.  For something
        // like a Cursor, we would close it here.
    }
}
