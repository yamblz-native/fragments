package ru.yandex.provider.developer_settings;

import android.support.annotation.NonNull;

import ru.yandex.provider.ui.fragments.DeveloperSettingsFragment;

import dagger.Subcomponent;

@Subcomponent
public interface DeveloperSettingsComponent {
    void inject(@NonNull DeveloperSettingsFragment developerSettingsFragment);
}
