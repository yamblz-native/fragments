package ru.yandex.yamblz.ui.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pushtorefresh.storio.contentresolver.StorIOContentResolver;
import com.pushtorefresh.storio.contentresolver.impl.DefaultStorIOContentResolver;
import com.pushtorefresh.storio.contentresolver.queries.Query;

import butterknife.BindView;
import ru.yandex.yamblz.R;
import ru.yandex.yamblz.data.models.ArtistStorIOContentResolverGetResolver;
import ru.yandex.yamblz.data.models.Artist;
import ru.yandex.yamblz.ui.activities.MainActivity;
import ru.yandex.yamblz.ui.adapters.ArtistsFragmentStatePagerAdapter;
import ru.yandex.yamblz.utils.AppConfig;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class ArtistsPagerFragment extends BaseFragment {
    private static final int PAGE_LIMIT = 7;
    private FragmentManager fragmentManager;
    private Subscription subscription;

    @BindView(R.id.pager)
    ViewPager viewPager;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;

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
        updateToolBar();
        fragmentManager = getChildFragmentManager();
        loadArtists();
    }

    /**
     * Loads artists and sends it to viewPager
     */
    private void loadArtists() {
        StorIOContentResolver storIOContentResolver = DefaultStorIOContentResolver.builder()
                .contentResolver(getContext().getContentResolver())
                .build();
        subscription = storIOContentResolver.get()
                .listOfObjects(Artist.class)
                .withQuery(Query.builder()
                        .uri(Uri.parse(AppConfig.ARTISTS_URI))
                        .build())
                .withGetResolver(new ArtistStorIOContentResolverGetResolver())
                .prepare()
                .asRxObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(artistsList -> {
                            for (Artist artist : artistsList) {
                                artist = Artist.convertArtistFields(artist);
                            }

                            viewPager.setAdapter(new ArtistsFragmentStatePagerAdapter(fragmentManager, artistsList));
                            tabLayout.setupWithViewPager(viewPager);
                            viewPager.setOffscreenPageLimit(PAGE_LIMIT);
                        },
                        throwable -> Timber.e(throwable.toString()));
    }

    @Override
    public void onDestroyView() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
        super.onDestroyView();
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
