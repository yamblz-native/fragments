package ru.yandex.yamblz.ui.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

import ru.yandex.yamblz.data.Artist;
import ru.yandex.yamblz.ui.fragments.ArtistContentFragment;

/**
 * Created by Volha on 07.08.2016.
 */

public class ArtistFragmentsAdapter extends FragmentStatePagerAdapter {

    private List<Artist> items;

    public ArtistFragmentsAdapter(FragmentManager fm) {
        super(fm);
        items = new ArrayList<>();
    }

    public void setData(List<Artist> artists) {
        this.items.clear();
        this.items.addAll(artists);
        notifyDataSetChanged();
    }

    public Artist getArtist(int position) {
        return items.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        Artist artist = items.get(position);
        return ArtistContentFragment.newInstance(artist.getId(), artist.getCover().getBig());
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return items.get(position).getName();
    }

    @Override
    public int getCount() {
        return items.size();
    }

}
