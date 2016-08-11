package ru.yandex.yamblz.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.List;

import butterknife.BindView;
import ru.yandex.yamblz.ArtistLoader;
import ru.yandex.yamblz.ArtistLoader.ArtistLoaderListener;
import ru.yandex.yamblz.R;
import ru.yandex.yamblz.euv.shared.model.Artist;
import ru.yandex.yamblz.ui.adapters.RecyclerAdapter;

public class ArtistListRecyclerFragment extends BaseFragment implements ArtistLoaderListener {
    private ArtistLoader loader;
    private RecyclerAdapter adapter;

    @BindView(R.id.progress_bar) ProgressBar progressBar;
    @BindView(R.id.recycler) RecyclerView recycler;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loader = new ArtistLoader(this, getContext());
        loader.execute();
    }


    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_artist_list_recycler, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        recycler.setAdapter(adapter = new RecyclerAdapter(getContext(), recycler));
    }


    @Override
    public void onArtistListLoaded(List<Artist> artists) {
        if (artists == null || !isFragmentAlive()) return;
        adapter.setArtists(artists);
        recycler.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }


    @Override
    public void onDestroy() {
        if (loader != null) {
            loader.cancel(false);
            loader = null;
        }
        super.onDestroy();
    }
}
