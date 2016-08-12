package ru.yandex.yamblz;

import android.os.Handler;
import android.support.annotation.NonNull;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Component;
import ru.yandex.yamblz.developer_settings.DevMetricsProxy;
import ru.yandex.yamblz.developer_settings.DeveloperSettingsComponent;
import ru.yandex.yamblz.developer_settings.DeveloperSettingsModel;
import ru.yandex.yamblz.developer_settings.DeveloperSettingsModule;
import ru.yandex.yamblz.developer_settings.LeakCanaryProxy;
import ru.yandex.yamblz.di.component.ViewComponent;
import ru.yandex.yamblz.di.module.DataSourceModule;
import ru.yandex.yamblz.di.module.FragmentArgumentModule;
import ru.yandex.yamblz.di.module.MapperModule;
import ru.yandex.yamblz.di.module.PresenterModule;
import ru.yandex.yamblz.di.module.RepositoryModule;
import ru.yandex.yamblz.ui.activities.MainActivity;

@Singleton
@Component(modules = {
        ApplicationModule.class,
        DeveloperSettingsModule.class,
        DataSourceModule.class,
        RepositoryModule.class,
        PresenterModule.class,
        MapperModule.class
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

    EventBus eventBus();

    void inject(@NonNull MainActivity mainActivity);

    ViewComponent plus(FragmentArgumentModule arguments);
}
