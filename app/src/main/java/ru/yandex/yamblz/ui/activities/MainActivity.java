package ru.yandex.yamblz.ui.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import ru.yandex.yamblz.App;
import ru.yandex.yamblz.R;
import ru.yandex.yamblz.data.Artist;
import ru.yandex.yamblz.developer_settings.DeveloperSettingsModule;
import ru.yandex.yamblz.retrofit.ApiServices;
import ru.yandex.yamblz.ui.fragments.ArtistContentFragment;
import ru.yandex.yamblz.ui.fragments.ArtistDetailsFragment;
import ru.yandex.yamblz.ui.fragments.ArtistDialogFragment;
import ru.yandex.yamblz.ui.fragments.ArtistsMasterFragment;
import ru.yandex.yamblz.ui.fragments.RetainFragment;
import ru.yandex.yamblz.ui.fragments.ViewPagerFragment;
import ru.yandex.yamblz.ui.other.ArtistProviderInterface;
import ru.yandex.yamblz.ui.other.ArtistSelectedInterface;
import ru.yandex.yamblz.ui.other.OnArtistListItemClickListener;
import ru.yandex.yamblz.ui.other.OnArtistMoreClickListener;
import ru.yandex.yamblz.ui.other.UpdateArtistsListener;
import ru.yandex.yamblz.ui.other.ViewModifier;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends BaseActivity implements OnArtistMoreClickListener, OnArtistListItemClickListener {

    private static final String SELECTED_ARTIST_ID = "selected_id";

    @Inject @Named(DeveloperSettingsModule.MAIN_ACTIVITY_VIEW_MODIFIER)
    ViewModifier viewModifier;

    private int selectedArtistId = -1;

    @SuppressLint("InflateParams") // It's okay in our case.
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.get(this).applicationComponent().inject(this);

        setContentView(viewModifier.modify(getLayoutInflater().inflate(R.layout.activity_main, null)));

        RetainFragment retain = (RetainFragment) getSupportFragmentManager().findFragmentByTag(RetainFragment.TAG);
        if (savedInstanceState == null) {
            retain = new RetainFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(retain, RetainFragment.TAG)
                    .commit();
            downloadArtists();
        } else {
            selectedArtistId = savedInstanceState.getInt(SELECTED_ARTIST_ID);
        }

        if (isOrientationPortrait()) {
            ViewPagerFragment pagerFragment = ViewPagerFragment.newInstance(selectedArtistId, retain);
            pagerFragment.setTargetFragment(retain, 1);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_frame_layout, pagerFragment, ViewPagerFragment.TAG)
                    .commit();
        } else {
            ArtistsMasterFragment masterFragment = ArtistsMasterFragment.newInstance(selectedArtistId, retain);
            FragmentTransaction transaction = getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_frame_layout, masterFragment, ArtistsMasterFragment.TAG);
            if (retain.getArtists().size() > 0) {
                String link = retain.getArtistById(selectedArtistId).getCover().getBig();
                ArtistContentFragment detailsFragment = ArtistContentFragment.newInstance(selectedArtistId, link);
                transaction.add(R.id.detail_frame_layout, detailsFragment);
            }
            transaction.commit();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SELECTED_ARTIST_ID, selectedArtistId);
        if (isOrientationPortrait()) {
            Fragment pagerFragment = getSupportFragmentManager().findFragmentByTag(ViewPagerFragment.TAG);
            ArtistSelectedInterface selectedInterface = (ArtistSelectedInterface) pagerFragment;
            int id = selectedInterface.getSelectedArtistId();
            if (id > 0)
                outState.putInt(SELECTED_ARTIST_ID, selectedInterface.getSelectedArtistId());
        }
    }

    @Override
    public void onArtistClick(int id) {
        RetainFragment retain = (RetainFragment) getSupportFragmentManager().findFragmentByTag(RetainFragment.TAG);
        String link = retain.getArtistById(id).getCover().getBig();
        ArtistContentFragment detailsFragment = ArtistContentFragment.newInstance(id, link);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.detail_frame_layout, detailsFragment)
                .commit();
        selectedArtistId = id;
    }

    public void downloadArtists() {
        ApiServices apiServices = new ApiServices();
        apiServices.getArtists()
                .retryWhen(this::showReloadSnackbar)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(artists -> {
                    Fragment retainFragment = getSupportFragmentManager().findFragmentByTag(RetainFragment.TAG);
                    if (retainFragment != null && retainFragment instanceof ArtistProviderInterface) {
                        ArtistProviderInterface provider = (ArtistProviderInterface) retainFragment;
                        provider.setArtists(artists);
                    }
                    updateArtistsInFragmentsByTag(ViewPagerFragment.TAG, artists);
                    updateArtistsInFragmentsByTag(ArtistsMasterFragment.TAG, artists);
                });
    }

    private void updateArtistsInFragmentsByTag(String tag, List<Artist> artists) {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(tag);
        if (fragment != null && fragment instanceof UpdateArtistsListener) {
            UpdateArtistsListener update = (UpdateArtistsListener) fragment;
            update.onArtistsUpdate(artists);
        }
    }

    private Observable<Object> showReloadSnackbar(Observable<? extends Throwable> errors) {
        return errors.flatMap( error ->
                Observable.create(
                        subscriber -> Snackbar.make( findViewById(R.id.main_frame_layout), R.string.no_data, Snackbar.LENGTH_INDEFINITE )
                                .setAction( R.string.reload, v -> { subscriber.onNext(null); subscriber.onCompleted(); })
                                .setActionTextColor( ContextCompat.getColor( this, R.color.colorAccent ) )
                                .show()));
    }

    @Override
    public void onMoreClick(int id, View sharedView) {
        Fragment retainFragment = getSupportFragmentManager().findFragmentByTag(RetainFragment.TAG);
        ArtistDialogFragment dialogFragment = ArtistDialogFragment.newInstance(id, retainFragment);
        if (isOrientationPortrait()) {
            openArtistDetails(dialogFragment);
        } else {
            showArtistDialog(dialogFragment);
        }
        selectedArtistId = id;
    }

    private void showArtistDialog(DialogFragment dialogFragment) {
        dialogFragment.setShowsDialog(true);
        dialogFragment.show(getSupportFragmentManager(), "dialog_fragment");
    }

    private void openArtistDetails(Fragment fragmentDialog) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_frame_layout, fragmentDialog)
                .addToBackStack("fragment_dialog")
                .commit();
    }

    private boolean isOrientationPortrait() {
        return findViewById(R.id.detail_frame_layout) == null;
    }
}
