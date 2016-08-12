package ru.yamblz.netizen.provider.contentprovider;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import android.support.v4.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.yamblz.netizen.provider.contentprovider.model.BdBard;

/**
 * Created by Александр on 12.08.2016.
 */

public class DbBackend {
    private final SQLiteOpenHelper openHelper;

    public DbBackend(SQLiteOpenHelper openHelper) {
        this.openHelper = openHelper;
    }

    public boolean isEmpty(){
        SQLiteDatabase db = openHelper.getReadableDatabase();
        try{
            Cursor cursor = db.rawQuery(ProviderContract.IS_EMPTY_BARD, null);
            cursor.moveToFirst();
            int count = cursor.getInt(0);
            if(count > 0) return false;
            else return true;
        }finally {
            db.close();
        }
    }

    public Cursor getAllBard(String[] projection,
                             String selection,
                             String[] selectionArgs,
                             String sortOrder){
        SQLiteDatabase db = openHelper.getReadableDatabase();
        try {
            Cursor c = db.query(ProviderContract.VIEW_BARD_WITH_GENRES, projection, selection, selectionArgs, null, null, sortOrder);
            if (c != null) c.moveToFirst();
            return c;
        }finally {
            db.close();
        }
    }

    public void saveAllBard(List<BdBard> bards){
        final SQLiteDatabase db = openHelper.getWritableDatabase();
        db.beginTransaction();
        try{
            final Map<String, Pair<Long, List<Long>>> genresRef = new HashMap<>();
            long[] incrementIdForGenre = new long[]{0};
            for (BdBard bard : bards){
                performBardGenre(db, genresRef, incrementIdForGenre, bard, bard.genres());
                saveOneBard(db, bard);
            }
            for (Map.Entry<String, Pair<Long, List<Long>>> e : genresRef.entrySet()){
                long idGenre = e.getValue().first;
                for (Long idBard : e.getValue().second)
                    saveBardGenreReference(db, idBard, idGenre);
            }
            db.setTransactionSuccessful();
        }finally {
            db.endTransaction();
            db.close();
        }
    }

    private static void performBardGenre(@NonNull final SQLiteDatabase db,
                                         @NonNull final Map<String, Pair<Long, List<Long>>> genresReference,
                                         @NonNull final long[] incrementIdForGenre,
                                         @NonNull final BdBard bard,
                                         @NonNull final List<String> genres) {
        for (String gen : genres) {
            if (!genresReference.containsKey(gen)) {
                List<Long> aloneIdList = new ArrayList<>();
                aloneIdList.add(bard.id());
                genresReference.put(gen, Pair.create(incrementIdForGenre[0], aloneIdList));
                saveOneGenre(db, incrementIdForGenre[0], gen);
                incrementIdForGenre[0] += 1;
            } else {
                genresReference.get(gen).second.add(bard.id());
            }
        }
    }


    private static void saveOneBard(@NonNull final SQLiteDatabase db,
                                    @NonNull final BdBard bard){
        ContentValues contentValues = new ContentValues(8);
        contentValues.put(ProviderContract.Bard.FIELD_ID, bard.id());
        contentValues.put(ProviderContract.Bard.FIELD_NAME, bard.name());
        contentValues.put(ProviderContract.Bard.FIELD_ALBUMS, bard.albums());
        contentValues.put(ProviderContract.Bard.FIELD_BIG_PHOTO, bard.bigImage());
        contentValues.put(ProviderContract.Bard.FIELD_DESCRIPTION, bard.description());
        contentValues.put(ProviderContract.Bard.FIELD_LINK, bard.link());
        contentValues.put(ProviderContract.Bard.FIELD_SMALL_PHOTO, bard.smallImage());
        contentValues.put(ProviderContract.Bard.FIELD_TRACKS, bard.tracks());
        db.insertWithOnConflict(ProviderContract.TABLE_BARD, null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
    }

    private static void saveOneGenre(@NonNull final SQLiteDatabase db,
                                     long idGenre,
                                     @NonNull final String genre){
        ContentValues contentValues = new ContentValues(2);
        contentValues.put(ProviderContract.Genres.FIELD_ID, idGenre);
        contentValues.put(ProviderContract.Genres.FIELD_NAME, genre);
        db.insertWithOnConflict(ProviderContract.TABLE_GENRE, null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
    }

    private static void saveBardGenreReference(@NonNull final SQLiteDatabase db,
                                               long idBard, long idGenre){

        ContentValues values = new ContentValues(2);
        values.put(ProviderContract.BardGanre.FIELD_GENRE_ID, idGenre);
        values.put(ProviderContract.BardGanre.FIELD_BARD_ID, idBard);
        db.insert(ProviderContract.TABLE_BARD_GANRE, null, values);
    }
}
