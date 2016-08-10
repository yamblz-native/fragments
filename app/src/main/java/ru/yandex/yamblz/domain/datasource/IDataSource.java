package ru.yandex.yamblz.domain.datasource;

import java.util.List;

import ru.yandex.yamblz.domain.model.core.Bard;
import rx.Single;

/**
 * Created by Александр on 05.08.2016.
 */

public interface IDataSource {
    Single<List<Bard>>  getAllBards();
    Single<Bard>        getBardById(long idBard);
}
