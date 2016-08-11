package ru.yandex.yamblz.euv.provider;

import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Arrays;

import ru.yandex.yamblz.euv.shared.contract.DbContract.ArtistTable;
import ru.yandex.yamblz.euv.shared.model.Artist;
import ru.yandex.yamblz.euv.shared.model.Cover;
import timber.log.Timber;

import static ru.yandex.yamblz.euv.shared.contract.ProviderContract.ARTISTS_PATH;
import static ru.yandex.yamblz.euv.shared.contract.ProviderContract.AUTHORITY;
import static ru.yandex.yamblz.euv.shared.contract.ProviderContract.SCHEME;

public class DebugActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Uri uri = Uri.withAppendedPath(Uri.parse(SCHEME + AUTHORITY), ARTISTS_PATH + "/41075");

        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        if (cursor == null) {
            throw new RuntimeException("Cursor mustn't be null here!");
        }

        try {
            cursor.moveToNext();
            Artist artist = createArtist(cursor);
            Timber.e(artist.toString());
        } finally {
            cursor.close();
        }
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
