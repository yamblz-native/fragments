package ru.yandex.yamblz.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.BindView;
import ru.yandex.yamblz.R;
import ru.yandex.yamblz.data.Artist;
import ru.yandex.yamblz.ui.adapters.ArtistFragmentsAdapter;
import ru.yandex.yamblz.ui.other.ArtistProviderInterface;
import ru.yandex.yamblz.ui.other.ArtistSelectedInterface;
import ru.yandex.yamblz.ui.other.UpdateArtistsListener;

public class ViewPagerFragment extends BaseFragment implements UpdateArtistsListener, ArtistSelectedInterface {

    public static final String TAG = "view_pager_fragment";
    private static final String TAG_ARTIST_ID = "selectedArtistId";

    public static ViewPagerFragment newInstance(int artistId, Fragment target) {
        ViewPagerFragment fragment = new ViewPagerFragment();
        fragment.setTargetFragment(target, 1);
        Bundle args = new Bundle();
        args.putInt(TAG_ARTIST_ID, artistId);
        fragment.setArguments(args);
        return fragment;
    }

    @BindView(R.id.viewPager)
    ViewPager pager;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;

    ArtistFragmentsAdapter adapter;

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pager, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new ArtistFragmentsAdapter(getChildFragmentManager());
        pager.setAdapter(adapter);

        if(getTargetFragment() instanceof ArtistProviderInterface) {
            ArtistProviderInterface provider = (ArtistProviderInterface) getTargetFragment();
            List<Artist> data = provider.getArtists();
            adapter.setData(data);
            tabLayout.setupWithViewPager(pager);
            scrollToSelectedPosition(data);
        }
    }

    private void scrollToSelectedPosition(List<Artist> artists) {
        int artistId = getArguments().getInt(TAG_ARTIST_ID);
        if (artistId < 0)
            return;
        for (int i = 0; i < artists.size(); i++) {
            if (artistId == artists.get(i).getId()) {
                pager.setCurrentItem(i);
            }
        }
    }

    @Override
    public void onArtistsUpdate(List<Artist> artists) {
        adapter.setData(artists);
    }

    @Override
    public int getSelectedArtistId() {
        if (pager != null)
            return adapter.getArtist(pager.getCurrentItem()).getId();
        else
            return -1;
    }
}
