package ru.yandex.yamblz.domain.datasource;

import java.util.List;

import ru.yandex.yamblz.domain.mapper.Mapper;
import ru.yandex.yamblz.domain.model.api.NetBard;
import ru.yandex.yamblz.domain.model.core.Bard;
import rx.Observable;
import rx.Single;

/**
 * Created by Александр on 10.08.2016.
 */

public class NetworkDataSource implements IDataSource {

    private final BardService bardService;
    private final Mapper<NetBard, Bard> mapper;


    public NetworkDataSource(BardService bardService, Mapper<NetBard, Bard> mapper) {
        this.bardService = bardService;
        this.mapper = mapper;
    }

    @Override
    public Single<List<Bard>> getAllBards() {
        return bardService.getBards()
                .flatMap(netBards -> Observable.just(mapper.improove(netBards)))
                .toSingle();
    }

    @Override
    public Single<Bard> getBardById(long idBard) {
        return getAllBards()
                .flatMapObservable(Observable::from)
                .filter(bard -> bard.id() == idBard)
                .toSingle();
    }
}
