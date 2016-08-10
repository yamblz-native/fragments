package ru.yandex.yamblz.di.module;

import dagger.Module;
import dagger.Provides;
import ru.yandex.yamblz.domain.datasource.BardService;
import ru.yandex.yamblz.domain.datasource.IDataSource;
import ru.yandex.yamblz.domain.datasource.NetworkDataSource;
import ru.yandex.yamblz.domain.mapper.Mapper;
import ru.yandex.yamblz.domain.model.api.NetBard;
import ru.yandex.yamblz.domain.model.core.Bard;

/**
 * Created by Александр on 08.08.2016.
 */
@Module
public class DataSourceModule {

    @Provides
    IDataSource provideDataSource(BardService bardService, Mapper<NetBard, Bard> mapper){
        return new NetworkDataSource(bardService, mapper);
    }
}
