package ru.yandex.yamblz.di.module;

import dagger.Module;
import dagger.Provides;
import ru.yandex.yamblz.domain.mapper.Mapper;
import ru.yandex.yamblz.domain.model.core.Bard;
import ru.yandex.yamblz.domain.model.presentation.BardUI;
import ru.yandex.yamblz.domain.repository.BardRepository;
import ru.yandex.yamblz.domain.repository.IBardRepository;
import ru.yandex.yamblz.ui.contcract.BardDetailContract;
import ru.yandex.yamblz.ui.contcract.BardListContract;
import ru.yandex.yamblz.ui.presenters.BardDetailPresenter;
import ru.yandex.yamblz.ui.presenters.BardListPresenter;

/**
 * Created by Александр on 08.08.2016.
 */
@Module
public class PresenterModule {

    @Provides
    BardDetailContract.BardsDetailPresenter bardDetailPresenter(IBardRepository bardRepository,
                                                                Mapper<Bard, BardUI> bardUIMapper,
                                           BardDetailPresenter.BardDetailPresenterArgument argument){
        return new BardDetailPresenter(argument, bardUIMapper, bardRepository);
    }

    @Provides
    BardListContract.BardListPresenter bardListPresenter(IBardRepository bardRepository,
                                                         Mapper<Bard, BardUI> bardUIMapper){
        return new BardListPresenter(bardRepository, bardUIMapper);
    }
}
