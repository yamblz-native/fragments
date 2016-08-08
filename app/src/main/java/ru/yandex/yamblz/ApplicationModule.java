package ru.yandex.yamblz;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.yandex.yamblz.provider.CPDataProvider;
import ru.yandex.yamblz.provider.CPDataTransformer;
import ru.yandex.yamblz.provider.DataProvider;
import ru.yandex.yamblz.provider.DataTransformer;

@Module
public class ApplicationModule {

    public static final String MAIN_THREAD_HANDLER = "main_thread_handler";
    public static final String WORK_EXECUTOR = "worker";
    public static final String POST_EXECUTOR = "poster";

    @NonNull
    private final Application application;

    public ApplicationModule(@NonNull Application application) {
        this.application = application;
    }

    @Provides @NonNull @Singleton
    public Application provideYamblzApp() {
        return application;
    }

    @Provides @NonNull @Named(MAIN_THREAD_HANDLER) @Singleton
    public Handler provideMainThreadHandler() {
        return new Handler(Looper.getMainLooper());
    }

    @Provides
    @Singleton
    Context provideContext() {
        return application;
    }

    @Provides
    @Singleton
    @Named(WORK_EXECUTOR)
    Executor provideWorker() {
        return new ThreadPoolExecutor(4, 10, 120, TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>());
    }

    @Provides
    @Singleton
    @Named(POST_EXECUTOR)
    Executor providePoster(@Named(MAIN_THREAD_HANDLER) final Handler uiHandler) {
        return new Executor() {
            @Override
            public void execute(Runnable command) {
                uiHandler.post(command);
            }
        };
    }

    @Provides
    @Singleton
    DataTransformer provideDataTransformer() {
        return new CPDataTransformer();
    }

    @Provides
    @Singleton
    DataProvider provideDataProvider(Context context, DataTransformer dataTransformer,
                                     @Named(WORK_EXECUTOR) Executor worker,
                                     @Named(POST_EXECUTOR) Executor poster) {
        return new CPDataProvider(context, dataTransformer, worker, poster);
    }

}
