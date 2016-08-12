package ru.yandex.provider.network;

import android.util.Log;

import java.io.IOException;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.yandex.yamblz.artistmodel.Artist;

/**
 * Created by root on 8/9/16.
 */
public class ContentLoader {

    public static Artist[] loadArtists() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://raw.githubusercontent.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        List<Artist> artistList = null;
        try {
            artistList = retrofit.create(ArtistService.class)
                    .listArtists().execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return artistList.toArray(new Artist[artistList.size()]);
    }

}
