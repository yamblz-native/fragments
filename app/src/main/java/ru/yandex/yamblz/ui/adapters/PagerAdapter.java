package ru.yandex.yamblz.ui.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

import ru.yandex.yamblz.model.Artist;
import ru.yandex.yamblz.ui.fragments.CoverFragment;
import timber.log.Timber;

/**
 * Created by Aleksandra on 09/08/16.
 */

public class PagerAdapter extends FragmentStatePagerAdapter {

    private List<Artist> dataset;

    @Override
    public CharSequence getPageTitle(int position) {
        return dataset.get(position).name();
    }

    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return CoverFragment.newInstance(dataset.get(position));
    }

    @Override
    public int getCount() {
        return dataset != null ? dataset.size() : 0;
    }

    public void setDataset(List<Artist> artists) {
        dataset = artists;
        notifyDataSetChanged();
    }
}
