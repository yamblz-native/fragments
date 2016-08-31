package ru.yandex.yamblz.homework.data.resolver;

import android.database.Cursor;
import android.support.annotation.NonNull;

import com.pushtorefresh.storio.contentresolver.operations.get.DefaultGetResolver;

import ru.yandex.yamblz.homework.data.entity.Artist;

import static ru.yandex.yamblz.homework.data.source.ArtistsContract.*;

/**
 * Created by platon on 06.08.2016.
 */
public class GetResolver extends DefaultGetResolver<Artist>
{
    @NonNull
    @Override
    public Artist mapFromCursor(@NonNull Cursor cursor)
    {
        return Artist.Builder()
                .setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ARTIST_ID)))
                .setName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ARTIST_NAME)))
                .setAlbums(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ALBUMS)))
                .setTracks(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_TRACKS)))
                .setDesc(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESC)))
                .setGenres(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_GENRES)))
                .setCoverSmall(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_COVER_SMALL)))
                .setCoverBig(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_COVER_BIG)))
                .build();
    }
}
