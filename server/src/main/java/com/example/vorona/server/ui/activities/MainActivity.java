package com.example.vorona.server.ui.activities;

import android.app.LoaderManager;
import android.content.Loader;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.BinderThread;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.vorona.server.R;
import com.example.vorona.server.loaders.JsonLoader;
import com.example.vorona.server.model.Singer;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

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

    @Override
    public void onLoadFinished(Loader<List<Singer>> loader, List<Singer> singerList) {
//TODO put in content provider
        if (singerList != null)
            resulst.setText("Downloaded!");
        else
            resulst.setText("Failed!");
    }

    @Override
    public void onLoaderReset(Loader<List<Singer>> loader) {

    }
}
