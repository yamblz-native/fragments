package ru.yandex.yamblz.ui.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import ru.yandex.yamblz.model.ArtistProvider;
import ru.yandex.yamblz.ui.fragments.ArtistPhotoFragment;

public class ArtistPhotoPagerAdapter extends FragmentStatePagerAdapter {
    private ArtistProvider mArtistProvider;

    public ArtistPhotoPagerAdapter(FragmentManager fm, ArtistProvider artistProvider) {
        super(fm);
        mArtistProvider = artistProvider;
    }

    @Override
    public Fragment getItem(int position) {
        return ArtistPhotoFragment.newInstance(mArtistProvider.getArtist(position));
    }

    @Override
    public int getCount() {
        return mArtistProvider.getArtistCount();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mArtistProvider.getArtist(position).getName();
    }
}
