package ru.yandex.yamblz.ui.fragments;

import android.content.res.Configuration;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pushtorefresh.storio.contentresolver.StorIOContentResolver;
import com.pushtorefresh.storio.contentresolver.impl.DefaultStorIOContentResolver;
import com.pushtorefresh.storio.contentresolver.operations.get.DefaultGetResolver;
import com.pushtorefresh.storio.contentresolver.queries.Query;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import ru.yandex.yamblz.R;
import ru.yandex.yamblz.model.Artist;
import rx.android.schedulers.AndroidSchedulers;
import timber.log.Timber;

public class ContentFragment extends BaseFragment {
    private static final String TAG_LAST_POSITION = "last_pos";
    private static final String TAG_LIST_ARTIST = "list_artist";
    @Nullable
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @Nullable
    @BindView(R.id.sliding_tabs)
    TabLayout slidingTabLayout;

    @Inject
    StorIOContentResolver storIOContentResolver;

    private FragmentManager fragmentManager;
    private int orientation;
    private int lastPositionViewPager;
    private List<Artist> artistList;

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            return inflater.inflate(R.layout.landscape, container, false);
        } else {
            return inflater.inflate(R.layout.fragment_content, container, false);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //получаю уведомление из провайдера и обновляю данные
        getContext().getContentResolver().registerContentObserver(Uri.parse("content://" + "ru.yandex.yamblz" + "/artists"), true, new ContentObserver(new Handler()) {
            @Override
            public void onChange(boolean selfChange) {
                super.onChange(selfChange);
                loadArtists();
            }
        });

        fragmentManager = getChildFragmentManager();
        if (savedInstanceState != null) {
            lastPositionViewPager = savedInstanceState.getInt(TAG_LAST_POSITION);
            artistList = savedInstanceState.getParcelableArrayList(TAG_LIST_ARTIST);
            initForOrientation();
        } else {
            loadArtists();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (viewPager != null) {
            lastPositionViewPager = viewPager.getCurrentItem();
        }
    }

    public void setViewPagerPosition(int position) {
        lastPositionViewPager = position;
    }

    private void loadArtists() {
        Timber.d("refresh");
        StorIOContentResolver storIOContentResolver = DefaultStorIOContentResolver.builder()
                .contentResolver(getContext().getContentResolver())
                .build();

        storIOContentResolver
                .get()
                .listOfObjects(Artist.class)
                .withQuery(Query.builder()
                        .uri(Uri.parse("content://" + "ru.yandex.yamblz" + "/artists"))
                        .build())
                .withGetResolver(new DefaultGetResolver<Artist>() {
                    @NonNull
                    @Override
                    public Artist mapFromCursor(@NonNull Cursor cursor) {
                        // не захотелось чтобы storIO генерил автоматически
                        Artist artist = new Artist(
                                cursor.getInt(cursor.getColumnIndex("rowid")),
                                cursor.getString(cursor.getColumnIndex("name")),
                                cursor.getString(cursor.getColumnIndex("link")),
                                cursor.getInt(cursor.getColumnIndex("tracks")),
                                cursor.getInt(cursor.getColumnIndex("albums")),
                                cursor.getString(cursor.getColumnIndex("cover_big")),
                                cursor.getString(cursor.getColumnIndex("cover_small")),
                                cursor.getString(cursor.getColumnIndex("description")),
                                cursor.getString(cursor.getColumnIndex("genres_list"))
                        );
                        cursor.close();
                        return artist;
                    }
                })
                .prepare()
                .asRxObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(artists -> {
                    artistList = new ArrayList<>(artists.size());
                    artistList.addAll(artists);
                    initForOrientation();
                });
    }

    private void initForOrientation() {
        if (viewPager != null) {
            viewPager.setAdapter(new FragmentPagerAdapter(fragmentManager, artistList));
            slidingTabLayout.setupWithViewPager(viewPager);
            viewPager.setCurrentItem(lastPositionViewPager);
            slidingTabLayout.setScrollPosition(lastPositionViewPager, 0f, true);
        }
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            ListFragment listFragment = (ListFragment) fragmentManager.findFragmentById(R.id.list_fragment);
            if (listFragment != null) {
                listFragment.loadArtists(artistList);
                listFragment.setFragmentClickedList(lastPositionViewPager);
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(TAG_LAST_POSITION, lastPositionViewPager);
        outState.putParcelableArrayList(TAG_LIST_ARTIST, (ArrayList<? extends Parcelable>) artistList);
    }

}
