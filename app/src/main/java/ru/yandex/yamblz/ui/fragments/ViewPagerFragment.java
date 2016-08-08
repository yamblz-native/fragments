package ru.yandex.yamblz.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.yandex.yamblz.R;
import ru.yandex.yamblz.data.Artist;
import ru.yandex.yamblz.retrofit.ApiServices;
import ru.yandex.yamblz.ui.adapters.ArtistFragmentsAdapter;
import ru.yandex.yamblz.ui.other.ArtistProviderInterface;
import ru.yandex.yamblz.ui.other.UpdateArtistsListener;
import ru.yandex.yamblz.ui.views.SlidingTabLayout;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ViewPagerFragment extends BaseFragment implements UpdateArtistsListener{

    public static final String TAG = "view_pager_fragment";

    @BindView(R.id.viewPager)
    ViewPager pager;
    @BindView(R.id.tabLayout)
    SlidingTabLayout tabLayout;

    ArtistFragmentsAdapter adapter;

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_content, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        adapter = new ArtistFragmentsAdapter(getChildFragmentManager());
        pager.setAdapter(adapter);

        List<Artist> data = new ArrayList<>();
        if(getTargetFragment() instanceof ArtistProviderInterface) {
            ArtistProviderInterface provider = (ArtistProviderInterface) getTargetFragment();
            data.addAll(provider.getArtists());
        }

        adapter.setData(data);
        tabLayout.setViewPager(pager);
    }

    @Override
    public void onArtistsUpdate(List<Artist> artists) {
        adapter.setData(artists);
        tabLayout.setViewPager(pager);
    }
}
