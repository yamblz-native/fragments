package ru.yandex.yamblz.ui.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import ru.yandex.yamblz.R;
import ru.yandex.yamblz.utils.StringUtils;
import ru.yandex.yamblz.model.Artist;

public class ArtistDetailsFragment extends BaseFragment{


    public static final String EXTRA_ARTIST = "EXTRA_ARTIST_DETAILS";
    @BindView(R.id.tvArtistGenres)
    TextView tvGenres;
    @BindView(R.id.tvArtistAlbums)
    TextView tvAlbums;
    @BindView(R.id.tvArtistTracks)
    TextView tvTracks;
    @BindView(R.id.tvArtistDescription)
    TextView tvDescription;
    @BindView(R.id.tvArtistName)
    TextView tvName;

    private Artist artist;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_artist_details, container, false);
        Bundle args = getArguments();
        if (args != null) {
            artist = (Artist) args.getSerializable(EXTRA_ARTIST);
        }
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvGenres.setText(StringUtils.getGenres(artist.getGenres()));

        tvAlbums.setText(getPlural(R.plurals.albums, artist.getAlbums()));
        tvTracks.setText(getPlural(R.plurals.tracks, artist.getTracks()));
        tvDescription.setText(artist.getDescription());
        tvName.setText(artist.getName());

    }

    private String getPlural(int pluralId, int number) {
        return getResources().getQuantityString(pluralId, number, number);
    }
}
