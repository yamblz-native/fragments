package ru.yandex.yamblz.ui.fragments.list;


import java.util.List;

import ru.yandex.yamblz.model.Artist;
import ru.yandex.yamblz.ui.fragments.ArtistClickHandler;

/**
 * Created by aleien on 09.08.16.
 */

public interface ArtistsView {
    void showContent(List<Artist> artists, ArtistClickHandler handler);
}
