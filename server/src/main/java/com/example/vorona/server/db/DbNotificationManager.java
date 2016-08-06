package com.example.vorona.server.db;

import android.os.Handler;
import android.os.Looper;

import java.util.HashSet;

/**
 * Created by user on 31/07/2016.
 */
public class DbNotificationManager {

    private HashSet<Listener> mListeners = new HashSet<>();
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private Runnable mNotifyRunnable = new Runnable() {
        @Override
        public void run() {
            notifyOnUiThread();
        }
    };

    public interface Listener {
        void onDataUpdated();
    }

    public void addListener(Listener listener) {
        mListeners.add(listener);
    }

    public void removeListener(Listener listener) {
        mListeners.remove(listener);
    }

    void notifyListeners() {
        mHandler.removeCallbacks(mNotifyRunnable);
        mHandler.post(mNotifyRunnable);
    }

    private void notifyOnUiThread() {
        for (Listener l : mListeners) l.onDataUpdated();
    }
}
