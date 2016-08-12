package ru.yandex.yamblz.domain.datasource;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.database.CursorJoiner;
import android.net.Uri;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import ru.yandex.yamblz.domain.model.core.Bard;
import rx.Observable;
import rx.Single;

/**
 * Created by Александр on 12.08.2016.
 */

public class ContentProviderDataSource implements IDataSource {
    private final ContentResolver contentResolver;

    public ContentProviderDataSource(ContentResolver contentResolver) {
        this.contentResolver = contentResolver;
    }

    @Override
    public Single<List<Bard>> getAllBards() {


        return Single.fromCallable(()->{
            ArrayList<Bard> result = new ArrayList<Bard>();
            Cursor query = contentResolver.query(Uri.parse(ProviderContract.BARD_URI), null, null, null, null);

            if (query != null){
                query.moveToFirst();
                do {
                    result.add(mapOne(query));
                }while (query.moveToNext());
                query.close();
                result.trimToSize();
            }
            return Collections.unmodifiableList(result);
        });
    }

    @Override
    public Single<Bard> getBardById(long idBard) {
        return getAllBards().flatMapObservable(Observable::from)
                .first(bard -> bard.id() == idBard)
                .toSingle();
    }

    private static Bard mapOne(Cursor c){
        final Bard.Builder blr = Bard.toBuilder();
        blr.id(c.getLong(c.getColumnIndex(ProviderContract.Bard.FIELD_ID)));
        blr.name(c.getString(c.getColumnIndex(ProviderContract.Bard.FIELD_NAME)));
        blr.description(c.getString(c.getColumnIndex(ProviderContract.Bard.FIELD_DESCRIPTION)));
        blr.bigImage(c.getString(c.getColumnIndex(ProviderContract.Bard.FIELD_BIG_PHOTO)));
        blr.smallImage(c.getString(c.getColumnIndex(ProviderContract.Bard.FIELD_SMALL_PHOTO)));
        blr.albums(c.getInt(c.getColumnIndex(ProviderContract.Bard.FIELD_ALBUMS)));
        blr.tracks(c.getInt(c.getColumnIndex(ProviderContract.Bard.FIELD_TRACKS)));
        blr.link(c.getString(c.getColumnIndex(ProviderContract.Bard.FIELD_LINK)));
        String genres = c.getString(c.getColumnIndex(ProviderContract.TABLE_GENRE_LIST));
        if(genres != null) blr.genres(Arrays.asList(genres.split(",")));
        else blr.genres(Collections.emptyList());

        return blr.build();
    }
}
