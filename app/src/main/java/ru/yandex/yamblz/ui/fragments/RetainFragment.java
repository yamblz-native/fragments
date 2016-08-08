package ru.yandex.yamblz.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import ru.yandex.yamblz.data.Artist;
import ru.yandex.yamblz.ui.other.ArtistProviderInterface;

/**
 * Created by Volha on 07.08.2016.
 */

public class RetainFragment extends Fragment implements ArtistProviderInterface {

    public static final String TAG = "retain";

    private List<Artist> artists = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public List<Artist> getArtists() {
        return artists;
    }

    @Override
    public void setArtists(List<Artist> artists) {
        this.artists.clear();
        this.artists.addAll(artists);
    }

    @Override
    public Artist getArtistById(int id) {
        for (Artist artist : artists) {
            if (artist.getId() == id) {
                return artist;
            }
        }
        return null;
    }
}
