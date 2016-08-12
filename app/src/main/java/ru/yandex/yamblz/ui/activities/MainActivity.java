package ru.yandex.yamblz.ui.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewCompat;
import android.transition.AutoTransition;
import android.transition.ChangeBounds;
import android.transition.ChangeImageTransform;
import android.transition.ChangeTransform;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.TransitionSet;
import android.view.View;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import ru.yandex.yamblz.App;
import ru.yandex.yamblz.R;
import ru.yandex.yamblz.artistmodel.Artist;
import ru.yandex.yamblz.developer_settings.DeveloperSettingsModule;
import ru.yandex.yamblz.ui.fragments.ArtistInfoFragment;
import ru.yandex.yamblz.ui.fragments.ArtistTabFragment;
import ru.yandex.yamblz.ui.fragments.ArtistsFragment;
import ru.yandex.yamblz.ui.fragments.ArtistsLargeFragment;
import ru.yandex.yamblz.ui.other.ViewModifier;

public class MainActivity extends BaseActivity implements ArtistTabFragment.OnDetailClickListener {

    private static final String BACK_STACK_NAME = "artist-detail";

    @Inject @Named(DeveloperSettingsModule.MAIN_ACTIVITY_VIEW_MODIFIER)
    ViewModifier viewModifier;

    @SuppressLint("InflateParams") // It's okay in our case.
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.get(this).applicationComponent().inject(this);

        setContentView(viewModifier.modify(getLayoutInflater().inflate(R.layout.activity_main, null)));

        if (savedInstanceState == null) {

            Fragment fragment;

            if(getResources().getBoolean(R.bool.isTablet)) {
                fragment = new ArtistsLargeFragment();
            } else {
                fragment = new ArtistsFragment();
            }

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_frame_layout, fragment)
                    .commit();
        }
    }

    @Override
    public void onDetailClick(Artist artist, Map<String, View> transitionViews) {

        Bundle args = new Bundle();
        args.putSerializable(Artist.class.getCanonicalName(), artist);

        Fragment fragment = new ArtistInfoFragment();
        fragment.setArguments(args);

        fragment.setSharedElementEnterTransition(new TransitionSet()
                .setOrdering(TransitionSet.ORDERING_TOGETHER)
                .addTransition(new ChangeBounds())
                .addTransition(new ChangeImageTransform())
                .addTransition(new ChangeTransform()));

        TransitionSet transition = new TransitionSet()
                .addTransition(new Fade())
                .addTransition(new Slide());

        fragment.setEnterTransition(transition);
        fragment.setExitTransition(transition);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        for (Map.Entry<String, View> entry : transitionViews.entrySet()) {
            transaction.addSharedElement(entry.getValue(), entry.getKey());
        }

        transaction
                .addToBackStack(BACK_STACK_NAME)
                .add(R.id.main_frame_layout, fragment, fragment.getClass().getCanonicalName());

        transaction.commit();
    }
}
