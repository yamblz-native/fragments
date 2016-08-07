package ru.yandex.yamblz.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.yandex.yamblz.R;
import ru.yandex.yamblz.model.Artist;

public class ArtistDetailDialogFragment extends DialogFragment {
    private static final String ARTIST_ARG = "artist_arg";
    private Artist mArtist;

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


    public static ArtistDetailDialogFragment newInstance(Artist artist) {
        Bundle args = new Bundle();
        args.putParcelable(ARTIST_ARG, artist);
        ArtistDetailDialogFragment fragment = new ArtistDetailDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_artist_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view); // Не от BaseFragment наследуемся
        mArtist = getArguments().getParcelable(ARTIST_ARG);

        mName.setText(mArtist.getName());
        mGenre.setText(mArtist.getGenres());
        // TODO: Сделать нормально
        mCount.setText(mArtist.getCountOfAlbums() + " альбомов, " + mArtist.getCountOfTracks() + " треков");
        mUrl.setText(mArtist.getSiteUrl());
        mDescription.setText(mArtist.getDescription());
    }
}