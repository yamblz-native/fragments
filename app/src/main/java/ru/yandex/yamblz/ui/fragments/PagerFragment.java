package ru.yandex.yamblz.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.yandex.yamblz.R;
import ru.yandex.yamblz.model.Artist;
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

    private MuAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_pager, container, false);
        ButterKnife.bind(this, v);

        adapter = new MuAdapter(getChildFragmentManager());
        pager.setAdapter(adapter);

        tabLayout.setupWithViewPager(pager);
        tabLayout.setTabsFromPagerAdapter(adapter);

        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        return v;
    }

    public class MuAdapter extends FragmentPagerAdapter {

        private List<Artist> dataset;

        @Override
        public CharSequence getPageTitle(int position) {
            return dataset.get(position).name();
        }

        public MuAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return CoverFragment.newInstance(dataset.get(position));
        }

        @Override
        public int getCount() {
            return dataset != null ? dataset.size() : 0;
        }

        public void setDataset(List<Artist> artists) {
            Timber.d("In set dataset");
            dataset = artists;
            notifyDataSetChanged();
            tabLayout.setTabsFromPagerAdapter(adapter);
            Timber.d("Dataset size %d", dataset.size());
        }
    }

    public void onNewArtistsAvailable(List<Artist> artists) {
        Timber.d("In onNewArtistsAvailable");
        adapter.setDataset(artists);
    }
}
