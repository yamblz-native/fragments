package ru.yandex.yamblz.ui.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

import ru.yandex.yamblz.data.models.Artist;
import ru.yandex.yamblz.ui.fragments.ArtistSlidePageFragmentBuilder;

/**
 * Created by shmakova on 07.08.16.
 */

public class ArtistsFragmentStatePagerAdapter extends FragmentStatePagerAdapter {
    private List<Artist> artists;

    public ArtistsFragmentStatePagerAdapter(FragmentManager fm, List<Artist> artists) {
        super(fm);
        this.artists = artists;
    }

    @Override
    public Fragment getItem(int position) {
        return new ArtistSlidePageFragmentBuilder(artists.get(position)).build();
    }

    @Override
    public int getCount() {
        return artists.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return artists.get(position).getName();
    }
}
