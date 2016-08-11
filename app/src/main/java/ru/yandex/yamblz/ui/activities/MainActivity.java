package ru.yandex.yamblz.ui.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import ru.yandex.yamblz.App;
import ru.yandex.yamblz.R;
import ru.yandex.yamblz.developer_settings.DeveloperSettingsModule;
import ru.yandex.yamblz.model.Artist;
import ru.yandex.yamblz.ui.background.MyLoader;
import ru.yandex.yamblz.ui.fragments.ArtistsListFragment;
import ru.yandex.yamblz.ui.fragments.CoverFragment;
import ru.yandex.yamblz.ui.fragments.DetailedFragment;
import ru.yandex.yamblz.ui.fragments.PagerFragment;
import ru.yandex.yamblz.ui.fragments.StubFragment;
import ru.yandex.yamblz.ui.other.ViewModifier;
import timber.log.Timber;

public class MainActivity extends BaseActivity implements LoaderManager.LoaderCallbacks<List<Artist>> {
    public static final String DEBUG_TAG = MainActivity.class.getName();

    @Inject
    @Named(DeveloperSettingsModule.MAIN_ACTIVITY_VIEW_MODIFIER)
    ViewModifier viewModifier;

    @SuppressLint("InflateParams") // It's okay in our case.
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.get(this).applicationComponent().inject(this);

        setContentView(viewModifier.modify(getLayoutInflater().inflate(R.layout.activity_main, null)));

        if (savedInstanceState == null) {
            if (isTablet()) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.list_container, new ArtistsListFragment(), ArtistsListFragment.FRAGMENT_TAG)
                        .commit();
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.detail_container, new StubFragment(), CoverFragment.FRAGMENT_TAG)
                        .commit();
            } else {
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.detail_container, new PagerFragment(), PagerFragment.FRAGMENT_TAG)
                        .commit();
            }
        }

        getSupportLoaderManager().initLoader(1, null, this);
    }

    public void onArtistSelected(Artist artist) {
        Timber.d("in main's onArtist selected");
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.detail_container, CoverFragment.newInstance(artist), CoverFragment.FRAGMENT_TAG)
                .commit();
    }

    @Override
    public Loader<List<Artist>> onCreateLoader(int id, Bundle args) {
        Log.d(DEBUG_TAG, "onCreateLoader");
        return new MyLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<List<Artist>> loader, List<Artist> data) {
        if (isTablet()) {
            ((ArtistsListFragment) getSupportFragmentManager().findFragmentByTag(ArtistsListFragment.FRAGMENT_TAG))
                    .onNewDatasetAvailable(data);
        } else {
            ((PagerFragment) getSupportFragmentManager().findFragmentByTag(PagerFragment.FRAGMENT_TAG))
                    .onNewArtistsAvailable(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Artist>> loader) {

    }

    public void showDetailedFragment(Artist artist) {
        DetailedFragment fragment = (DetailedFragment) DetailedFragment.newInstance(artist);

        if (isTablet()) {
            fragment.show(getSupportFragmentManager(), DetailedFragment.TAG);
        } else {
            getSupportFragmentManager()
                    .beginTransaction()
                    //because replacing is too slow
                    .add(R.id.detail_container, fragment, DetailedFragment.TAG)
                    .addToBackStack(DetailedFragment.TAG)
                    .commit();
        }
    }

    public boolean isTablet() {
        return getResources().getBoolean(R.bool.isTablet);
    }
}
