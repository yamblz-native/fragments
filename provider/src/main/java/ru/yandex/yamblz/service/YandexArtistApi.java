package ru.yandex.yamblz.service;

import java.util.List;

import retrofit2.http.GET;
import ru.yandex.yamblz.model.Artist;
import rx.Observable;


/**
 * Created by SerG3z on 07.08.16.
 */

public interface YandexArtistApi {
    String SERVICE_ENDPOINT = "http://download.cdn.yandex.net/";

    @GET("mobilization-2016/artists.json")
    Observable<List<Artist>> getListArtist();
}