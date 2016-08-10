package ru.yandex.yamblz.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import javax.inject.Inject;

import ru.yandex.yamblz.App;
import ru.yandex.yamblz.R;
import ru.yandex.yamblz.domain.DataManager;
import ru.yandex.yamblz.model.Artist;

public class ArtistDetailsFragment extends DialogFragment {

    public static final String TAG = ArtistDetailsFragment.class.getName();

    private static final String ARG_POSITION  = "position";

    TextView tvName;
    TextView tvLink;
    TextView tvDescription;

    @Inject
    DataManager dataManager;

    public static ArtistDetailsFragment newInstance(int position) {
        ArtistDetailsFragment fragment = new ArtistDetailsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.get(getContext()).applicationComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_artist_details, container, false);
        initViews(view);

        int position = getArguments().getInt(ARG_POSITION);
        Artist artist = dataManager.getArtists().get(position);
        showArtistDetails(artist);

        return view;
    }

    private void initViews(View view) {
        tvName = (TextView) view.findViewById(R.id.tv_name);
        tvLink = (TextView) view.findViewById(R.id.tv_link);
        tvDescription = (TextView) view.findViewById(R.id.tv_description);
    }

    private void showArtistDetails(Artist artist) {
        tvName.setText(artist.getName());
        if (artist.getLink() == null) {
            tvLink.setVisibility(View.GONE);
        } else {
            tvLink.setText(artist.getLink());
        }
        tvDescription.setText(artist.getDescription());
    }

}
