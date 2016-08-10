package ru.yandex.yamblz;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.yandex.yamblz.domain.datasource.BardService;
import ru.yandex.yamblz.domain.model.api.ApiConst;
import ru.yandex.yamblz.domain.model.api.NetBard;

@Module
public class ApplicationModule {

    public static final String MAIN_THREAD_HANDLER = "main_thread_handler";

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

    @Provides @NonNull
    public EventBus provideEventBus(){
        return EventBus.getDefault();
    }

    @Provides
    public Gson provideJson(){
        return new Gson();
    }

    @Provides
    public BardService provideBardService(){
        return new Retrofit.Builder().addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(ApiConst.ENDPOINT)
                .build().create(BardService.class);
    }

}
