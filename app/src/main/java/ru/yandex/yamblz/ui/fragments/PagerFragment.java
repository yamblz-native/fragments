package ru.yandex.yamblz.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.yandex.yamblz.R;
import ru.yandex.yamblz.model.Artist;
import ru.yandex.yamblz.ui.adapters.PagerAdapter;
import timber.log.Timber;

/**
 * Created by Aleksandra on 07/08/16.
 */
public class PagerFragment extends Fragment {
    public static final String FRAGMENT_TAG = "pager fragment";

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;

    @BindView(R.id.pager)
    ViewPager pager;

    PagerAdapter adapter;

    private List<Artist> artists = new LinkedList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_pager, container, false);
        ButterKnife.bind(this, v);

        adapter = new PagerAdapter(getChildFragmentManager());
        adapter.setDataset(artists);
        pager.setAdapter(adapter);

        tabLayout.setupWithViewPager(pager);
        tabLayout.setTabsFromPagerAdapter(adapter);

        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        return v;
    }

    public void onNewArtistsAvailable(List<Artist> artists) {
        Timber.d("In onNewArtistsAvailable");
        this.artists = artists;
        adapter.setDataset(artists);
        tabLayout.setTabsFromPagerAdapter(adapter);
    }
}
