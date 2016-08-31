package ru.yandex.yamblz.homework.artists.interfaces;

import java.util.List;

import ru.yandex.yamblz.homework.base.BaseView;
import ru.yandex.yamblz.homework.data.entity.Artist;

/**
 * Created by platon on 15.07.2016.
 */
public interface ArtistsView extends BaseView
{
    void showArtists(List<Artist> artists);
}
