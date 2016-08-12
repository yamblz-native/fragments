package ru.yandex.provider;

import android.app.Application;
import android.support.annotation.NonNull;

import ru.yandex.provider.ProviderApp;
import ru.yandex.provider.developer_settings.DevMetricsProxy;
import ru.yandex.provider.developer_settings.DeveloperSettingsModule;
import ru.yandex.yamblz.DaggerApplicationComponent;

public class UnitTestProviderApp extends ProviderApp {

    @NonNull
    @Override
    protected DaggerApplicationComponent.Builder prepareApplicationComponent() {
        return super.prepareApplicationComponent()
                .developerSettingsModule(new DeveloperSettingsModule() {
                    @NonNull
                    public DevMetricsProxy provideDevMetricsProxy(@NonNull Application application) {
                        return () -> {
                            //No Op
                        };
                    }
                });
    }
}
