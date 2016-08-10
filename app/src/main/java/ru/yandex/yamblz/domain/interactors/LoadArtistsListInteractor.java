package ru.yandex.yamblz.domain.interactors;

import android.os.AsyncTask;

import java.io.IOException;

import ru.yandex.yamblz.domain.DataManager;
import ru.yandex.yamblz.domain.interactors.listeners.OnArtistsUpdateListener;

public class LoadArtistsListInteractor
        extends BaseInteractor<OnArtistsUpdateListener, LoadArtistsListInteractor.LoadTask> {

    private DataManager dataManager;

    public LoadArtistsListInteractor(DataManager dataManager) {
        this.dataManager = dataManager;
        task = new LoadTask();
    }

    @Override
    public void execute() {
        if (task.getStatus() != AsyncTask.Status.RUNNING) {
            task = new LoadTask();
            task.execute();
        }
    }

    class LoadTask extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                return dataManager.loadArtists();
            } catch (IOException e) {
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean success) {
            if (success) {
                if (listener != null) listener.onArtistsLoadingSuccess();
            } else {
                if (listener != null) listener.onArtistsLoadingFailure();
            }
        }
    }

}
