package ru.yandex.yamblz.ui.views;

import android.support.annotation.NonNull;

import ru.yandex.yamblz.performance.AnyThread;
import ru.yandex.yamblz.ui.contcract.BaseContract;

public interface DeveloperSettingsView extends BaseContract.MvpView {

    @AnyThread
    void changeBuildVersionCode(@NonNull String versionCode);

    @AnyThread
    void changeBuildVersionName(@NonNull String versionName);

    @AnyThread
    void changeStethoState(boolean enabled);

    @AnyThread
    void changeLeakCanaryState(boolean enabled);

    @AnyThread
    void changeTinyDancerState(boolean enabled);

    @AnyThread
    void showMessage(@NonNull String message);

    @AnyThread
    void showAppNeedsToBeRestarted();
}
