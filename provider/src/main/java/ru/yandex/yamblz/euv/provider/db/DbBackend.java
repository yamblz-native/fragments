package ru.yandex.yamblz.euv.provider.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import static ru.yandex.yamblz.euv.shared.contract.DbContract.ArtistTable.PROJECTION;
import static ru.yandex.yamblz.euv.shared.contract.DbContract.ArtistTable.TABLE_NAME;
import static ru.yandex.yamblz.euv.shared.contract.DbContract.ArtistTable._ID;

public class DbBackend {
    private final DbOpenHelper dbHelper;

    public DbBackend(Context context) {
        dbHelper = new DbOpenHelper(context);
    }

    public Cursor getArtists() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        return db.query(TABLE_NAME, PROJECTION, null, null, null, null, null);
    }

    public Cursor getArtist(String id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        return db.query(TABLE_NAME, PROJECTION, _ID + "=?", new String[]{id}, null, null, null);
    }
}
