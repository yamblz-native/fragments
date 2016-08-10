package ru.yandex.yamblz.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ru.yandex.yamblz.ArtistLoader;
import ru.yandex.yamblz.ArtistLoader.ArtistLoaderListener;
import ru.yandex.yamblz.R;
import ru.yandex.yamblz.euv.shared.model.Artist;
import timber.log.Timber;

public class ArtistLoaderTabFragment extends BaseFragment implements ArtistLoaderListener {
    private ArtistLoader loader;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loader = new ArtistLoader(this, getContext());
        loader.execute();
    }


    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_content, container, false);
    }


    @Override
    public void onArtistListLoaded(List<Artist> artists) {
        if (artists == null) return;
        for (Artist artist : artists) {
            Timber.d(artist.toString());
        }
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
