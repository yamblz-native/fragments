package ru.yandex.yamblz.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.frogermcs.androiddevmetrics.internal.ui.MetricsActivity;
import com.hannesdorfmann.fragmentargs.annotation.Arg;
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs;

import javax.inject.Inject;

import butterknife.BindView;
import ru.yandex.yamblz.ApplicationComponent;
import ru.yandex.yamblz.R;
import ru.yandex.yamblz.di.module.FragmentArgumentModule;
import ru.yandex.yamblz.di.module.PresenterModule;
import ru.yandex.yamblz.domain.model.core.Bard;
import ru.yandex.yamblz.domain.model.presentation.BardUI;
import ru.yandex.yamblz.ui.contcract.BardDetailContract;

/**
 * Created by Александр on 07.08.2016.
 */
@FragmentWithArgs
public class BardDetailFragment extends BaseDialogFragment implements BardDetailContract.BardDetailView {
    public static final String TAG = "BardDetailFragment";

    @Inject
    protected BardDetailContract.BardsDetailPresenter bardDetailPresenter;
    @Arg
    long idBard;

    @BindView(R.id.custom_toolbar)
    protected Toolbar toolbar;
    //@BindView(R.id.tv_bard_name)
    //protected TextView tvBardName;
    @BindView(R.id.sdv_bard_photo)
    protected SimpleDraweeView sdvPhoto;
    @BindView(R.id.tv_bard_genres)
    protected TextView tvGenres;
    @BindView(R.id.tv_bard_song)
    protected TextView tvCounters;
    @BindView(R.id.tv_bard_albums)
    protected TextView tvAlbums;
    @BindView(R.id.tv_bard_biography)
    protected TextView tvBiography;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail_bard, container, false);
    }

    @Override
    protected void setUpComponent(ApplicationComponent applicationComponent) {
        applicationComponent.plus(FragmentArgumentModule.bardDetailPresenterArgument(idBard)).inject(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (!getResources().getBoolean(R.bool.is_two_pane)) activity.getSupportActionBar().hide();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        bardDetailPresenter.bindView(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        AppCompatActivity activity = (AppCompatActivity) getActivity();

        activity.setSupportActionBar((Toolbar) activity.findViewById(R.id.toolbar));
        if (!getResources().getBoolean(R.bool.is_two_pane)) activity.getSupportActionBar().show();
        bardDetailPresenter.unbindView(this);
    }

    @Override
    public void showBard(BardUI bard) {
        sdvPhoto.setImageURI(bard.bigImage());
        tvGenres.setText(TextUtils.join(", ", bard.genres()));
        tvBiography.setText(bard.description());
        toolbar.setTitle(bard.name());
        tvCounters.setText(String.format(getResources().getQuantityText(R.plurals.plur_song, bard.tracks()).toString(), bard.tracks()));
        tvAlbums.setText(getResources().getQuantityText(R.plurals.plur_albums, bard.albums()));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            getFragmentManager().popBackStack();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static BardDetailFragment newInstance(long idBard){
        return BardDetailFragmentBuilder.newBardDetailFragment(idBard);
    }
}
