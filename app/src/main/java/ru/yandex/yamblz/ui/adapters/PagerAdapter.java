package ru.yandex.yamblz.ui.adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTabHost;

import ru.yandex.yamblz.artistmodel.Artist;
import ru.yandex.yamblz.ui.fragments.ArtistTabFragment;

/**
 * Created by root on 8/10/16.
 */
public class PagerAdapter extends FragmentStatePagerAdapter {

    private final FragmentManager fm;
    private final Artist[] artists;

    public PagerAdapter(FragmentManager fm, Artist[] artists) {
        super(fm);
        this.fm = fm;
        this.artists = artists;
    }

    @Override
    public Fragment getItem(int position) {

        Fragment fragment = new ArtistTabFragment();

        Bundle args = new Bundle();
        args.putSerializable(Artist.class.getCanonicalName(), artists[position]);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public int getCount() {
        return artists.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return artists[position].getName();
    }
}
