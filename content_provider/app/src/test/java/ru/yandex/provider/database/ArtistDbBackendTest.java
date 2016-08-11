package ru.yandex.provider.database;

import android.database.Cursor;
import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;

import org.junit.Test;

import ru.yandex.yamblz.artistmodel.Artist;

import static org.junit.Assert.*;

/**
 * Created by root on 8/11/16.
 */
public class ArtistDbBackendTest extends AndroidTestCase {

    private ArtistDbBackend db;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        RenamingDelegatingContext context = new RenamingDelegatingContext(getContext(), "test_");
        db = new ArtistDbBackend(context);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        db.close();
    }

    @Test
    public void testGetArtists() throws Exception {

        assertEquals(0, getCount(db.getArtists()));

        Artist fallOutBoy = new Artist();
        fallOutBoy.setName("FallOutBoy");

        db.insertArtist(fallOutBoy);

        assertEquals(fallOutBoy.getName(), Artists.cursorToArtist(db.getArtists()).getName());

    }

    @Test
    public void testInsertArtist() throws Exception {
        assertEquals(0, getCount(db.getArtists()));

        db.insertArtist(new Artist());

        assertEquals(1, getCount(db.getArtists()));
    }

    private int getCount(Cursor cursor) {
        return cursor.getCount();
    }

}