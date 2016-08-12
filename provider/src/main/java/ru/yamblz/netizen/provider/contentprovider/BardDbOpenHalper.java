package ru.yamblz.netizen.provider.contentprovider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Александр on 12.08.2016.
 */

public class BardDbOpenHalper extends SQLiteOpenHelper {


    public BardDbOpenHalper(Context context) {
        super(context, "bard_db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ProviderContract.CREATE_TABLE_BARD);
        db.execSQL(ProviderContract.CREATE_TABLE_GENRE);
        db.execSQL(ProviderContract.CREATE_TABLE_BARD_GENRE);
        db.execSQL(ProviderContract.CREATE_VIEW);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(ProviderContract.DROP_TABLE_BARD);
        db.execSQL(ProviderContract.DROP_TABLE_GENRE);
        db.execSQL(ProviderContract.DROP_TABLE_BARD_GENRE);
        db.execSQL(ProviderContract.DROP_VIEW_BARD_WITH_GENRE);
        onCreate(db);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        setWriteAheadLoggingEnabled(true);
    }
}
