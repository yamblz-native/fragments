package com.example.vorona.server.db;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.VisibleForTesting;

import com.example.vorona.server.model.Singer;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by user on 31/07/2016.
 */
public class DbProvider {

    private final DbBackend mDbBackend;
    private final DbNotificationManager mDbNotificationManager;
    private final CustomExecutor mExecutor;
    private final Handler mHandler = new Handler(Looper.getMainLooper());

    public interface ResultCallback<T> {
        void onFinished(T result);
    }

    public DbProvider(Context context) {
        mDbBackend = new DbBackend(context);
        mDbNotificationManager = FakeContainer.getNotificationInstance(context);
        mExecutor = new CustomExecutor();
    }

    @VisibleForTesting
    DbProvider(DbBackend dbBackend,
               DbNotificationManager dbNotificationManager,
               CustomExecutor executor) {
        mDbBackend = dbBackend;
        mDbNotificationManager = dbNotificationManager;
        mExecutor = executor;
    }

    public void getSingers(final ResultCallback<List<Singer>> callback) {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                final List<Singer> c =  mDbBackend.getSingers();
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onFinished(c);
                    }
                });
            }
        });
    }

    public void getSingers(final int id, final ResultCallback<Singer> callback) {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                final Singer c =  mDbBackend.getSinger(id);
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onFinished(c);
                    }
                });
            }
        });
    }

    public void insertSinger(final Singer singer) {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mDbBackend.insertSinger(singer);
                mDbNotificationManager.notifyListeners();
            }
        });
    }

    public void insertSingerUnique(final Singer singer) {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mDbBackend.insertSingerUnique(singer);
                mDbNotificationManager.notifyListeners();
            }
        });
    }

    public void insertListUnique(final List<Singer> singer) {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mDbBackend.insertListUnique(singer);
                mDbNotificationManager.notifyListeners();
            }
        });
    }

    public void insertList(final List<Singer> singer) {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mDbBackend.insertList(singer);
                mDbNotificationManager.notifyListeners();
            }
        });
    }

    class CustomExecutor extends ThreadPoolExecutor {
        CustomExecutor() {
            super(2, 4, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
        }
    }
}
