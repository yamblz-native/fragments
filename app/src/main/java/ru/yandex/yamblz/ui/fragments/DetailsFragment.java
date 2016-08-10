package ru.yandex.yamblz.ui.fragments;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.OnClick;
import ru.yandex.yamblz.R;
import ru.yandex.yamblz.model.Artist;

/**
 * Created by SerG3z on 08.08.16.
 */

public class DetailsFragment extends BaseDialogFragment {
    private static final String TAG_ARTIST = "item_artist";
    private static final String CUSTOM_SESSION = "android.support.customtabs.extra.SESSION";
    private static final String TOOLBAR_COLOR = "android.support.customtabs.extra.TOOLBAR_COLOR";
    @BindView(R.id.image_details)
    ImageView imageView;
    @BindView(R.id.music_details)
    TextView typeMusic;
    @BindView(R.id.info_details)
    TextView typeInfo;
    @BindView(R.id.biography_info_details)
    TextView biographyInfo;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private Artist artist;

    public static DetailsFragment newInstance(Artist artist) {
        DetailsFragment pageFragment = new DetailsFragment();
        Bundle arguments = new Bundle();
        arguments.putParcelable(TAG_ARTIST, artist);
        pageFragment.setArguments(arguments);
        return pageFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            artist = bundle.getParcelable(TAG_ARTIST);
        }
    }

    @SuppressLint("InflateParams")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.details_info, null);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        Bundle bundle = getArguments();
        if (bundle != null) {
            artist = bundle.getParcelable(TAG_ARTIST);
        }
        toolbar.setNavigationOnClickListener(v ->
        {
            if (this.getDialog() != null) {
                this.dismiss();
            } else {
                getActivity().getSupportFragmentManager()
                        .popBackStack();
            }

        });

        if (artist != null) {
            setDataToView();
        }
    }

    private void setDataToView() {
        toolbar.setTitle(artist.getName());
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        Glide.with(this)
                .load(artist.getCover().getSmall())
                .dontAnimate()
                .into(imageView);

        typeMusic.setText(artist.getGenres());
        String typeInfoStr = String.valueOf(artist.getAlbums() + artist.getTracks());
        typeInfo.setText(typeInfoStr);
        biographyInfo.setText(artist.getDescription());
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    @OnClick(R.id.floating_button)
    public void onClickFloatingActionButton(View view) {
        int color = ContextCompat.getColor(getContext(), R.color.colorPrimary);
        if (artist.getLink() != null) {
            Uri address = Uri.parse(artist.getLink());
            Intent browseIntent = new Intent(Intent.ACTION_VIEW, address);
            Bundle extras = new Bundle();
            extras.putBinder(CUSTOM_SESSION, null);
            extras.putInt(TOOLBAR_COLOR, color);
            browseIntent.putExtras(extras);
            startActivity(browseIntent);
        } else {
            Snackbar snackbar = Snackbar.make(view, R.string.error_link, Snackbar.LENGTH_LONG);
            View snackBarView = snackbar.getView();
            snackBarView.setBackgroundColor(color);
            snackbar.show();
        }
    }
}