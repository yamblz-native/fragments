package ru.yandex.yamblz.data_base;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.VisibleForTesting;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import ru.yandex.yamblz.model.Artist;

public class DbProvider {

    private final DbBackend dbBackend;
    private final CustomExecutor executor;

    public DbProvider(Context context) {
        dbBackend = new DbBackend(context);
        executor = new CustomExecutor();
    }

    @VisibleForTesting
    DbProvider(DbBackend dbBackend,
               CustomExecutor executor) {
        this.dbBackend = dbBackend;
        this.executor = executor;
    }

    public Cursor getCursorArtistList() throws ExecutionException, InterruptedException {
        Future<Cursor> result = executor.submit(dbBackend::getArtistsList);
        return result.get();
    }

    public void addArtistsList(final List<Artist> artistList) {
        executor.execute(() -> {
            dbBackend.addArtistsList(artistList);
        });
    }

    // чет не получилось
//    public void clear() {
//        executor.execute(() -> {
//            dbBackend.clear();
//            dbNotificationManager.notifyListeners();
//        });
//    }

    private class CustomExecutor extends ThreadPoolExecutor {
        CustomExecutor() {
            super(Runtime.getRuntime().availableProcessors() * 2, Runtime.getRuntime().availableProcessors() * 2,
                    0L,
                    TimeUnit.MILLISECONDS,
                    new LinkedBlockingQueue<>());
        }
    }
}
