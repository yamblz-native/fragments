package ru.yandex.yamblz.ui.fragments;

/**
 * Created by shmakova on 08.08.16.
 */

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pushtorefresh.storio.contentresolver.StorIOContentResolver;
import com.pushtorefresh.storio.contentresolver.impl.DefaultStorIOContentResolver;
import com.pushtorefresh.storio.contentresolver.queries.Query;

import butterknife.BindView;

import ru.yandex.yamblz.R;
import ru.yandex.yamblz.data.models.Artist;
import ru.yandex.yamblz.data.models.ArtistStorIOContentResolverGetResolver;
import ru.yandex.yamblz.ui.activities.MainActivity;
import ru.yandex.yamblz.ui.adapters.ArtistsAdapter;
import ru.yandex.yamblz.utils.AppConfig;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class ArtistsListFragment extends BaseFragment {
    private Subscription subscription;

    @BindView(R.id.artists_list)
    RecyclerView recyclerView;

    private OnListItemCLickListener onListItemCLickListener;

    public interface OnListItemCLickListener {
        void onListItemClick(Artist artist);
    }

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_artists_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        updateToolBar();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        loadArtists();
    }

    /**
     * Loads artists and sends it to recyclerView
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

                    ArtistsAdapter artistsAdapter = new ArtistsAdapter(artistsList, (position) -> {
                        Artist artist = artistsList.get(position);
                        onListItemCLickListener.onListItemClick(artist);
                    });
                    recyclerView.setAdapter(artistsAdapter);
                }, throwable -> Timber.e(throwable.toString()));
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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (!(getActivity() instanceof OnListItemCLickListener)) {
            throw new ClassCastException(getActivity().toString() + " must implement " +
                    OnListItemCLickListener.class.getName());
        }

        onListItemCLickListener = (OnListItemCLickListener) getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onListItemCLickListener = null;
    }
}