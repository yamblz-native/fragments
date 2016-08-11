package ru.yandex.yamblz.ui.fragments;

import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.BindView;
import ru.yandex.yamblz.ArtistLoader;
import ru.yandex.yamblz.ArtistLoader.ArtistLoaderListener;
import ru.yandex.yamblz.R;
import ru.yandex.yamblz.euv.shared.model.Artist;
import ru.yandex.yamblz.ui.adapters.FragmentPagerAdapter;
import ru.yandex.yamblz.ui.views.SlidingTabLayout;

public class ArtistListTabFragment extends BaseFragment implements ArtistLoaderListener {
    private ArtistLoader loader;

    @BindView(R.id.pager) ViewPager pager;
    @BindView(R.id.tabs) SlidingTabLayout tabs;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loader = new ArtistLoader(this, getContext());
        loader.execute();
    }


    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_artist_list_tabs, container, false);
    }


    @Override
    public void onArtistListLoaded(List<Artist> artists) {
        if (artists == null || !isFragmentAlive()) return;
        pager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager(), artists));
        tabs.setViewPager(pager);
        tabs.setSelectedIndicatorColors(getColor(R.color.yellow), getColor(R.color.red), getColor(R.color.black));
    }


    @SuppressWarnings("deprecation")
    private int getColor(@ColorRes int resId) {
        return getResources().getColor(resId);
    }


    @Override
    public void onDestroy() {
        if (loader != null) {
            loader.cancel(false);
            loader = null;
        }
        super.onDestroy();
    }
}
