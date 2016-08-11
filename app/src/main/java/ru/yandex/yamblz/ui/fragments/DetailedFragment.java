package ru.yandex.yamblz.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.yandex.yamblz.R;
import ru.yandex.yamblz.model.Artist;

/**
 * Created by Aleksandra on 07/08/16.
 */
public class DetailedFragment extends DialogFragment {
    public static final String TAG = "DetailedFragment";
    private static final String ARTIST_KEY = "artist";

    @BindView(R.id.artist_detail_big_img)
    ImageView imageView;

    @BindView(R.id.artist_detail_albums_and_tracks)
    TextView albumsAndTracks;

    @BindView(R.id.artist_detail_biography_text)
    TextView artistDescription;

    @BindView(R.id.artist_detail_genre_text)
    TextView genres;

    private Artist artist;

    public static Fragment newInstance(Artist artist) {
        Bundle args = new Bundle();
        args.putParcelable(ARTIST_KEY, artist);
        Fragment fragment = new DetailedFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        artist = args.getParcelable(ARTIST_KEY);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_detailed, container, false);
        ButterKnife.bind(this, v);
        setViews();
        return v;
    }

    protected void setViews() {
        artistDescription.setText(artist.description());
        albumsAndTracks.setText(getResources().getQuantityString(R.plurals.artistAlbums, artist.albums(), artist.albums()) +
                " " +
                getResources().getQuantityString(R.plurals.artistTracks, artist.tracks(), artist.tracks()));

        genres.setText(TextUtils.join(", ", artist.genres()));

        Picasso.with(getActivity())
                .load(artist.cover().bigCover())
                .into(imageView);
    }
}
