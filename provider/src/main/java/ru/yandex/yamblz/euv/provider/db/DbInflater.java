package ru.yandex.yamblz.euv.provider.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

import ru.yandex.yamblz.euv.provider.R;
import ru.yandex.yamblz.euv.shared.contract.DbContract.ArtistTable;
import ru.yandex.yamblz.euv.shared.model.Artist;
import timber.log.Timber;

public class DbInflater {
    private final Context context;

    public DbInflater(Context context) {
        this.context = context;
    }

    public void inflate(SQLiteDatabase db) {
        ArrayList<Artist> artists = loadArtistList();
        if (artists == null || artists.isEmpty()) {
            Timber.e("There is no artists to fill the database");
            return;
        }

        db.beginTransaction();
        try {
            for (Artist artist : artists) {
                ContentValues val = new ContentValues();
                val.put(ArtistTable._ID, artist.getId());
                val.put(ArtistTable.NAME, artist.getName());
                val.put(ArtistTable.GENRES, artist.getGenresStr());
                val.put(ArtistTable.TRACKS, artist.getTracks());
                val.put(ArtistTable.ALBUMS, artist.getAlbums());
                val.put(ArtistTable.LINK, artist.getLink());
                val.put(ArtistTable.DESCRIPTION, artist.getDescription());
                val.put(ArtistTable.COVER_SMALL, artist.getCover().getSmall());
                val.put(ArtistTable.COVER_BIG, artist.getCover().getBig());

                db.insert(ArtistTable.TABLE_NAME, null, val);
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }


    private ArrayList<Artist> loadArtistList() {
        String artistsJsonString = null;
        try {
            InputStream is = context.getResources().openRawResource(R.raw.artists);

            // Read the whole file
            Scanner scanner = new Scanner(is).useDelimiter("\\A");
            if (scanner.hasNext()) {
                artistsJsonString = scanner.next();
            }

            is.close();
        } catch (IOException e) {
            Timber.e(e, "Failed to read data from internal storage");
            return null;
        }

        return (ArrayList<Artist>) JSON.parseArray(artistsJsonString, Artist.class);
    }
}
