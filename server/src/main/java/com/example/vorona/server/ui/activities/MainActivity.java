package com.example.vorona.server.ui.activities;

import android.app.LoaderManager;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Loader;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.example.vorona.server.R;
import com.example.vorona.server.db.DbProvider;
import com.example.vorona.server.loaders.JsonLoader;
import com.example.vorona.server.model.Singer;
import com.example.vorona.server.provider.MyContentProvider;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Singer>> {

    @BindView(R.id.txtResult)
    TextView resulst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public Loader<List<Singer>> onCreateLoader(int i, Bundle bundle) {
        return new JsonLoader(this);
    }

    static final String PROVIDER_NAME = "ru.yandex.yamblz.database";
    static final String URL = "content://" + PROVIDER_NAME + "/artists";
    static final Uri CONTENT_URI = Uri.parse(URL);

    @Override
    public void onLoadFinished(Loader<List<Singer>> loader, List<Singer> singerList) {
        if (singerList != null) {
            resulst.setText("Downloaded!");
            getContentResolver().insert(CONTENT_URI, createCV(singerList.get(0)));
        }
        else
            resulst.setText("Failed!");
    }

    public ContentValues createCV(Singer singer) {
        ContentValues cv = new ContentValues();
        cv.put("id", singer.getId());
        cv.put("name", singer.getName());
        cv.put("bio", singer.getBio());
        cv.put("albums", singer.getAlbums());
        cv.put("tracks", singer.getTracks());
        cv.put("cover", singer.getCover_big());
        cv.put("genres", singer.getGenres());
        cv.put("cover_small", singer.getCover_small());
        return cv;
    }

    @Override
    public void onLoaderReset(Loader<List<Singer>> loader) {

    }
}
