package ru.yandex.yamblz.homework.artists.interfaces;

import android.support.v4.app.Fragment;

/**
 * Created by platon on 08.08.2016.
 */
public interface FragmentTransactionManager
{
    void addFragment(Fragment fragment, boolean toBackStack);
    void addFragment(Fragment fragment);
    void replaceFragment(Fragment fragment, boolean toBackStack);
    void removeFragment(Fragment fragment);
}
