package ru.yandex.yamblz.domain.datasource;

import java.util.Arrays;
import java.util.List;

import ru.yandex.yamblz.domain.model.core.Bard;
import ru.yandex.yamblz.domain.model.core.Genre;
import ru.yandex.yamblz.domain.repository.exception.NotFoundException;
import rx.Single;

/**
 * Created by Александр on 09.08.2016.
 */

public class MockDataSource implements IDataSource{
    public final Genre firstGenre = Genre.create(0, "first");
    public final Genre secondGenre = Genre.create(1, "secondGenre");

    public final Bard firstBard = createBard(0, firstGenre);
    public final Bard secondBard = createBard(1, firstGenre, secondGenre);
    public final Bard afterSecondBard = createBard(2, secondGenre);

    @Override
    public Single<List<Bard>> getAllBards() {
        return Single.just(Arrays.asList(firstBard, secondBard, afterSecondBard));
    }

    @Override
    public Single<Bard> getBardById(long idBard) {
        if(idBard == 0 || idBard == 1 || idBard == 2)
            return Single.just(idBard == 0 ? firstBard: (idBard == 1)? secondBard: afterSecondBard);
        return Single.error(new NotFoundException("Not found bard with id = " + idBard));
    }

    Bard createBard(long id, Genre... genres){
        return Bard.create(id, "test", Arrays.asList(genres), 0, 0, "testLink", "testDescription", "http://avatars.yandex.net/get-music-content/c6672c12.p.125851/300x300", "http://avatars.yandex.net/get-music-content/c6672c12.p.125851/1000x1000");
    }
}
