package ru.yandex.yamblz.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.BindView;
import ru.yandex.yamblz.R;
import ru.yandex.yamblz.data.Artist;
import ru.yandex.yamblz.ui.adapters.ArtistsRecyclerAdapter;
import ru.yandex.yamblz.ui.other.ArtistProviderInterface;
import ru.yandex.yamblz.ui.other.OnArtistListItemClickListener;
import ru.yandex.yamblz.ui.other.UpdateArtistsListener;

/**
 * Created by Volha on 08.08.2016.
 */

public class ArtistsMasterFragment extends BaseFragment implements UpdateArtistsListener {

    public static final String TAG = "artist_master_tag";
    private static final String TAG_ARTIST_ID = "selectedArtistId";

    public static ArtistsMasterFragment newInstance(int artistId, Fragment target) {
        ArtistsMasterFragment fragment = new ArtistsMasterFragment();
        fragment.setTargetFragment(target, 1);
        Bundle args = new Bundle();
        args.putInt(TAG_ARTIST_ID, artistId);
        fragment.setArguments(args);
        return fragment;
    }

    @BindView(R.id.recycler)
    RecyclerView recyclerView;
    ArtistsRecyclerAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_master, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new ArtistsRecyclerAdapter((OnArtistListItemClickListener) getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        Fragment target = getTargetFragment();
        if (target instanceof ArtistProviderInterface) {
            ArtistProviderInterface provider = (ArtistProviderInterface) target;
            List<Artist> artists = provider.getArtists();
            adapter.setData(artists);
            scrollToSelectedPosition(artists);
        } else {
            throw new ClassCastException(getString(R.string.not_implemented_artist_interface_exception));
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (!(getActivity() instanceof OnArtistListItemClickListener))
            throw new ClassCastException(getString(R.string.need_on_artist_click_listener_exception));
    }

    private void scrollToSelectedPosition(List<Artist> artists) {
        int artistId = getArguments().getInt(TAG_ARTIST_ID);
        if (artistId < 0)
            return;
        for (int i = 0; i < artists.size(); i++) {
            if (artistId == artists.get(i).getId()) {
                recyclerView.scrollToPosition(i);
            }
        }
    }

    @Override
    public void onArtistsUpdate(List<Artist> artists) {
        adapter.setData(artists);
    }

}
