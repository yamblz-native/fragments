package ru.yandex.yamblz.ui.fragments;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hannesdorfmann.fragmentargs.FragmentArgs;
import com.hannesdorfmann.fragmentargs.annotation.Arg;
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ru.yandex.yamblz.R;
import ru.yandex.yamblz.data.models.Artist;
import ru.yandex.yamblz.ui.activities.MainActivity;

@FragmentWithArgs
public class ArtistDetailFragment extends DialogFragment {
    @BindView(R.id.cover_big)
    ImageView cover;
    @BindView(R.id.genres)
    TextView genres;
    @BindView(R.id.info)
    TextView info;
    @BindView(R.id.description)
    TextView description;

    @Arg
    private Artist artist;

    private Unbinder viewBinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentArgs.inject(this);
    }

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_artist_detail, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewBinder = ButterKnife.bind(this, view);
        Resources resources = getResources();

        if (artist != null) {
            updateToolBar(artist.getName());

            Glide.with(view.getContext())
                    .load(artist.getCover().getBig())
                    .centerCrop()
                    .crossFade()
                    .into(cover);

            genres.setText(artist.getGenres());
            int albums = artist.getAlbums();
            int tracks = artist.getTracks();
            info.setText(resources.getQuantityString(R.plurals.albums, albums, albums) +
                    " ·" + resources.getQuantityString(R.plurals.tracks, tracks, tracks));
            description.setText(artist.getDescription());
        }
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    @Override
    public void onDestroyView() {
        if (viewBinder != null) {
            viewBinder.unbind();
        }
        super.onDestroyView();
    }

    /**
     * Updates toolbar
     * @param text - toolbar's title
     */
    private void updateToolBar(String text) {
        ActionBar actionBar = ((MainActivity) getActivity()).getSupportActionBar();

        if (actionBar != null) {
            actionBar.setTitle(text);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
}
