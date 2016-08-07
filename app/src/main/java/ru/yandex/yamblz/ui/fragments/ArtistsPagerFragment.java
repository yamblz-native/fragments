package ru.yandex.yamblz.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import ru.yandex.yamblz.R;
import ru.yandex.yamblz.managers.DataManager;
import ru.yandex.yamblz.data.models.Artist;
import ru.yandex.yamblz.ui.activities.MainActivity;
import ru.yandex.yamblz.ui.adapters.ArtistsFragmentStatePagerAdapter;
import ru.yandex.yamblz.ui.other.SlidingTabLayout;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ArtistsPagerFragment extends BaseFragment {
    private DataManager dataManager;
    private FragmentManager fragmentManager;

    @BindView(R.id.pager)
    ViewPager viewPager;
    @BindView(R.id.sliding_tab_layout)
    SlidingTabLayout slidingTabLayout;

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_artists_pager, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fragmentManager = getFragmentManager();
        dataManager = DataManager.getInstance(getContext());
        loadArtists();
    }

    private void loadArtists() {
        Observable.from(dataManager.getArtistsListCursor())
                .map(Artist::getArtistFromCursor)
                .toList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(artistsList -> {
                    viewPager.setAdapter(new ArtistsFragmentStatePagerAdapter(fragmentManager, artistsList));
                    slidingTabLayout.setViewPager(viewPager);
                });
    }


    /**
     * Updates toolbar
     */
    private void updateToolBar() {
        ActionBar actionBar = ((MainActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.main_activity_name);
            actionBar.setDisplayHomeAsUpEnabled(false);
        }
    }
}
