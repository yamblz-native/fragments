package ru.yandex.yamblz.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ru.yandex.yamblz.R;
import ru.yandex.yamblz.model.Artist;
import ru.yandex.yamblz.ui.recycler_view.ArtistAdapter;

/**
 * Created by Aleksandra on 06/08/16.
 */
public class ArtistsListFragment extends BaseFragment {
    public static final String FRAGMENT_TAG = "list of artists";

    @BindView(R.id.recycler_view_list_of_artist)
    RecyclerView rw;

    private ArtistAdapter adapter = new ArtistAdapter();

    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list, container, false);
        unbinder = ButterKnife.bind(this, v);

        rw.setLayoutManager(new LinearLayoutManager(getActivity()));
        rw.setAdapter(adapter);

        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    public void onNewDatasetAvailable(List<Artist> artistList) {
        adapter.setDataset(artistList);
    }


}
