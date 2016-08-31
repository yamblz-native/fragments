package ru.yandex.yamblz.homework.data.resolver;

import android.support.annotation.NonNull;

import com.pushtorefresh.storio.contentresolver.operations.delete.DefaultDeleteResolver;
import com.pushtorefresh.storio.contentresolver.queries.DeleteQuery;
import static ru.yandex.yamblz.homework.data.source.ArtistsContract.*;
import ru.yandex.yamblz.homework.data.entity.Artist;

/**
 * Created by platon on 06.08.2016.
 */
public class DeleteResolver extends DefaultDeleteResolver<Artist>
{
    @NonNull
    @Override
    protected DeleteQuery mapToDeleteQuery(@NonNull Artist artist)
    {
        return DeleteQuery.builder()
                .uri(ARTISTS_URI)
                .where(COLUMN_ARTIST_ID + " = ?")
                .whereArgs(artist.getId())
                .build();
    }
}
