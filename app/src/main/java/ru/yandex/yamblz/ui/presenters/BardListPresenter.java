package ru.yandex.yamblz.ui.presenters;

import android.support.annotation.NonNull;

import com.fernandocejas.frodo.annotation.RxLogObservable;
import com.fernandocejas.frodo.annotation.RxLogSubscriber;

import java.util.List;

import icepick.State;
import ru.yandex.yamblz.domain.mapper.Mapper;
import ru.yandex.yamblz.domain.model.core.Bard;
import ru.yandex.yamblz.domain.model.presentation.BardUI;
import ru.yandex.yamblz.domain.repository.BardRepository;
import ru.yandex.yamblz.domain.repository.IBardRepository;
import ru.yandex.yamblz.ui.contcract.BardListContract;
import ru.yandex.yamblz.ui.other.ListBundler;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Александр on 07.08.2016.
 */

public class BardListPresenter extends Presenter<BardListContract.BardListView> implements BardListContract.BardListPresenter {
    private final CompositeSubscription cs = new CompositeSubscription();

    private final IBardRepository bardRepository;
    private final Mapper<Bard, BardUI> bardUIMapper;

    @State(value = ListBundler.class )
    List<BardUI> bardUIs;

    public BardListPresenter(IBardRepository bardRepository, Mapper<Bard, BardUI> bardUIMapper) {
        this.bardRepository = bardRepository;
        this.bardUIMapper = bardUIMapper;
    }

    @Override
    public void unbindView(@NonNull BardListContract.BardListView view) {
        super.unbindView(view);
        cs.clear();
    }

    @Override
    public void bindView(@NonNull BardListContract.BardListView view) {
        super.bindView(view);
        if(bardUIs != null) view().showData(bardUIs);
        cs.add(getBardListObservable().subscribe(new BardListDataSubscriber()));
    }

    @NonNull
    @RxLogObservable
    private Observable<List<BardUI>> getBardListObservable() {
        return Observable.merge(Observable.just(null))
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(v -> view().showLoading(true))
                .observeOn(Schedulers.io())
                .flatMap(aVoid -> bardRepository.getAllBards().toObservable())
                .observeOn(AndroidSchedulers.mainThread())
                .observeOn(Schedulers.computation())
                .map(bards -> bardUIMapper.improove(bards))
                .observeOn(AndroidSchedulers.mainThread())
                .doOnEach(d -> view().showLoading(false));
    }
    @RxLogSubscriber
    private class BardListDataSubscriber extends Subscriber<List<BardUI>>{

        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            throw new RuntimeException(e);
        }

        @Override
        public void onNext(List<BardUI> bards) {
            BardListPresenter.this.bardUIs = bards;
            view().showData(bards);
        }
    }
}
