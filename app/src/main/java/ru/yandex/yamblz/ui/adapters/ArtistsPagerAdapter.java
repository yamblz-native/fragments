package ru.yandex.yamblz.ui.adapters;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

import ru.yandex.yamblz.artists.ArtistModel;
import ru.yandex.yamblz.artists.DataSingleton;
import ru.yandex.yamblz.ui.fragments.ArtistFragmentBuilder;

public class ArtistsPagerAdapter extends FragmentStatePagerAdapter {
    List<ArtistModel> artistModels=DataSingleton.get().getArtists();
    public ArtistsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return new ArtistFragmentBuilder(position).build();
    }

    @Override
    public int getCount() {
        return artistModels.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return artistModels.get(position).getName();
    }
}
