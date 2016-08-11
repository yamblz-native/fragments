package ru.yandex.yamblz.ui.fragments.tablet;

import android.database.ContentObserver;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.List;

import butterknife.BindView;
import ru.yandex.yamblz.R;
import ru.yandex.yamblz.model.Artist;
import ru.yandex.yamblz.ui.fragments.ArtistClickHandler;
import ru.yandex.yamblz.ui.fragments.ArtistsFragment;
import ru.yandex.yamblz.ui.fragments.tabs.ArtistTabFragmentBuilder;
import ru.yandex.yamblz.utils.adapters.ArtistsRecyclerAdapter;

public class ArtistsListFragment extends ArtistsFragment implements ArtistClickHandler {
    @BindView(R.id.tabs_recycler)
    RecyclerView recycler;
    @BindView(R.id.tabs_container)
    FrameLayout container;

    ArtistsRecyclerAdapter listAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_artists_tablet, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupRecycler();
    }

    @Override
    protected void showContent(List<Artist> artists, ArtistClickHandler handler) {
        artistsList.clear();
        artistsList.addAll(artists);

        if (currentArtist == null) currentArtist = artistsList.get(0);
        if (recycler != null) {
            if (listAdapter == null) listAdapter = new ArtistsRecyclerAdapter(artistsList, this);
            recycler.setAdapter(listAdapter);

            setFragment(new ArtistTabFragmentBuilder(currentArtist).build());
        }
    }

    private void setFragment(Fragment fragment) {
        if (container != null) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.tabs_container, fragment)
                    .commit();
        }
    }

    @Override
    public void artistClicked(Artist artist) {
        currentArtist = artist;
        setFragment(new ArtistTabFragmentBuilder(artist).build());
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
