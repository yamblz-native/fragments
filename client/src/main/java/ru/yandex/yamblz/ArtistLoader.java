package ru.yandex.yamblz;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ru.yandex.yamblz.euv.shared.contract.DbContract.ArtistTable;
import ru.yandex.yamblz.euv.shared.model.Artist;
import ru.yandex.yamblz.euv.shared.model.Cover;
import timber.log.Timber;

import static ru.yandex.yamblz.euv.shared.contract.ProviderContract.ARTISTS_PATH;
import static ru.yandex.yamblz.euv.shared.contract.ProviderContract.AUTHORITY;
import static ru.yandex.yamblz.euv.shared.contract.ProviderContract.SCHEME;

/**
 * Used to retrieve data from the Content Provider.
 * {@link AsyncTask} covers our needs and is chosen because of its simplicity.
 * Loader does return data in form of a {@link List}, not a {@link Cursor}
 * since such approach provides much more convenient API and *DOES NOT* have
 * real negative impact on performance (avoid premature optimization).
 */
public class ArtistLoader extends AsyncTask<Void, Void, List<Artist>> {
    private final ArtistLoaderListener callback;
    private final ContentResolver resolver;

    public interface ArtistLoaderListener {
        void onArtistListLoaded(List<Artist> artists);
    }


    public ArtistLoader(ArtistLoaderListener callback, Context context) {
        this.callback = callback;
        this.resolver = context.getContentResolver();
    }


    @Override
    protected List<Artist> doInBackground(Void... params) {
        Uri uri = Uri.withAppendedPath(Uri.parse(SCHEME + AUTHORITY), ARTISTS_PATH);

        Cursor cursor = resolver.query(uri, null, null, null, null);
        if (cursor == null) {
            Timber.e("Failed to retrieve data from Content Provider");
            return null;
        }

        List<Artist> artists = new ArrayList<>();
        try {
            while (cursor.moveToNext()) {
                artists.add(createArtist(cursor));
            }
        } finally {
            cursor.close();
        }

        return artists;
    }


    @Override
    protected void onPostExecute(List<Artist> artists) {
        callback.onArtistListLoaded(artists);
    }


    private Artist createArtist(Cursor cursor) {
        Artist artist = new Artist();
        artist.setId(getInt(cursor, ArtistTable._ID));
        artist.setName(getString(cursor, ArtistTable.NAME));
        artist.setGenres(Arrays.asList(getString(cursor, ArtistTable.GENRES).split(", ")));
        artist.setTracks(getInt(cursor, ArtistTable.TRACKS));
        artist.setAlbums(getInt(cursor, ArtistTable.ALBUMS));
        artist.setLink(getString(cursor, ArtistTable.LINK));
        artist.setDescription(getString(cursor, ArtistTable.DESCRIPTION));
        artist.setCover(new Cover(getString(cursor, ArtistTable.COVER_SMALL), getString(cursor, ArtistTable.COVER_BIG)));
        return artist;
    }


    private int getInt(Cursor cursor, String column) {
        return cursor.getInt(cursor.getColumnIndexOrThrow(column));
    }


    private String getString(Cursor cursor, String column) {
        return cursor.getString(cursor.getColumnIndexOrThrow(column));
    }
}
