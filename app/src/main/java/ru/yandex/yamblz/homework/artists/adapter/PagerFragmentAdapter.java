package ru.yandex.yamblz.homework.artists.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;


import java.util.List;

import ru.yandex.yamblz.homework.artists.ArtistsFragment;
import ru.yandex.yamblz.homework.data.entity.Artist;

public class PagerFragmentAdapter extends FragmentStatePagerAdapter
{
    private final List<Artist> artists;

    public PagerFragmentAdapter(FragmentManager fm, List<Artist> artists)
    {
        super(fm);
        this.artists = artists;
    }

    @Override
    public Fragment getItem(int position)
    {
        return ArtistsFragment.newInstance(artists.get(position));
    }

    @Override
    public int getCount()
    {
        return artists == null ? 0 : artists.size();
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        return artists.get(position).getName();
    }
}
