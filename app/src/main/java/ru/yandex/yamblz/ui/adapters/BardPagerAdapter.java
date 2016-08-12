package ru.yandex.yamblz.ui.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

import ru.yandex.yamblz.domain.model.presentation.BardUI;
import ru.yandex.yamblz.ui.fragments.BardPreviewFragment;

/**
 * Created by Александр on 10.08.2016.
 */

public class BardPagerAdapter extends FragmentStatePagerAdapter {

    private final List<BardUI> dataSet;

    public BardPagerAdapter(FragmentManager fm, List<BardUI> dataSet) {
        super(fm);
        this.dataSet = dataSet;
    }

    @Override
    public Fragment getItem(int position) {
        return BardPreviewFragment.newInstance(dataSet.get(position));
    }

    @Override
    public int getCount() {
        return dataSet.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return dataSet.get(position).name();
    }
}
