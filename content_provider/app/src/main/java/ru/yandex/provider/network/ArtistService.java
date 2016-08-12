package ru.yandex.provider.network;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import ru.yandex.yamblz.artistmodel.Artist;

/**
 * Created by root on 8/9/16.
 */
public interface ArtistService {
    @GET("Pipboy200011/YM2016/master/app/src/main/assets/artists.json")
    Call<List<Artist>> listArtists();
}
