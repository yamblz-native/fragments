package ru.yandex.yamblz.homework.artists.interfaces;

import android.support.v4.app.Fragment;

/**
 * Created by platon on 08.08.2016.
 */
public interface FragmentTransactionManager
{
    void addFragmentWithoutBackStack(Fragment fragment);
    void addFragment(Fragment fragment);
    void replaceFragmentWithoutBackStack(Fragment fragment);
    void replaceFragment(Fragment fragment);
    void removeFragment(Fragment fragment);
}
