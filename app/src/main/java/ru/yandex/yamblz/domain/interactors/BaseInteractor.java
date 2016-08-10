package ru.yandex.yamblz.domain.interactors;

import android.os.AsyncTask;

public abstract class BaseInteractor<Listener, Task extends AsyncTask> {

    protected Listener listener;
    protected Task task;

    abstract public void execute();

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public void removeListener() {
        listener = null;
    }

    public boolean isRunning() {
        return task.getStatus() == AsyncTask.Status.RUNNING;
    }

}
