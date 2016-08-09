package ru.yandex.yamblz.ui.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ru.yandex.yamblz.R;
import ru.yandex.yamblz.model.Artist;

public class ArtistDetailDialogFragment extends DialogFragment {
    private static final String ARTIST_ARG = "artist_arg";
    private static final String FULLSCREEN_ARG = "fullscreen_arg";

    private boolean mFullscreen;
    private Unbinder mUnbinder;

    @BindView(R.id.fragment_artist_detail_name)
    TextView mName;

    @BindView(R.id.fragment_artist_detail_genre)
    TextView mGenre;

    @BindView(R.id.fragment_artist_detail_count)
    TextView mCount;

    @BindView(R.id.fragment_artist_detail_url)
    TextView mUrl;

    @BindView(R.id.fragment_artist_detail_description)
    TextView mDescription;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mFullscreen = getArguments().getBoolean(FULLSCREEN_ARG);
        return new Dialog(getActivity(), getTheme()) {
            @Override
            public void onBackPressed() {
                if (mFullscreen) {
                    Activity activity = getActivity();
                    if (activity != null) {
                        activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                    }
                }
                super.onBackPressed();
            }
        };
    }

    public static ArtistDetailDialogFragment newInstance(Artist artist, boolean fullscreen) {
        Bundle args = new Bundle();
        args.putParcelable(ARTIST_ARG, artist);
        args.putBoolean(FULLSCREEN_ARG, fullscreen);
        ArtistDetailDialogFragment fragment = new ArtistDetailDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onStart() {
        super.onStart();
        if (mFullscreen) {
            Dialog dialog = getDialog();
            if (dialog != null) {
                dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_artist_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUnbinder = ButterKnife.bind(this, view); // Не от BaseFragment наследуемся

        Artist artist = getArguments().getParcelable(ARTIST_ARG);
        mName.setText(artist.getName());
        mGenre.setText(artist.getGenresAsString());
        // TODO: Сделать строки нормально
        mCount.setText(artist.getCountOfAlbums() + " альбомов, " + artist.getCountOfTracks() + " треков");
        mUrl.setText(artist.getSiteUrl());
        mDescription.setText(artist.getDescription());
    }

    @Override
    public void onDestroyView() {
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
        super.onDestroyView();
    }
}