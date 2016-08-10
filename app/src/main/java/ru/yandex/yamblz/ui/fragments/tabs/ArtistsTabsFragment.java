package ru.yandex.yamblz.ui.fragments.tabs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnPageChange;
import butterknife.Optional;
import ru.yandex.yamblz.R;
import ru.yandex.yamblz.model.Artist;
import ru.yandex.yamblz.ui.fragments.ArtistClickHandler;
import ru.yandex.yamblz.ui.fragments.ArtistsFragment;
import ru.yandex.yamblz.ui.fragments.tablet.ArtistsView;
import timber.log.Timber;

public class ArtistsTabsFragment extends ArtistsFragment implements ArtistsView {
    @BindView(R.id.tabs_viewpager)
    ViewPager viewPager;
    @BindView(R.id.tabs)
    TabLayout tabs;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_artists_tabs, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (artistsList.size() != 0)
            showContent(new ArrayList<>(artistsList), null);
    }

    @Override
    public void showContent(List<Artist> artists, ArtistClickHandler handler) {
        artistsList.clear();
        artistsList.addAll(artists);

        if (currentArtist == null) currentArtist = artistsList.get(0);
        setupAdapter(artists);
    }

    private void setupAdapter(List<Artist> artists) {
        if (viewPager != null && tabs != null) {
            viewPager.setAdapter(new ArtistsTabsAdapter(getChildFragmentManager(), artists));
            tabs.setupWithViewPager(viewPager);
            tabs.setVisibility(View.VISIBLE);
            viewPager.setCurrentItem(currentPage, false);
        }
    }

    @Optional
    @OnPageChange(value = R.id.tabs_viewpager,
            callback = OnPageChange.Callback.PAGE_SELECTED)
    public void onPageChanged(int page) {
        Timber.d("Selected page: %d", page);
        currentPage = page;
    }

    @Override
    public void artistClicked(Artist artist) {
        currentArtist = artist;
    }
}
