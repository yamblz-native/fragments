package ru.yandex.yamblz.ui.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.MenuItem;

import javax.inject.Inject;
import javax.inject.Named;

import ru.yandex.yamblz.App;
import ru.yandex.yamblz.R;
import ru.yandex.yamblz.data.models.Artist;
import ru.yandex.yamblz.developer_settings.DeveloperSettingsModule;
import ru.yandex.yamblz.ui.fragments.ArtistDetailFragment;
import ru.yandex.yamblz.ui.fragments.ArtistDetailFragmentBuilder;
import ru.yandex.yamblz.ui.fragments.ArtistPageFragment;
import ru.yandex.yamblz.ui.fragments.ArtistsPagerFragment;
import ru.yandex.yamblz.ui.other.ViewModifier;

public class MainActivity extends BaseActivity implements ArtistPageFragment.OnMoreButtonClickListener {
    private static final String ARTIST_DETAIL_FRAGMENT_TAG = "ARTIST_DETAIL_FRAGMENT_TAG";
    private FragmentManager supportFragmentManager;
    private boolean hasTwoPanes;

    @Inject
    @Named(DeveloperSettingsModule.MAIN_ACTIVITY_VIEW_MODIFIER)
    ViewModifier viewModifier;

    @SuppressLint("InflateParams") // It's okay in our case.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportFragmentManager = getSupportFragmentManager();
        App.get(this).applicationComponent().inject(this);

        setContentView(viewModifier.modify(getLayoutInflater().inflate(R.layout.activity_main, null)));
        hasTwoPanes = getResources().getBoolean(R.bool.has_two_panes);

        if (savedInstanceState == null) {
            if (!hasTwoPanes) {
                supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.main_frame_layout, new ArtistsPagerFragment())
                        .commit();
            }
        }
    }

    @Override
    public void onMoreButtonClick(Artist artist) {
        ArtistDetailFragment artistDetailFragment = new ArtistDetailFragmentBuilder(artist).build();

        if (hasTwoPanes) {
            artistDetailFragment.show(supportFragmentManager, ARTIST_DETAIL_FRAGMENT_TAG);
        } else {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.main_frame_layout, artistDetailFragment)
                    .addToBackStack(null)
                    .commit();
        }
    }

    /**
     * On options menu item selected
     *
     * @param item of menu
     * @return result of options item selected
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
