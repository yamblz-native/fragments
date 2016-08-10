package ru.yandex.yamblz.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SlidingPaneLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import ru.yandex.yamblz.R;
import ru.yandex.yamblz.model.Artist;
import ru.yandex.yamblz.ui.adapters.ArtistsPageAdapter;

public class ContentFragment extends BaseFragment {
    private static final String TAG = "ContentFragment";

    @BindView(R.id.vpArtists)
    ViewPager vpArtists;

    @BindView(R.id.tlArtistsTabs)
    TabLayout tlArtists;



    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_content, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        List<Artist> artists = getArtists();

        vpArtists.setAdapter(new ArtistsPageAdapter(artists, getFragmentManager()));


        for (int i = 0; i < artists.size(); i++) {
            tlArtists.addTab(tlArtists.newTab().setText(artists.get(i).getName()));
        }
        tlArtists.setupWithViewPager(vpArtists);
    }



    private List<Artist> getArtists() {
        ObjectMapper mapper = new ObjectMapper();
        InputStream jsonInputStream = getContext().getResources().openRawResource(R.raw.artists);
        List<Artist> artists = new LinkedList<>();

        try {
            artists = mapper.readValue(jsonInputStream, new TypeReference<List<Artist>>() {});
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close(jsonInputStream);
        }
        return artists;
    }

    private void close(InputStream is) {
        if (is != null) {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
