package ru.yandex.yamblz.model;

import java.util.List;

public interface ArtistProvider {
    public List<Artist> getArtists();

    public Artist getArtist(int position);

    public int getArtistCount();

    public int getPositionForArtist(Artist artist);
}
