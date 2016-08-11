package ru.yandex.yamblz.ui.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;

import javax.inject.Inject;
import javax.inject.Named;

import ru.yandex.yamblz.App;
import ru.yandex.yamblz.R;
import ru.yandex.yamblz.developer_settings.DeveloperSettingsModule;
import ru.yandex.yamblz.euv.shared.model.Artist;
import ru.yandex.yamblz.ui.adapters.RecyclerAdapter.ArtistSelectedListener;
import ru.yandex.yamblz.ui.fragments.ArtistInfoFullBuilder;
import ru.yandex.yamblz.ui.fragments.ArtistInfoShortBuilder;
import ru.yandex.yamblz.ui.fragments.ArtistListRecyclerFragment;
import ru.yandex.yamblz.ui.fragments.ArtistListTabFragment;
import ru.yandex.yamblz.ui.fragments.ArtistInfoShort.MoreButtonClickListener;
import ru.yandex.yamblz.ui.other.ViewModifier;

import static android.R.anim.fade_in;
import static android.R.anim.fade_out;
import static android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN;

public class MainActivity extends BaseActivity implements MoreButtonClickListener, ArtistSelectedListener {
    private boolean singlePaneLayout;

    @Inject
    @Named(DeveloperSettingsModule.MAIN_ACTIVITY_VIEW_MODIFIER)
    ViewModifier viewModifier;

    @SuppressLint("InflateParams") // It's okay in our case.
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.get(this).applicationComponent().inject(this);

        getWindow().setFlags(FLAG_FULLSCREEN, FLAG_FULLSCREEN);

        setContentView(viewModifier.modify(getLayoutInflater().inflate(R.layout.activity_main, null)));

        singlePaneLayout = (findViewById(R.id.single_pane_container) != null);

        if (savedInstanceState == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            if (singlePaneLayout) {
                transaction.add(R.id.single_pane_container, new ArtistListTabFragment());
            } else {
                transaction.add(R.id.two_pane_master_container, new ArtistListRecyclerFragment());
            }
            transaction.commit();
        }
    }


    @Override
    public void onMoreButtonClicked(Artist artist) {
        if (isDestroyed()) return;

        if (singlePaneLayout) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(fade_in, fade_out, fade_in, fade_out)
                    .add(R.id.single_pane_container, new ArtistInfoFullBuilder(artist).build())
                    .addToBackStack(null)
                    .commit();
        } else {
            // TODO Show Dialog Fragment
        }
    }


    @Override
    public void onArtistSelected(Artist artist) {
        if (isDestroyed()) return;

        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(fade_in, fade_out, fade_in, fade_out)
                .replace(R.id.two_pane_detail_container, new ArtistInfoShortBuilder(artist).build())
                .commit();
    }
}
