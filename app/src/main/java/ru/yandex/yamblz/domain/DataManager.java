package ru.yandex.yamblz.domain;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Response;
import ru.yandex.yamblz.domain.datasources.network.YandexService;
import ru.yandex.yamblz.model.Artist;

public class DataManager {

    private YandexService service;

    private ArrayList<Artist> artists;

    public DataManager(YandexService service) {
        this.service = service;
    }

    public ArrayList<Artist> getArtists() {
        return artists;
    }

    public boolean loadArtists() throws IOException {
        Response<ArrayList<Artist>> response = service.getArtists().execute();
        if (response.isSuccessful()) {
            artists = service.getArtists().execute().body();
            return true;
        } else {
            return false;
        }
    }

}
