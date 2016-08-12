package ru.yandex.yamblz.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import icepick.Icepick;
import ru.yandex.yamblz.ApplicationComponent;
import ru.yandex.yamblz.R;
import ru.yandex.yamblz.di.module.FragmentArgumentModule;
import ru.yandex.yamblz.di.module.PresenterModule;
import ru.yandex.yamblz.domain.model.core.Bard;
import ru.yandex.yamblz.domain.model.presentation.BardUI;
import ru.yandex.yamblz.ui.adapters.BardAdapter;
import ru.yandex.yamblz.ui.contcract.BardListContract;
import ru.yandex.yamblz.ui.event.ToBardPreviewEvent;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Александр on 07.08.2016.
 */

public class BardListFragment extends BaseFragment implements BardListContract.BardListView {
    public static final String TAG = "BardListFragment";
    public static final CompositeSubscription cs = new CompositeSubscription();
    @Inject
    protected BardListContract.BardListPresenter presenter;
    @Inject
    protected EventBus eventBus;

    @BindView(R.id.rv_content)
    protected RecyclerView rvListBard;
    private BardAdapter bardAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_bard, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bardAdapter = new BardAdapter();
        rvListBard.setLayoutManager(new LinearLayoutManager(getContext()));
        rvListBard.setAdapter(bardAdapter);
        cs.add(obserClicks());
        Icepick.restoreInstanceState(presenter, savedInstanceState);
        presenter.bindView(this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Icepick.saveInstanceState(presenter, outState);
    }

    private Subscription obserClicks() {
        return bardAdapter.clicks()
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(bardUI -> eventBus.post(ToBardPreviewEvent.create(BardPreviewFragment.newInstance(bardUI))));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.unbindView(this);
        cs.clear();
    }

    @Override
    protected void setUpComponent(ApplicationComponent applicationComponent) {
        applicationComponent.plus(FragmentArgumentModule.empty()).inject(this);
    }


    @Override
    public void showData(List<BardUI> data) {
        bardAdapter.setData(data);
    }
}
