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
import icepick.Icepick;
import icepick.State;
import ru.yandex.yamblz.Provider;
import ru.yandex.yamblz.R;
import ru.yandex.yamblz.ui.adapters.ArtistListAdapterCallbacks;
import ru.yandex.yamblz.ui.adapters.ArtistListRecyclerAdapter;

public class ArtistListFragment extends BaseFragment {
    @State
    int mPosition;

    @BindView(R.id.fragment_artist_list_recycler_view)
    RecyclerView mRecyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Icepick.restoreInstanceState(this, savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_artist_list, container, false);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Icepick.saveInstanceState(this, outState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ArtistListAdapterCallbacks listCallbacks = (ArtistListAdapterCallbacks) getActivity();
        Provider provider = (Provider) getActivity();

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(new ArtistListRecyclerAdapter(provider.provideArtistProvider(), Picasso.with(getContext()), listCallbacks));

        internalScrollTo(mPosition);
    }

    public void scrollTo(int position) {
        mPosition = position;
        internalScrollTo(mPosition);
    }

    private void internalScrollTo(int position) {
        if (mRecyclerView != null) {
            mRecyclerView.smoothScrollToPosition(position);
        }
    }
}