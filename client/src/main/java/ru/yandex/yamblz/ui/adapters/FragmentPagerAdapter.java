package ru.yandex.yamblz.ui.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

import ru.yandex.yamblz.euv.shared.model.Artist;
import ru.yandex.yamblz.ui.fragments.ArtistShortInfoBuilder;

public class FragmentPagerAdapter extends FragmentStatePagerAdapter {
    private final List<Artist> artists;

    public FragmentPagerAdapter(FragmentManager fm, List<Artist> artists) {
        super(fm);
        this.artists = artists;
    }


    @Override
    public Fragment getItem(int position) {
        return new ArtistShortInfoBuilder(artists.get(position)).build();
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return artists.get(position).getName();
    }


    @Override
    public int getCount() {
        return artists.size();
    }
}
