package ru.yandex.provider;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import ru.yandex.provider.developer_settings.DevMetricsProxy;
import ru.yandex.provider.developer_settings.DeveloperSettingsModel;
import ru.yandex.provider.BuildConfig;
import ru.yandex.provider.DaggerApplicationComponent;
import timber.log.Timber;

public class ProviderApp extends Application {
    private ApplicationComponent applicationComponent;

    // Prevent need in a singleton (global) reference to the application object.
    @NonNull
    public static ProviderApp get(@NonNull Context context) {
        return (ProviderApp) context.getApplicationContext();
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
}
