package ru.yandex.yamblz.domain.repository;

import android.support.annotation.NonNull;

import com.fernandocejas.frodo.annotation.RxLogObservable;

import org.javatuples.Pair;

import java.util.List;

import ru.yandex.yamblz.domain.datasource.IDataSource;
import ru.yandex.yamblz.domain.model.core.Bard;
import ru.yandex.yamblz.domain.model.core.Genre;
import ru.yandex.yamblz.domain.repository.exception.NotFoundException;
import rx.Observable;
import rx.Single;
import rx.schedulers.Schedulers;

/**
 * Created by Александр on 05.08.2016.
 */

public class BardRepository implements IBardRepository {

    public final IDataSource dataSource;

    public BardRepository(IDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @NonNull
    @Override
    public Single<List<Bard>> getAllBards() {
        return dataSource.getAllBards();
    }

    @NonNull
    @Override
    public Single<Bard> getBardById(long idBard) {
        return dataSource.getBardById(idBard);
    }

    @NonNull
    @Override
    public Single<String> getGenreById(long idGenre) {
        return getAllGenres()
                .flatMapObservable(Observable::from)
                .first(g-> g.hashCode() == idGenre)
                .switchIfEmpty(Observable.error(new NotFoundException("Not found genre by id = " + idGenre)))
                .toSingle();
    }

    @NonNull
    @Override
    @RxLogObservable
    public Single<List<Bard>> getAllBardByGenre(long idGenre) {
        return dataSource.getAllBards()
                .observeOn(Schedulers.computation())
                .flatMapObservable(Observable::from)
                .filter(g-> containsGenreWithId(g.genres(), idGenre))
                .switchIfEmpty(Observable.error(new NotFoundException("Not found bard by " + idGenre)))
                .toList()
                .toSingle();
    }

    @NonNull
    @Override
    @RxLogObservable
    public Single<List<Pair<String, List<Bard>>>> getAllBardsByGenre() {
        return dataSource.getAllBards()
                .observeOn(Schedulers.computation())
                .flatMapObservable(Observable::from)
                .flatMap(bard -> Observable.from(bard.genres())
                        .map(it-> Pair.with(it,bard)))
                .groupBy(Pair::getValue0)
                .flatMap(genrePair -> genrePair
                        .map(Pair::getValue1)
                        .toList()
                        .map(it-> Pair.with(genrePair.getKey(), it)))
                .switchIfEmpty(Observable.error(new NotFoundException("Not found genres")))
                .toList()
                .toSingle();
    }

    @NonNull
    @Override
    public Single<List<String>> getAllGenres() {
        return dataSource.getAllBards()
                .observeOn(Schedulers.computation())
                .flatMapObservable(Observable::from)
                .flatMap(b->Observable.from(b.genres()))
                .switchIfEmpty(Observable.error(new NotFoundException("Not found genres")))
                .distinct()
                .toList()
                .toSingle();
    }

    private boolean containsGenreWithId(List<String> data, long idGenre){
        for (String g : data){
            if (g.hashCode() == idGenre) return true;
        }
        return false;
    }
}
