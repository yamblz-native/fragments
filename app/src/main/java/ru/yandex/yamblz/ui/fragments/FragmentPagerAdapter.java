package ru.yandex.yamblz.ui.fragments;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

import ru.yandex.yamblz.model.Artist;

/**
 * Created by SerG3z on 08.08.16.
 */

public class FragmentPagerAdapter extends FragmentStatePagerAdapter {

    private List<Artist> artistList;

    public FragmentPagerAdapter(FragmentManager fm, List<Artist> list) {
        super(fm);
        this.artistList = list;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return artistList.get(position).getName();
    }

    @Override
    public Fragment getItem(int position) {
        return PageFragment.newInstance(artistList.get(position));
    }

    @Override
    public int getCount() {
        return artistList.size();
    }
}
