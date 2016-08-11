package ru.yandex.provider.database;

import android.content.Context;
import android.database.Cursor;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.VisibleForTesting;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import ru.yandex.yamblz.artistmodel.Artist;

/**
 * Created by root on 8/9/16.
 */
public class ArtistDbProvider {

    private final ArtistDbBackend dbBackend;
    private final CustomExecutor executor;
    private final Handler handler = new Handler(Looper.getMainLooper());

    public interface ResultCallback<T> {
        void onFinished(T result);
    }

    public ArtistDbProvider(Context context) {
        dbBackend = new ArtistDbBackend(context);
        executor = new CustomExecutor();
    }

    @VisibleForTesting
    public ArtistDbProvider(ArtistDbBackend dbBackend,
               CustomExecutor executor) {
        this.dbBackend = dbBackend;
        this.executor = executor;
    }

    public void getArtists(ResultCallback<Cursor> callback) {
        executor.execute(() -> {
            final Cursor c =  dbBackend.getArtists();
            handler.post(() -> callback.onFinished(c));
        });
    }

    public void insertArtist(final Artist artist) {
        executor.execute(() -> {
                dbBackend.insertArtist(artist);
                // notify listeners
            });
    }

    class CustomExecutor extends ThreadPoolExecutor {
        CustomExecutor() {
            super(2, 2, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());
        }
    }

}
