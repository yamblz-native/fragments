package ru.yandex.yamblz.homework.artists;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ru.yandex.yamblz.R;;
import ru.yandex.yamblz.homework.Utils;
import ru.yandex.yamblz.homework.data.entity.Artist;

import static ru.yandex.yamblz.homework.artists.ArtistsFragment.KEY_ARTIST;

/**
 * Created by postnov on 25.03.2016.
 */
public class DetailsFragment extends DialogFragment
{
    @BindView(R.id.detail_cover)
    ImageView cover;

    @BindView(R.id.detail_genres)
    TextView genres;

    @BindView(R.id.detail_albums_songs)
    TextView albumsAndTracks;

    @BindView(R.id.detail_desc)
    TextView description;

    private Unbinder unbinder;

    public static DetailsFragment newInstance(Artist artist)
    {
        Bundle args = new Bundle();
        args.putParcelable(KEY_ARTIST, artist);
        DetailsFragment fragment = new DetailsFragment();
        fragment.setArguments(args);
        fragment.setStyle(DialogFragment.STYLE_NO_TITLE, 0);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle bundle)
    {
        View view = inflater.inflate(R.layout.fragment_details, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        Artist artist = getArguments().getParcelable(KEY_ARTIST);
        boolean isLargeLayout = getResources().getBoolean(R.bool.large_layout);
        if (isLargeLayout) initToolbar(view, artist.getName());

        if (artist != null)
        {
            Glide.with(this).load(artist.getCoverSmall()).into(cover);

            String albums = getResources().getQuantityString(
                    R.plurals.numberOfAlbums,
                    artist.getAlbums(),
                    artist.getAlbums());
            String tracks = getResources().getQuantityString(
                    R.plurals.numberOfTracks,
                    artist.getTracks(),
                    artist.getTracks());

            genres.setText(artist.getGenres());
            albumsAndTracks.setText(Utils.concatStrings(tracks, ", ", albums));
            description.setText(artist.getDesc());
        }
    }

    @Override
    public void onDestroyView()
    {
        if (unbinder != null) unbinder.unbind();
        super.onDestroyView();
    }

    private void initToolbar(View view, String name)
    {
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.details_toolbar);
        toolbar.setTitle(name);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(v -> {
            getFragmentManager().beginTransaction().remove(this).commit();
        });
    }
}
