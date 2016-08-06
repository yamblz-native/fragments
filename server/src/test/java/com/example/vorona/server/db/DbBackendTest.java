package com.example.vorona.server.db;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.vorona.server.model.Singer;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import static com.example.vorona.server.db.DbContract.ARTISTS;

/**
 * Created by vorona on 05.08.16.
 */

@RunWith(RobolectricTestRunner.class)
public class DbBackendTest {

    @Test
    public void testInsertSinger() {
        DBHelper helper = new DBHelper(RuntimeEnvironment.application);
        SQLiteDatabase db = helper.getWritableDatabase();

        DbBackend dbBackend = new DbBackend(RuntimeEnvironment.application);

        //Empty table, one singer added
        Singer singer = new Singer();
        singer.setName("AAAA");
        dbBackend.insertSinger(singer);
        Assert.assertEquals(1, getCount(db, ARTISTS));

        //One another
        Singer singer2 = new Singer();
        singer.setName("BBB");
        dbBackend.insertSinger(singer2);
        Assert.assertEquals(2, getCount(db, ARTISTS));

        //The same singer should also be added
        Singer singer3 = new Singer();
        singer.setName("AAAA");
        dbBackend.insertSinger(singer3);
        int p =  getCount(db, ARTISTS);
        Assert.assertEquals(3, p);
    }

    @Test
    public void testInsertUniqueSinger() {
        DBHelper helper = new DBHelper(RuntimeEnvironment.application);
        SQLiteDatabase db = helper.getWritableDatabase();

        DbBackend dbBackend = new DbBackend(RuntimeEnvironment.application);

        //Empty table, one singer added
        Singer singer = new Singer();
        singer.setName("AAAA");
        dbBackend.insertSingerUnique(singer);
        Assert.assertEquals(1, getCount(db, ARTISTS));

        //One another
        Singer singer2 = new Singer();
        singer.setName("BBB");
        dbBackend.insertSingerUnique(singer2);
        Assert.assertEquals(2, getCount(db, ARTISTS));

        //The same singer should not be added
        Singer singer3 = new Singer();
        singer.setName("AAAA");
        dbBackend.insertSingerUnique(singer3);
        int p =  getCount(db, ARTISTS);
        Assert.assertEquals(2, p);
    }

    @Test
    public void testInsertList() {
        DBHelper helper = new DBHelper(RuntimeEnvironment.application);
        SQLiteDatabase db = helper.getWritableDatabase();
        DbBackend dbBackend = new DbBackend(RuntimeEnvironment.application);
        List<Singer> singerList = new ArrayList<>();
        //Empty table, list of singers added
        Singer singer = new Singer();
        singer.setName("AAAA");
        Singer singer2 = new Singer();
        singer.setName("BBB");
        Singer singer3 = new Singer();
        singer.setName("AAAA");
        singerList.add(singer);
        singerList.add(singer2);
        singerList.add(singer3);
        dbBackend.insertList(singerList);
        int p = getCount(db, ARTISTS);
        Assert.assertEquals(3, p);
    }

    @Test
    public void testInsertUniqueList() {
        DBHelper helper = new DBHelper(RuntimeEnvironment.application);
        SQLiteDatabase db = helper.getWritableDatabase();
        DbBackend dbBackend = new DbBackend(RuntimeEnvironment.application);
        List<Singer> singerList = new ArrayList<>();
        //Empty table, list of singers added
        Singer singer = new Singer();
        singer.setName("AAAA");
        Singer singer2 = new Singer();
        singer.setName("BBB");
        Singer singer3 = new Singer();
        singer.setName("AAAA");
        singerList.add(singer);
        singerList.add(singer2);
        singerList.add(singer3);
        dbBackend.insertListUnique(singerList);
        int p = getCount(db, ARTISTS);
        Assert.assertEquals(2, p);
    }

    @Test
    public void testGetSinger() {
        DBHelper helper = new DBHelper(RuntimeEnvironment.application);
        SQLiteDatabase db = helper.getWritableDatabase();
        DbBackend dbBackend = new DbBackend(RuntimeEnvironment.application);
        List<Singer> singerList = new ArrayList<>();
        //Empty table, list of singers added
        Singer singer = new Singer();
        singer.setName("AAAA");
        singer.setId(1);
        Singer singer2 = new Singer();
        singer2.setName("BBB");
        singer2.setId(2);
        singerList.add(singer);
        singerList.add(singer2);
        dbBackend.insertList(singerList);
        Singer s = dbBackend.getSinger(1);
        Assert.assertEquals(s.getName(), singer.getName());
    }

    @Test
    public void testGetAllSingers() {
        DBHelper helper = new DBHelper(RuntimeEnvironment.application);
        SQLiteDatabase db = helper.getWritableDatabase();
        DbBackend dbBackend = new DbBackend(RuntimeEnvironment.application);
        List<Singer> singerList = new ArrayList<>();
        //Empty table, list of singers added
        Singer singer = new Singer();
        singer.setName("AAAA");
        singer.setId(1);
        Singer singer2 = new Singer();
        singer2.setName("BBB");
        singer2.setId(2);
        singerList.add(singer);
        singerList.add(singer2);
        dbBackend.insertList(singerList);
        List<Singer> s = dbBackend.getSingers();
        Assert.assertEquals(s.size(), singerList.size());
    }

    private int getCount(SQLiteDatabase db, String table) {
        return db.rawQuery("select * from " + table, null).getCount();
    }

}
