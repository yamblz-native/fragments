package ru.yamblz.netizen.provider.contentprovider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import ru.yamblz.netizen.provider.R;
import ru.yamblz.netizen.provider.contentprovider.model.AdapterFactory;
import ru.yamblz.netizen.provider.contentprovider.model.BdBard;
import ru.yamblz.netizen.provider.contentprovider.model.JsonBard;

/**
 * Created by Александр on 11.08.2016.
 */

public class BardContentProvider extends ContentProvider {
    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    private static final int ARTISTS_CODE = 1;

    private DbBackend dbBackend;

    static {
        uriMatcher.addURI(ProviderContract.AUTHORITY, ProviderContract.TABLE_BARD, ARTISTS_CODE);
    }


    @Override
    public boolean onCreate() {
        dbBackend = new DbBackend(new BardDbOpenHalper(getContext()));
        return true;
    }

    private void inflateDB(){
        Gson gson = new GsonBuilder()
                .registerTypeAdapterFactory(AdapterFactory.create())
                .create();
        Type type = new TypeToken<List<JsonBard>>(){}.getType();
        List<JsonBard> artists = gson.fromJson(new BufferedReader(new InputStreamReader(
                getContext().getResources().openRawResource(R.raw.artists))), type);
        dbBackend.saveAllBard(map(artists));
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri,
                        String[] projection,
                        String selection, String[] selectionArgs,
                        String sortOrder) {

        if (dbBackend.isEmpty())
            inflateDB();

        switch (uriMatcher.match(uri)) {
            case ARTISTS_CODE:
                return dbBackend.getAllBard(projection, selection, selectionArgs, null);
            default:
                throw new IllegalArgumentException();
        }
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int update(@NonNull Uri uri,
                      ContentValues values,
                      String selection, String[] selectionArgs) {
        throw new UnsupportedOperationException();
    }

    public List<BdBard> map(List<JsonBard> data){
        List<BdBard> result = new ArrayList<>(data.size());
        for (JsonBard bard : data){
            BdBard.Builder builder = BdBard.toBuilder();
            builder.id(bard.id())
                    .albums(bard.albums())
                    .tracks(bard.tracks())
                    .bigImage((bard.images() != null)? bard.images().bigImage() : "")
                    .smallImage((bard.images() != null)? bard.images().smallImage() : "")
                    .link(bard.link())
                    .name(bard.name())
                    .description(bard.description());
            builder.genres(bard.genres());
            result.add(builder.build());
        }
        return result;
    }
}
