package ru.yandex.yamblz.ui.adapters;


import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import ru.yandex.yamblz.lib.ContentProviderContract;
import ru.yandex.yamblz.ui.fragments.ArtistFragmentBuilder;

public class ArtistsPagerAdapter extends FragmentStatePagerAdapter {
    private Cursor cursor;
    public ArtistsPagerAdapter(FragmentManager fm,Cursor cursor) {
        super(fm);
        this.cursor=cursor;
    }

    @Override
    public Fragment getItem(int position) {
        cursor.moveToPosition(position);
        String name= cursor.getString(cursor.getColumnIndex(ContentProviderContract.Artists.NAME));
        return new ArtistFragmentBuilder(name).build();
    }

    @Override
    public int getCount() {
        return cursor.getCount();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        cursor.moveToPosition(position);
        return cursor.getString(cursor.getColumnIndex(ContentProviderContract.Artists.NAME));
    }
}
