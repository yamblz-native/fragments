package ru.yandex.yamblz.model;

import android.content.res.Resources;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;

import ru.yandex.yamblz.R;

public class ArtistProviderImpl implements ArtistProvider {
    private Resources mResources;
    private List<Artist> mArtists;

    public ArtistProviderImpl(Resources resources) {
        mResources = resources;
        mArtists = getList();
    }

    @Override
    public List<Artist> getArtists() {
        return mArtists;
    }

    @Override
    public Artist getArtist(int position) {
        return mArtists.get(position);
    }

    @Override
    public int getArtistCount() {
        return mArtists.size();
    }

    @Override
    public int getPositionForArtist(Artist artist) {
        return mArtists.indexOf(artist);
    }

    private List<Artist> getList() {
        Type type = new TypeToken<List<Artist>>() {
        }.getType();

        Gson gson = new Gson();
        InputStream inStream = mResources.openRawResource(R.raw.artists);
        InputStreamReader inStreamReader = new InputStreamReader(inStream);

        return gson.fromJson(inStreamReader, type);
    }
}