package ru.yandex.yamblz.homework.artists.interfaces;

import ru.yandex.yamblz.homework.base.BasePresenter;

/**
 * Created by platon on 15.07.2016.
 */
public interface ArtistsPresenter extends BasePresenter<ArtistsView>
{
    void fetchArtists();
}
