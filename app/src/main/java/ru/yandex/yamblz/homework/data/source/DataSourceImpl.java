package ru.yandex.yamblz.homework.data.source;

import android.content.ContentResolver;

import com.pushtorefresh.storio.contentresolver.ContentResolverTypeMapping;
import com.pushtorefresh.storio.contentresolver.StorIOContentResolver;
import com.pushtorefresh.storio.contentresolver.impl.DefaultStorIOContentResolver;
import com.pushtorefresh.storio.contentresolver.queries.Query;

import java.util.List;

import ru.yandex.yamblz.homework.data.entity.Artist;
import ru.yandex.yamblz.homework.data.resolver.DeleteResolver;
import ru.yandex.yamblz.homework.data.resolver.GetResolver;
import ru.yandex.yamblz.homework.data.resolver.PutResolver;
import rx.Observable;

import static ru.yandex.yamblz.homework.data.source.ArtistsContract.*;

/**
 * Created by platon on 15.07.2016.
 */
public class DataSourceImpl implements IDataSource
{
    private StorIOContentResolver storIOContentResolver;

    public DataSourceImpl(ContentResolver contentResolver)
    {
        storIOContentResolver = DefaultStorIOContentResolver.builder()
                .contentResolver(contentResolver)
                .addTypeMapping(Artist.class, ContentResolverTypeMapping.<Artist>builder()
                        .putResolver(new PutResolver())
                        .getResolver(new GetResolver())
                        .deleteResolver(new DeleteResolver()).build())
                .build();
    }

    @Override
    public Observable<List<Artist>> getArtists()
    {
        return storIOContentResolver.get()
                .listOfObjects(Artist.class)
                .withQuery(Query.builder()
                          .uri(ARTISTS_URI)
                          .build())
                .prepare()
                .asRxObservable();
    }
}