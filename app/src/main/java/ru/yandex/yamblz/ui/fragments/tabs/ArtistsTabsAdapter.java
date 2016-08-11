package ru.yandex.yamblz.ui.fragments.tabs;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

import ru.yandex.yamblz.model.Artist;
import ru.yandex.yamblz.ui.fragments.ArtistClickHandler;

class ArtistsTabsAdapter extends FragmentStatePagerAdapter {
    private final List<Artist> artists = new ArrayList<>();

    public ArtistsTabsAdapter(FragmentManager fm, List<Artist> artists) {
        super(fm);
        this.artists.addAll(artists);
    }

    @Override
    public Fragment getItem(int position) {
        return new ArtistTabFragmentBuilder(artists.get(position)).build();
    }

    @Override
    public int getCount() {
        return artists.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return artists.get(position).name;
    }
}
