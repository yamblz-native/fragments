package ru.yandex.yamblz.ui.activities;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.widget.Toast;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Named;

import ru.yandex.yamblz.App;
import ru.yandex.yamblz.R;
import ru.yandex.yamblz.developer_settings.DeveloperSettingsModule;
import ru.yandex.yamblz.model.Artist;
import ru.yandex.yamblz.model.ArtistFetcher;
import ru.yandex.yamblz.model.ArtistLab;
import ru.yandex.yamblz.model.ArtistProvider;
import ru.yandex.yamblz.ui.fragments.ArtistDetailDialogFragment;
import ru.yandex.yamblz.ui.fragments.ArtistListFragment;
import ru.yandex.yamblz.ui.fragments.ArtistPhotoFragment;
import ru.yandex.yamblz.ui.fragments.ArtistViewPagerFragment;
import ru.yandex.yamblz.ui.other.ViewModifier;

public class MainActivity extends BaseActivity implements ArtistPhotoFragment.Callbacks, ArtistListFragment.Callbacks {

    @Inject
    @Named(DeveloperSettingsModule.MAIN_ACTIVITY_VIEW_MODIFIER)
    ViewModifier viewModifier;
    private ArtistProvider mArtistProvider;
    private ArtistViewPagerFragment mArtistViewPagerFragment;

    @SuppressLint("InflateParams") // It's okay in our case.
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.get(this).applicationComponent().inject(this);

        setContentView(viewModifier.modify(getLayoutInflater().inflate(R.layout.activity_master_detail, null)));

        mArtistProvider = ArtistLab.get(this);

        // TODO: Убрать сразу после создания нормальной БД
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                ArtistLab artistLab = ArtistLab.get(getApplicationContext());
                if (artistLab.getArtistCount() == 0) {
                    ArtistFetcher artistFetcher = new ArtistFetcher();
                    try {
                        artistLab.setArtists(artistFetcher.getArtistsFromJson(artistFetcher.getJson()));
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(MainActivity.this, "Ошибка при загрузке!", Toast.LENGTH_LONG).show();
                    }
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                Toast.makeText(MainActivity.this, "Готово: " + mArtistProvider.getArtistCount() + " исполниетелей", Toast.LENGTH_SHORT).show();

                mArtistViewPagerFragment = ArtistViewPagerFragment.newInstance(0);
                if (isPhone()) {
                    if (savedInstanceState == null) {
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.main_frame_layout, mArtistViewPagerFragment)
                                .commit();
                    }
                } else {
                    if (savedInstanceState == null) {
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.main_frame_layout, new ArtistListFragment())
                                .commit();

                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.photo_frame_layout, mArtistViewPagerFragment)
                                .commit();
                    }
                }
            }
        }.execute();


    }

    @Override
    public void onClickMoreInformation(Artist artist) {
        FragmentManager fm = getSupportFragmentManager();
        if (isPhone()) {
            // TODO: Во весь экран
            ArtistDetailDialogFragment.newInstance(artist).show(fm, "detail_fragment");
        } else {
            ArtistDetailDialogFragment.newInstance(artist).show(fm, "detail_fragment");
        }
    }

    @Override
    public void onArtistInListSelected(Artist artist) {
        int position = mArtistProvider.getPositionForArtist(artist);
        mArtistViewPagerFragment.setCurrentItem(position);
    }

    private boolean isPhone() {
        return findViewById(R.id.photo_frame_layout) == null;
    }
}