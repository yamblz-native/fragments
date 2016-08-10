package ru.yandex.yamblz.di.module;

import dagger.Module;
import dagger.Provides;
import ru.yandex.yamblz.domain.datasource.IDataSource;
import ru.yandex.yamblz.domain.datasource.MockDataSource;

/**
 * Created by Александр on 08.08.2016.
 */
@Module
public class DataSourceModule {
    @Provides
    IDataSource provideDataSource(){
        return new MockDataSource();
    }
}
