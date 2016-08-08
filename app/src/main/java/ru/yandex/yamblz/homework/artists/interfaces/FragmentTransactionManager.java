package ru.yandex.yamblz.homework.artists.interfaces;

import android.support.v4.app.Fragment;

/**
 * Created by platon on 08.08.2016.
 */
public interface FragmentTransactionManager
{
    void showFragment(Fragment fragment, boolean toBackStack);
    void showFragment(Fragment fragment);
    void removeFragment(Fragment fragment);
}
