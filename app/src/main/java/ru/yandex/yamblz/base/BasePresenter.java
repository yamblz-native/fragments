package ru.yandex.yamblz.base;

import java.lang.ref.WeakReference;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by aleien on 21.04.16.
 * Базовый класс для презентера, отвечает за сохранение ссылки на представление (вьюху).
 * Используем WeakReference во избежание ликов.
 */
public abstract class BasePresenter<V> {
    private WeakReference<V> view;
    private CompositeSubscription subs = new CompositeSubscription();

    public abstract void onStart();

    public void subscribe(Subscription sub) {
        subs.add(sub);
    }

    public void attachView(V view) {
        this.view = new WeakReference<>(view);
    }

    public void detachView() {
        subs.clear();
        view.clear();
        view = null;
    }

    protected V getView() {
        return view.get();
    }


}
