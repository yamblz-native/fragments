package ru.yandex.yamblz.di.module;

import dagger.Module;
import dagger.Provides;
import ru.yandex.yamblz.domain.mapper.BardMapper;
import ru.yandex.yamblz.domain.mapper.Mapper;
import ru.yandex.yamblz.domain.model.core.Bard;
import ru.yandex.yamblz.domain.model.presentation.BardUI;

/**
 * Created by Александр on 10.08.2016.
 */
@Module
public class MapperModule {

    @Provides
    Mapper<Bard, BardUI> bardUiMapper(){
        return new BardMapper();
    }
}
