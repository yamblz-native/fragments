package ru.yandex.yamblz.ui.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;

import javax.inject.Inject;
import javax.inject.Named;

import ru.yandex.yamblz.App;
import ru.yandex.yamblz.R;
import ru.yandex.yamblz.developer_settings.DeveloperSettingsModule;
import ru.yandex.yamblz.model.Artist;
import ru.yandex.yamblz.model.ArtistProvider;
import ru.yandex.yamblz.model.ArtistProviderImpl;
import ru.yandex.yamblz.ui.adapters.ArtistListAdapterCallbacks;
import ru.yandex.yamblz.ui.fragments.ArtistDetailDialogFragment;
import ru.yandex.yamblz.ui.fragments.ArtistListFragment;
import ru.yandex.yamblz.ui.fragments.ArtistPhotoFragment;
import ru.yandex.yamblz.ui.fragments.ArtistViewPagerFragment;
import ru.yandex.yamblz.Provider;
import ru.yandex.yamblz.ui.other.ViewModifier;

public class MainActivity extends BaseActivity implements
        ArtistPhotoFragment.Callbacks,
        ArtistViewPagerFragment.Callbacks,
        Provider,
        ArtistListAdapterCallbacks {

    @Inject
    @Named(DeveloperSettingsModule.MAIN_ACTIVITY_VIEW_MODIFIER)
    ViewModifier viewModifier;
    private ArtistProvider mArtistProvider;
    private ArtistViewPagerFragment mArtistViewPagerFragment;
    private ArtistListFragment mArtistListFragment;

    @SuppressLint("InflateParams") // It's okay in our case.
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.get(this).applicationComponent().inject(this);
        setContentView(viewModifier.modify(getLayoutInflater().inflate(R.layout.activity_master_detail, null)));

        mArtistProvider = new ArtistProviderImpl(getResources());

        FragmentManager fragmentManager = getSupportFragmentManager();
        if (savedInstanceState == null) {
            // Значит запустились первый раз
            mArtistViewPagerFragment = ArtistViewPagerFragment.newInstance(0);
            if (isPhone()) {
                fragmentManager
                        .beginTransaction()
                        .replace(R.id.main_frame_layout, mArtistViewPagerFragment)
                        .commit();
            } else {
                mArtistListFragment = new ArtistListFragment();
                fragmentManager
                        .beginTransaction()
                        .replace(R.id.main_frame_layout, mArtistListFragment)
                        .commit();

                fragmentManager
                        .beginTransaction()
                        .replace(R.id.photo_frame_layout, mArtistViewPagerFragment)
                        .commit();
            }
        } else {
            // Значит конфигурация изменилась
            mArtistViewPagerFragment = (ArtistViewPagerFragment) fragmentManager.findFragmentById(R.id.photo_frame_layout);
            mArtistListFragment = (ArtistListFragment) fragmentManager.findFragmentById(R.id.main_frame_layout);
        }
    }

    @Override
    public void onClickMoreInformation(Artist artist) {
        FragmentManager fm = getSupportFragmentManager();
        if (isPhone()) {
            ArtistDetailDialogFragment.newInstance(artist, true).show(fm, "detail_fragment");
        } else {
            ArtistDetailDialogFragment.newInstance(artist, false).show(fm, "detail_fragment");
        }
    }

    @Override
    public void onArtistInListSelected(Artist artist) {
        if (mArtistViewPagerFragment != null) {
            int position = mArtistProvider.getPositionForArtist(artist);
            mArtistViewPagerFragment.setCurrentItem(position);
        }
    }

    @Override
    public void onScrollViewPager(int position) {
        if (mArtistListFragment != null) {
            mArtistListFragment.scrollTo(position);
        }
    }

    @Override
    public ArtistProvider provideArtistProvider() {
        return mArtistProvider;
    }

    private boolean isPhone() {
        return findViewById(R.id.photo_frame_layout) == null;
    }
}