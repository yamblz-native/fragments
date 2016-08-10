package ru.yandex.yamblz.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import ru.yandex.yamblz.R;
import ru.yandex.yamblz.ui.adapters.ArtistsRecyclerAdapter;
import ru.yandex.yamblz.ui.other.ArtistsContainer;
import ru.yandex.yamblz.ui.other.ShowImageCallback;

public class ArtistsListFragment extends BaseFragment {

    @BindView(R.id.rcArtists)
    RecyclerView rcArtists;

    private ArtistsContainer artistsContainer;
    private ShowImageCallback clickCallback;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            artistsContainer = (ArtistsContainer) getActivity();
            clickCallback = (ShowImageCallback) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity needs to implement ArtistsContainer and ShowImageCallback!");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_artists_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rcArtists.setAdapter(new ArtistsRecyclerAdapter(artistsContainer.getArtists(), clickCallback));
        rcArtists.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}
