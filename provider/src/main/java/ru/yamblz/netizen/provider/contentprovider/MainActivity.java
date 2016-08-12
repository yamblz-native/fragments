package ru.yamblz.netizen.provider.contentprovider;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import timber.log.Timber;

/**
 * Created by Александр on 12.08.2016.
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Timber.d("Start");
        Cursor query = getContentResolver().query(Uri.parse(ProviderContract.BARD_URI), null, null, null, null);
        Cursor query2 = getContentResolver().query(Uri.parse(ProviderContract.BARD_URI_BY_ID + 2765181), null, null, null, null);
        Timber.d("Cursor " + (query != null));
    }
}
