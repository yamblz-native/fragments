package ru.yandex.yamblz.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import ru.yandex.yamblz.R;
import ru.yandex.yamblz.model.Artist;
import ru.yandex.yamblz.model.ArtistLab;
import ru.yandex.yamblz.model.ArtistProvider;
import ru.yandex.yamblz.ui.adapters.ArtistListRecyclerAdapter;

public class ArtistListFragment extends BaseFragment {
    private ArtistProvider mArtistProvider;

    @BindView(R.id.fragment_artist_list_recycler_view)
    RecyclerView mRecyclerView;

    public interface Callbacks {
        void onArtistInListSelected(Artist artist);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_artist_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        // TODO: Ещё раз посмотреть все зависимости
        mRecyclerView.setAdapter(new ArtistListRecyclerAdapter(ArtistLab.get(getContext()), Picasso.with(getContext()), (Callbacks) getActivity()));
    }
}
