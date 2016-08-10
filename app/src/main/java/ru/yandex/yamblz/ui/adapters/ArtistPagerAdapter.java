package ru.yandex.yamblz.ui.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

import ru.yandex.yamblz.model.Artist;
import ru.yandex.yamblz.ui.fragments.ArtistPreviewFragment;

public class ArtistPagerAdapter extends FragmentStatePagerAdapter {

    private List<Artist> artists;

    public ArtistPagerAdapter(FragmentManager fragmentManager, List<Artist> artists) {
        super(fragmentManager);
        this.artists = artists;
    }

    @Override
    public Fragment getItem(int position) {
        return ArtistPreviewFragment.newInstance(position);
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
