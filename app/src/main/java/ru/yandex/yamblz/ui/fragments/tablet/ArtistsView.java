package ru.yandex.yamblz.ui.fragments.tablet;


import java.util.List;

import ru.yandex.yamblz.model.Artist;
import ru.yandex.yamblz.ui.fragments.ArtistClickHandler;

public interface ArtistsView {
    void showContent(List<Artist> artists, ArtistClickHandler handler);
}
