package ru.yandex.yamblz.ui.presenters;

import android.support.annotation.NonNull;

import ru.yandex.yamblz.domain.mapper.Mapper;
import ru.yandex.yamblz.domain.model.core.Bard;
import ru.yandex.yamblz.domain.model.presentation.BardUI;
import ru.yandex.yamblz.domain.repository.BardRepository;
import ru.yandex.yamblz.domain.repository.IBardRepository;
import ru.yandex.yamblz.ui.contcract.BardDetailContract;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Александр on 07.08.2016.
 */

public class BardDetailPresenter extends Presenter<BardDetailContract.BardDetailView>
        implements BardDetailContract.BardsDetailPresenter{

    public static class BardDetailPresenterArgument{
        private final long idBard;

        public BardDetailPresenterArgument(long idBard) {
            this.idBard = idBard;
        }

        public long getIdBard() {
            return idBard;
        }
    }

    private final CompositeSubscription cs = new CompositeSubscription();

    private final BardDetailPresenterArgument args;
    private final Mapper<Bard, BardUI> bardUIMapper;
    private final IBardRepository bardRepository;

    public BardDetailPresenter(BardDetailPresenterArgument args, Mapper<Bard, BardUI> bardUIMapper, IBardRepository bardRepository) {
        this.args = args;
        this.bardUIMapper = bardUIMapper;
        this.bardRepository = bardRepository;
    }

    @Override
    public void bindView(@NonNull BardDetailContract.BardDetailView view) {
        super.bindView(view);
        cs.add(bardUpdate().subscribe(new BardSubscriber()));
    }

    public Observable<BardUI> bardUpdate(){
        return Observable.<Void>just(null)
                .observeOn(Schedulers.computation())
                .flatMap(v -> bardRepository.getBardById(args.getIdBard()).toObservable())
                .map(bardUIMapper::improove)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void unbindView(@NonNull BardDetailContract.BardDetailView view) {
        super.unbindView(view);
        cs.clear();
    }

    private class BardSubscriber extends Subscriber<BardUI>{

        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onNext(BardUI bardUI) {
            view().showBard(bardUI);
        }
    }


}
