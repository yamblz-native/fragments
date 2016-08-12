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
import ru.yandex.yamblz.R;
import ru.yandex.yamblz.model.Artist;
import ru.yandex.yamblz.ui.adapters.RecyclerViewAdapter;

/**
 * Created by SerG3z on 08.08.16.
 */

public class ListFragment extends BaseFragment {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private int positionRecycler;

    private RecyclerViewAdapter adapter;

    public static ListFragment newInstance() {
        return new ListFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.recycler_view, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new RecyclerViewAdapter();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener((position, v) -> setFragmentClickedList(position));
    }

    public void loadArtists(List<Artist> artistList) {
        adapter.setAllArtists(artistList);
    }

    public void setFragmentClickedList(int position) {
        Artist artist = adapter.getItem(position);
        if (artist != null) {
            PageFragment pageFragment = (PageFragment) getParentFragment().getChildFragmentManager().findFragmentById(R.id.page_fragment);
            if (pageFragment != null) {
                pageFragment.setArtist(artist);
                recyclerView.scrollToPosition(position);
            }
        }
        positionRecycler = position;
    }

    @Override
    public void onPause() {
        super.onPause();
        ContentFragment contentFragment = (ContentFragment) getActivity().getSupportFragmentManager().findFragmentByTag("content");
        contentFragment.setViewPagerPosition(positionRecycler);
    }
}
