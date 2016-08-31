package ru.yandex.yamblz.homework;

import android.content.Context;

import ru.yandex.yamblz.homework.artists.ArtistsPresenterImpl;
import ru.yandex.yamblz.homework.artists.interfaces.ArtistsPresenter;
import ru.yandex.yamblz.homework.data.source.DataSourceImpl;
import ru.yandex.yamblz.homework.data.source.IDataSource;

/**
 * Created by platon on 06.08.2016.
 */
public class Injection
{
    public static IDataSource provideDataSource(Context context)
    {
        return new DataSourceImpl(context.getContentResolver());
    }

    public static ArtistsPresenter providePresenter(IDataSource dataSource)
    {
        return new ArtistsPresenterImpl(dataSource);
    }
}
