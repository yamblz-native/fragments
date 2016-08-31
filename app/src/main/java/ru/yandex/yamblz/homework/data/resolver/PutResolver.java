package ru.yandex.yamblz.homework.data.resolver;

import android.content.ContentValues;
import android.support.annotation.NonNull;

import com.pushtorefresh.storio.contentresolver.operations.put.DefaultPutResolver;
import com.pushtorefresh.storio.contentresolver.queries.InsertQuery;
import com.pushtorefresh.storio.contentresolver.queries.UpdateQuery;

import ru.yandex.yamblz.homework.data.entity.Artist;

import static ru.yandex.yamblz.homework.data.source.ArtistsContract.*;

/**
 * Created by platon on 06.08.2016.
 */
public class PutResolver extends DefaultPutResolver<Artist>
{
    @NonNull
    @Override
    protected InsertQuery mapToInsertQuery(@NonNull Artist artist)
    {
        return InsertQuery.builder()
                .uri(ARTISTS_URI)
                .build();
    }

    @NonNull
    @Override
    protected UpdateQuery mapToUpdateQuery(@NonNull Artist artist)
    {
        return UpdateQuery.builder()
                .uri(ARTISTS_URI)
                .where(COLUMN_ARTIST_ID + " = ?")
                .whereArgs(artist.getId())
                .build();
    }

    @NonNull
    @Override
    protected ContentValues mapToContentValues(@NonNull Artist artist)
    {
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_ARTIST_ID, artist.getId());
        contentValues.put(COLUMN_ARTIST_NAME, artist.getName());
        contentValues.put(COLUMN_TRACKS, artist.getTracks());
        contentValues.put(COLUMN_ALBUMS, artist.getAlbums());
        contentValues.put(COLUMN_GENRES, artist.getGenres());
        contentValues.put(COLUMN_DESC, artist.getDesc());
        contentValues.put(COLUMN_COVER_SMALL, artist.getCoverSmall());
        contentValues.put(COLUMN_COVER_BIG, artist.getCoverBig());

        return contentValues;
    }
}
