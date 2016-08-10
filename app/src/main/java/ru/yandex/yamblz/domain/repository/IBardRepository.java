package ru.yandex.yamblz.domain.repository;


import android.support.annotation.NonNull;

import org.javatuples.Pair;

import java.util.List;

import ru.yandex.yamblz.domain.model.core.Bard;
import ru.yandex.yamblz.domain.model.core.Genre;
import rx.Single;

/**
 * Created by Александр on 04.08.2016.
 */

public interface IBardRepository {
    @NonNull Single<List<Bard>> getAllBards();
    @NonNull Single<Bard> getBardById(long idBard);
    @NonNull Single<Genre> getGenreById(long idGenre);
    @NonNull Single<List<Bard>> getAllBardByGenre(long idGenre);
    @NonNull Single<List<Pair<Genre, List<Bard>>>> getAllBardsByGenre();
    @NonNull Single<List<Genre>> getAllGenres();
}
