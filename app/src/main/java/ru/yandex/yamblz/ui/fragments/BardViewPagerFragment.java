package ru.yandex.yamblz.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import icepick.Icepick;
import ru.yandex.yamblz.ApplicationComponent;
import ru.yandex.yamblz.R;
import ru.yandex.yamblz.di.module.FragmentArgumentModule;
import ru.yandex.yamblz.domain.model.presentation.BardUI;
import ru.yandex.yamblz.ui.adapters.BardPagerAdapter;
import ru.yandex.yamblz.ui.contcract.BardListContract;
import rx.Observable;

/**
 * Created by Александр on 10.08.2016.
 */

public class BardViewPagerFragment extends BaseFragment
        implements BardListContract.BardListView {

    @BindView(R.id.tl_cations)
    protected TabLayout tlCaptions;
    @BindView(R.id.vp_content)
    protected ViewPager vpContent;

    protected BardPagerAdapter bardPagerAdapter;

    @Inject
    protected BardListContract.BardListPresenter bardListPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_view_page, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Icepick.restoreInstanceState(bardListPresenter, savedInstanceState);
        bardListPresenter.bindView(this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Icepick.saveInstanceState(bardListPresenter, outState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        bardListPresenter.unbindView(this);
    }

    @Override
    protected void setUpComponent(ApplicationComponent applicationComponent) {
        applicationComponent.plus(FragmentArgumentModule.empty()).inject(this);
    }

    @Override
    public Observable<Void> clickRefresh() {
        return null;
    }

    @Override
    public Observable<BardUI> clickOnBard() {
        return null;
    }

    @Override
    public Observable<Boolean> clickRetry() {
        return null;
    }

    @Override
    public void showRetryError(boolean isShow) {

    }

    @Override
    public void showData(List<BardUI> data) {
        bardPagerAdapter = new BardPagerAdapter(getChildFragmentManager(), data);
        vpContent.setAdapter(bardPagerAdapter);
        tlCaptions.setupWithViewPager(vpContent);
        //tlCaptions.setTabsFromPagerAdapter(bardPagerAdapter);
    }

}
