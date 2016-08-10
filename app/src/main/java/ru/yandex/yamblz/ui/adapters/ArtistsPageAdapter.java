package ru.yandex.yamblz.ui.adapters;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

import ru.yandex.yamblz.model.Artist;
import ru.yandex.yamblz.ui.fragments.ArtistImageFragment;

public class ArtistsPageAdapter extends FragmentStatePagerAdapter  {

    private final List<Artist> artists;

    @Override
    public CharSequence getPageTitle(int position) {
        return artists.get(position).getName();
    }

    public ArtistsPageAdapter(@NonNull List<Artist> artists, FragmentManager manager){
        super(manager);
        this.artists = artists;
    }

    @Override
    public int getCount() {
        return artists.size();
    }

    @Override
    public Fragment getItem(int position) {
        Artist currentArtist = artists.get(position);
        ArtistImageFragment fragment = new ArtistImageFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ArtistImageFragment.EXTRA_ARTIST, currentArtist);
        fragment.setArguments(bundle);

        return fragment;
    }
}
