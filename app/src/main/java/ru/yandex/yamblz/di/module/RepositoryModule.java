package ru.yandex.yamblz.di.module;

import dagger.Module;
import dagger.Provides;
import ru.yandex.yamblz.domain.datasource.IDataSource;
import ru.yandex.yamblz.domain.repository.BardRepository;
import ru.yandex.yamblz.domain.repository.IBardRepository;

/**
 * Created by Александр on 08.08.2016.
 */
@Module
public class RepositoryModule {

    @Provides
    IBardRepository provideBardRepository(IDataSource dataSource){
        return new BardRepository(dataSource);
    }
}
