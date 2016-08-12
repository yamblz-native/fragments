package ru.yandex.provider;

import android.os.Handler;
import android.support.annotation.NonNull;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Component;
import ru.yandex.provider.developer_settings.DeveloperSettingsModule;
import ru.yandex.provider.ui.activities.MainActivity;
import ru.yandex.provider.developer_settings.DevMetricsProxy;
import ru.yandex.provider.developer_settings.DeveloperSettingsComponent;
import ru.yandex.provider.developer_settings.DeveloperSettingsModel;
import ru.yandex.provider.developer_settings.LeakCanaryProxy;

@Singleton
@Component(modules = {
        ApplicationModule.class,
        DeveloperSettingsModule.class,
})
public interface ApplicationComponent {

    // Provide LeakCanary without injection to leave.
    @NonNull
    LeakCanaryProxy leakCanaryProxy();

    @NonNull
    DeveloperSettingsComponent plusDeveloperSettingsComponent();

    DeveloperSettingsModel developerSettingModel();

    DevMetricsProxy devMetricsProxy();

    @NonNull @Named(ApplicationModule.MAIN_THREAD_HANDLER)
    Handler mainThreadHandler();

    void inject(@NonNull MainActivity mainActivity);
}
