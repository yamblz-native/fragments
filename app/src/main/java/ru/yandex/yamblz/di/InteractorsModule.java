package ru.yandex.yamblz.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.yandex.yamblz.domain.DataManager;
import ru.yandex.yamblz.domain.interactors.LoadArtistsListInteractor;

@Module(
        includes = ApplicationModule.class
)
public class InteractorsModule {

    @Singleton
    @Provides
    LoadArtistsListInteractor provideUpdateInteractor(DataManager dataManager) {
        return new LoadArtistsListInteractor(dataManager);
    }

}
