package ru.yandex.yamblz.ui.other;

import java.util.List;

import ru.yandex.yamblz.data.Artist;

/**
 * Created by Volha on 07.08.2016.
 */

public interface ArtistProviderInterface {
    List<Artist> getArtists();
    void setArtists(List<Artist> artists);
    Artist getArtistById(int id);
}
