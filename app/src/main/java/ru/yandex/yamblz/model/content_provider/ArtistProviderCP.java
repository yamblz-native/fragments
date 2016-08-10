package ru.yandex.yamblz.model.content_provider;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import ru.yandex.yamblz.model.Artist;
import ru.yandex.yamblz.model.ArtistContract;
import ru.yandex.yamblz.model.ArtistProvider;


public class ArtistProviderCP implements ArtistProvider {
    private Context mContext;
    private List<Artist> mArtists;

    public ArtistProviderCP(Context context) {
        mContext = context.getApplicationContext();
        mArtists = getList();
        if (mArtists == null) {
            mArtists = new ArrayList<>();
        }
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
        ContentResolver contentResolver = mContext.getContentResolver();
        Cursor cursor = contentResolver.query(ArtistContract.Artist.CONTENT_URI,
                new String[]{
                        ArtistContract.Artist.ID,
                        ArtistContract.Artist.NAME,
                        ArtistContract.Artist.TRACKS,
                        ArtistContract.Artist.ALBUMS,
                        ArtistContract.Artist.GENRES,
                        ArtistContract.Artist.BIG_COVER,
                        ArtistContract.Artist.SMALL_COVER,
                        ArtistContract.Artist.URL,
                        ArtistContract.Artist.DESCRIPTION,
                },
                null,
                null,
                ArtistContract.Artist.NAME);
        return new ArtistMaker().getArtistsFromCursor(cursor);
    }
}