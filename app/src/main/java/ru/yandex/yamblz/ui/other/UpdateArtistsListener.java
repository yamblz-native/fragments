package ru.yandex.yamblz.ui.other;

import java.util.List;

import ru.yandex.yamblz.data.Artist;

/**
 * Created by Volha on 08.08.2016.
 */

public interface UpdateArtistsListener {

    void onArtistsUpdate(List<Artist> artists);
}
