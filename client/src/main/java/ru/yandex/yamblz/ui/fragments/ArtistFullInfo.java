package ru.yandex.yamblz.ui.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.PluralsRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hannesdorfmann.fragmentargs.annotation.Arg;
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs;

import butterknife.BindView;
import ru.yandex.yamblz.R;
import ru.yandex.yamblz.euv.shared.model.Artist;
import ru.yandex.yamblz.ui.views.SquareDraweeView;

@FragmentWithArgs
public class ArtistFullInfo extends BaseFragment {

    @Arg Artist artist;

    @BindView(R.id.cover) SquareDraweeView cover;
    @BindView(R.id.name) TextView name;
    @BindView(R.id.genres) TextView genres;
    @BindView(R.id.albums_and_tracks) TextView albumsAndTracks;
    @BindView(R.id.description) TextView description;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_full_info, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String albums = getPlurals(R.plurals.albums, artist.getAlbums());
        String tracks = getPlurals(R.plurals.tracks, artist.getTracks());

        cover.setImageURI(Uri.parse(artist.getCover().getBig()));
        name.setText(artist.getName());
        genres.setText(artist.getGenresStr());
        description.setText(artist.getDescription());
        albumsAndTracks.setText(getString(R.string.separator, albums, tracks));
    }

    private String getPlurals(@PluralsRes int resId, int quantity) {
        return getResources().getQuantityString(resId, quantity, quantity);
    }
}
