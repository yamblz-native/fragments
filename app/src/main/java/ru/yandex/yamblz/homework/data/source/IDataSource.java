package ru.yandex.yamblz.homework.data.source;


import java.util.List;

import ru.yandex.yamblz.homework.data.entity.Artist;
import rx.Observable;

/**
 * Created by platon on 15.07.2016.
 */
public interface IDataSource
{
    Observable<List<Artist>> getArtists();
}
