package ru.yandex.yamblz.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ru.yandex.yamblz.R;
import ru.yandex.yamblz.provider.DataProvider;
import ru.yandex.yamblz.singerscontracts.Singer;
import ru.yandex.yamblz.ui.adapters.SingersPagerAdapter;

public class TabsFragment extends BaseFragment {

    @BindView(R.id.singers_pager)
    ViewPager singersPager;

    @BindView(R.id.tabs)
    TabLayout tabLayout;

    @Inject
    DataProvider mDataProvider;

    private Unbinder mUnbinder;

    public static TabsFragment newInstance() {

        Bundle args = new Bundle();

        TabsFragment fragment = new TabsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getAppComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tabs_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mUnbinder = ButterKnife.bind(this, view);
    }

    @Override
    public void onResume() {
        super.onResume();
        mDataProvider.getSingers(mSingersCallback);
    }

    @Override
    public void onPause() {
        super.onPause();

        mDataProvider.cancel(mSingersCallback);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        mUnbinder.unbind();
    }

    private DataProvider.Callback<List<Singer>> mSingersCallback = new DataProvider.Callback<List<Singer>>() {
        @Override
        public void postResult(List<Singer> result) {
            singersPager.setAdapter(new SingersPagerAdapter(getChildFragmentManager(), result));
            tabLayout.setupWithViewPager(singersPager);
        }
    };
}
