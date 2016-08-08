package ru.yandex.yamblz.ui.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import ru.yandex.yamblz.App;
import ru.yandex.yamblz.R;
import ru.yandex.yamblz.developer_settings.DeveloperSettingsModule;
import ru.yandex.yamblz.retrofit.ApiServices;
import ru.yandex.yamblz.ui.fragments.ViewPagerFragment;
import ru.yandex.yamblz.ui.fragments.RetainFragment;
import ru.yandex.yamblz.ui.other.ArtistProviderInterface;
import ru.yandex.yamblz.ui.other.UpdateArtistsListener;
import ru.yandex.yamblz.ui.other.ViewModifier;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends BaseActivity {

    @Inject @Named(DeveloperSettingsModule.MAIN_ACTIVITY_VIEW_MODIFIER)
    ViewModifier viewModifier;

    @BindView(R.id.main_frame_layout)
    View mainLayout;

    @SuppressLint("InflateParams") // It's okay in our case.
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.get(this).applicationComponent().inject(this);

        setContentView(viewModifier.modify(getLayoutInflater().inflate(R.layout.activity_main, null)));

        if (savedInstanceState == null) {
            RetainFragment retain = new RetainFragment();
            ViewPagerFragment content = new ViewPagerFragment();
            content.setTargetFragment(retain, 0);
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(retain, RetainFragment.TAG)
                    .replace(R.id.main_frame_layout, content, ViewPagerFragment.TAG)
                    .commit();
            downloadArtists();
        }
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
                    Fragment pagerFragment = getSupportFragmentManager().findFragmentByTag(ViewPagerFragment.TAG);
                    if (pagerFragment != null && pagerFragment instanceof UpdateArtistsListener) {
                        UpdateArtistsListener updateContent = (UpdateArtistsListener) pagerFragment;
                        updateContent.onArtistsUpdate(artists);
                    }
                });
    }

    private Observable<Object> showReloadSnackbar(Observable<? extends Throwable> errors) {
        return errors.flatMap( error ->
                Observable.create(
                        subscriber -> Snackbar.make( mainLayout, R.string.no_data, Snackbar.LENGTH_INDEFINITE )
                                .setAction( R.string.reload, v -> { subscriber.onNext(null); subscriber.onCompleted(); })
                                .setActionTextColor( ContextCompat.getColor( this, R.color.colorAccent ) )
                                .show()));
    }
}
