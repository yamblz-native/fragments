package ru.yandex.yamblz.ui.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;

import javax.inject.Inject;
import javax.inject.Named;

import ru.yandex.yamblz.App;
import ru.yandex.yamblz.R;
import ru.yandex.yamblz.developer_settings.DeveloperSettingsModule;
import ru.yandex.yamblz.model.Artist;
import ru.yandex.yamblz.ui.fragments.ContentFragment;
import ru.yandex.yamblz.ui.fragments.DetailsFragment;
import ru.yandex.yamblz.ui.fragments.PageFragment;
import ru.yandex.yamblz.ui.other.ViewModifier;

public class MainActivity extends BaseActivity implements PageFragment.PageFragmentListener {

    private static final String DETAILS_SHOW = "details_show";
    private static final String TAG_CONTENT_FRAGMENT = "content";
    @Inject
    @Named(DeveloperSettingsModule.MAIN_ACTIVITY_VIEW_MODIFIER)
    ViewModifier viewModifier;

    private FragmentManager fragmentManager;

    @SuppressLint("InflateParams")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.get(this).applicationComponent().inject(this);
        fragmentManager = getSupportFragmentManager();
        setContentView(viewModifier.modify(getLayoutInflater().inflate(R.layout.activity_main, null)));

        if (savedInstanceState == null) {
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.main_frame_layout, new ContentFragment(), TAG_CONTENT_FRAGMENT)
                    .addToBackStack(null)
                    .commit();
        }
    }

    @Override
    public void onPageFragmentClicked(Artist artist) {
        fragmentManager
                .beginTransaction()
                .replace(R.id.main_frame_layout, DetailsFragment.newInstance(artist))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onPageDialogFragmentClicked(Artist artist, boolean visible) {
        if (visible) {
            DetailsFragment.newInstance(artist).show(fragmentManager, DETAILS_SHOW);
        } else {
            fragmentManager.findFragmentByTag(DETAILS_SHOW).onDestroy();
        }
    }
}
