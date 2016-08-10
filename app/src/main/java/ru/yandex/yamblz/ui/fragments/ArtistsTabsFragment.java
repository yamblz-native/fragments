package ru.yandex.yamblz.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import ru.yandex.yamblz.App;
import ru.yandex.yamblz.R;
import ru.yandex.yamblz.domain.DataManager;
import ru.yandex.yamblz.ui.adapters.ArtistPagerAdapter;
import ru.yandex.yamblz.ui.views.SlidingTabLayout;

public class ArtistsTabsFragment extends Fragment {

    public static final String TAG = ArtistsTabsFragment.class.getName();

    SlidingTabLayout tabLayout;
    ViewPager vpArtists;
    PagerAdapter pagerAdapter;

    @Inject
    DataManager dataManager;

    public static ArtistsTabsFragment newInstance() {
        return new ArtistsTabsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.get(getContext()).applicationComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_artists_tabs, container, false);
        initViews(view);
        showArtists();
        return view;
    }

    private void initViews(View view) {
        tabLayout = (SlidingTabLayout) view.findViewById(R.id.tab_layout);
        vpArtists = (ViewPager) view.findViewById(R.id.view_pager);
    }

    private void showArtists() {
        pagerAdapter = new ArtistPagerAdapter(getChildFragmentManager(), dataManager.getArtists());
        vpArtists.setAdapter(pagerAdapter);
        tabLayout.setViewPager(vpArtists);
        tabLayout.setDistributeEvenly(true);
    }

}
