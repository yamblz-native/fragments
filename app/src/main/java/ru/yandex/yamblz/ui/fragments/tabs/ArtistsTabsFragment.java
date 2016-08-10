package ru.yandex.yamblz.ui.fragments.tabs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnPageChange;
import butterknife.Optional;
import icepick.Icepick;
import icepick.State;
import ru.yandex.yamblz.App;
import ru.yandex.yamblz.ApplicationModule;
import ru.yandex.yamblz.R;
import ru.yandex.yamblz.model.Artist;
import ru.yandex.yamblz.ui.fragments.ArtistClickHandler;
import ru.yandex.yamblz.ui.fragments.BaseFragment;
import ru.yandex.yamblz.ui.fragments.list.ArtistsView;
import ru.yandex.yamblz.utils.adapters.ArtistsRecyclerAdapter;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

public class ArtistsTabsFragment extends BaseFragment implements ArtistsView, ArtistClickHandler {
    @Nullable
    @BindView(R.id.tabs_viewpager)
    ViewPager viewPager;
    @Nullable
    @BindView(R.id.tabs)
    TabLayout tabs;

    @Nullable
    @BindView(R.id.tabs_recycler)
    RecyclerView recycler;
    @Nullable ArtistsRecyclerAdapter listAdapter;
    @Nullable
    @BindView(R.id.tabs_container)
    FrameLayout container;

    CompositeSubscription subs = new CompositeSubscription();

    List<Artist> artistsList = new ArrayList<>();
    @State Artist currentArtist;
    @State int currentPage;

    @Inject
    ApplicationModule.Api api;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((App) getActivity().getApplication()).applicationComponent().inject(this);
        Icepick.restoreInstanceState(this, savedInstanceState);

        // Здесь ничего не проичходит, мы просто берем данные, ничего такого (:
        subs.add(api.getArtists()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::setData,
                        Throwable::printStackTrace
                ));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_artists_tabs, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Icepick.restoreInstanceState(this, savedInstanceState);
        if (artistsList.size() != 0)
            showContent(new ArrayList<>(artistsList), null);

        setupRecycler();
    }

    public void setData(List<Artist> data) {
        artistsList.clear();
        artistsList.addAll(data);
        if (currentArtist == null) currentArtist = data.get(0);

        setupRecycler();
        setupAdapter(artistsList, this);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Icepick.saveInstanceState(this, outState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        subs.clear();
    }

    @Override
    public void showContent(List<Artist> artists, ArtistClickHandler handler) {
        artistsList.clear();
        artistsList.addAll(artists);

        if (currentArtist == null) currentArtist = artistsList.get(0);
        setupAdapter(artists, handler);

        // Проверки в коде на наличие вьюх? Получается один фрагмент отвечает и за планшетную, и за
        // портретную ориентацию. Лучше сделать отдельный фрагмент для того и другого, мне кажется
        // Это тоже как-то неправильно. Мне кажется, адаптер должен в одном месте задаваться
        if (recycler != null) {
            if (listAdapter == null) listAdapter = new ArtistsRecyclerAdapter(artistsList, this);
            recycler.setAdapter(listAdapter);

            setFragment(new ArtistTabFragmentBuilder(currentArtist).build());
        }
    }

    private void setupAdapter(List<Artist> artists, ArtistClickHandler handler) {
        if (viewPager != null && tabs != null) {
            viewPager.setAdapter(new ArtistsTabsAdapter(getFragmentManager(), artists, handler));
            tabs.setupWithViewPager(viewPager);
            tabs.setVisibility(View.VISIBLE);
            viewPager.setCurrentItem(currentPage, false);
        }
    }

    @Optional
    @OnPageChange(value = R.id.tabs_viewpager,
            callback = OnPageChange.Callback.PAGE_SELECTED)
    public void onPageChanged(int page) {
        Timber.d("Selected page: %d", page);
        currentPage = page;
    }

    @Override
    public void artistClicked(Artist artist) {
        currentArtist = artist;
        setFragment(new ArtistTabFragmentBuilder(artist).build());
    }

    private void setFragment(Fragment fragment) {
        if (container != null) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.tabs_container, fragment)
                    .commit();
        }
    }

    private void setupRecycler() {
        if (recycler != null) {
            recycler.setLayoutManager(new LinearLayoutManager(getContext()));
            if (listAdapter == null)
                listAdapter = new ArtistsRecyclerAdapter(artistsList, this);
            recycler.setAdapter(listAdapter);
            if (currentArtist != null)
                setFragment(new ArtistTabFragmentBuilder(currentArtist).build());
        }
    }
}
