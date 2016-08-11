package ru.yandex.yamblz.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import ru.yandex.yamblz.Model.Artist;
import ru.yandex.yamblz.R;
import ru.yandex.yamblz.database.ArtistDataSource;

public class ArtistInfoActivity extends FragmentActivity{

    private static final String EXTRA_ARTIST = "artist";

    private Artist artist;
    private Context caller;

    private TextView largeText;
    private ImageView imageView;
    private HeadSetReciever headSetReciever = new HeadSetReciever();

    private ArtistDataSource dataSource =  new ArtistDataSource(this);

    @Override
    protected void onResume() {
        IntentFilter filter = new IntentFilter(Intent.ACTION_HEADSET_PLUG);
        registerReceiver(headSetReciever, filter);

        IntentFilter intentFilterClickMUSIC = new IntentFilter(HeadSetReciever.MUSICBUTTON);
        registerReceiver(headSetReciever, intentFilterClickMUSIC);

        IntentFilter intentFilterClickRADIO = new IntentFilter(HeadSetReciever.RADIOBUTTON);
        registerReceiver(headSetReciever, intentFilterClickRADIO);
        super.onResume();
    }

    @Override
    protected void onPause() {
        unregisterReceiver(headSetReciever);
        super.onPause();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_layout);
        if (findViewById(R.id.fragment_container) != null) {
            if (savedInstanceState != null) {
                return;
            }
            Log.i("fxf", "STARTING");

            ArtistsListActivity firstFragment = new ArtistsListActivity();
            Log.i("fxf", "FINISHED");
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, firstFragment).commit();
        }


        (findViewById(R.id.toolbarImageButtonMailId)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mailer = new Intent(Intent.ACTION_SEND);
                mailer.setType("text/plain");
                mailer.putExtra(Intent.EXTRA_EMAIL, new String[]{"danyaschenko@gmail.com"});
                mailer.putExtra(Intent.EXTRA_SUBJECT, "super-app");
                mailer.putExtra(Intent.EXTRA_TEXT, "best eu");
                startActivity(Intent.createChooser(mailer, "Send email..."));
            }
        });

        (findViewById(R.id.toolbarImageButtonInfoId)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InfoFragment infoFragment = new InfoFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, infoFragment).addToBackStack(null).commit();
            }
        });

    }

    public static Intent getIntent(Context caller, Artist artist) {
        Intent intent = new Intent(caller, ArtistInfoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(EXTRA_ARTIST, artist);
        intent.putExtras(bundle);
        return intent;
    }
}
