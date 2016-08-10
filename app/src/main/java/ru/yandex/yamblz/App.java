package ru.yandex.yamblz;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import ru.yandex.yamblz.developer_settings.DevMetricsProxy;
import ru.yandex.yamblz.developer_settings.DeveloperSettingsModel;
import ru.yandex.yamblz.net.VolleyInstance;
import timber.log.Timber;

public class App extends Application {
    private ApplicationComponent applicationComponent;

    private VolleyInstance volleyInstance;
    // Prevent need in a singleton (global) reference to the application object.
    @NonNull
    public static App get(@NonNull Context context) {
        return (App) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        applicationComponent = prepareApplicationComponent().build();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());

            DeveloperSettingsModel developerSettingModel = applicationComponent.developerSettingModel();
            developerSettingModel.apply();

            DevMetricsProxy devMetricsProxy = applicationComponent.devMetricsProxy();
            devMetricsProxy.apply();
        }
    }

    @NonNull
    protected DaggerApplicationComponent.Builder prepareApplicationComponent() {
        return DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this));
    }

    @NonNull
    public ApplicationComponent applicationComponent() {
        return applicationComponent;
    }

    public VolleyInstance getVolley(){
        if(volleyInstance == null){
            volleyInstance = new VolleyInstance(this);
        }
        return volleyInstance;
    }

    public static App from(Context context){
        return (App) context.getApplicationContext();
    }

}
