package ru.yandex.yamblz.euv.provider.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import ru.yandex.yamblz.euv.shared.contract.DbContract.ArtistTable;
import ru.yandex.yamblz.euv.shared.contract.DbContract.ArtistsGenresTable;
import ru.yandex.yamblz.euv.shared.contract.DbContract.GenreTable;

public class DbOpenHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "artists.sqlite";
    private static final int DB_VERSION = 1;

    private final DbInflater dbInflater;

    public DbOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        dbInflater = new DbInflater(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ArtistTable.SQL_CREATE_TABLE);
        db.execSQL(GenreTable.SQL_CREATE_TABLE);
        db.execSQL(ArtistsGenresTable.SQL_CREATE_TABLE);

        // Insert initial data into database
        dbInflater.inflate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(ArtistTable.SQL_DROP_TABLE);
        db.execSQL(GenreTable.SQL_DROP_TABLE);
        db.execSQL(ArtistsGenresTable.SQL_DROP_TABLE);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
