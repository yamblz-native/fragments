package ru.yandex.yamblz.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.BindView;
import ru.yandex.yamblz.R;
import ru.yandex.yamblz.model.Artist;
import ru.yandex.yamblz.ui.adapters.ArtistsPageAdapter;
import ru.yandex.yamblz.ui.other.ArtistsContainer;

public class ContentFragment extends BaseFragment {
    private static final String TAG = "ContentFragment";
    public static final String EXTRA_ARTISTS_LIST = "extra_show_tab_layout";

    @BindView(R.id.vpArtists)
    ViewPager vpArtists;

    @BindView(R.id.tlArtistsTabs)
    TabLayout tlArtists;


    private ArtistsContainer artistsContainer;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            artistsContainer = (ArtistsContainer) getActivity();
        }catch (ClassCastException e){
            throw new ClassCastException("Activity needs to implement ArtistsContainer!");
        }
    }

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_content, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        List<Artist> artists = artistsContainer.getArtists();

        vpArtists.setAdapter(new ArtistsPageAdapter(artists, getFragmentManager()));

        for (int i = 0; i < artists.size(); i++) {
            tlArtists.addTab(tlArtists.newTab().setText(artists.get(i).getName()));
        }

        tlArtists.setupWithViewPager(vpArtists);
    }

}
