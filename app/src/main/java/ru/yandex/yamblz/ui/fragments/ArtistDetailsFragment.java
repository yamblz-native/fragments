package ru.yandex.yamblz.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.yandex.yamblz.R;
import ru.yandex.yamblz.data.Artist;
import ru.yandex.yamblz.ui.other.ArtistProviderInterface;

/**
 * Created by Volha on 07.08.2016.
 */

public class ArtistDetailsFragment extends Fragment {

    private static final String TAG_ARTIST_ID = "artist_id";

    @BindView(R.id.artist_cover)
    ImageView imageViewCover;
    @BindView(R.id.artist_genres)
    TextView genres;
    @BindView(R.id.artist_description)
    TextView description;

    public static ArtistDetailsFragment newInstance(int artistId, Fragment targetFragment) {
        ArtistDetailsFragment fragment = new ArtistDetailsFragment();
        Bundle args = new Bundle();
        args.putInt(TAG_ARTIST_ID, artistId);
        fragment.setArguments(args);
        fragment.setTargetFragment(targetFragment, 1);
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
        ButterKnife.bind(this, view);

        Fragment targetFragment = getTargetFragment();
        int artistId = getArguments().getInt(TAG_ARTIST_ID);

        if (targetFragment != null && targetFragment instanceof ArtistProviderInterface) {
            Artist artist = ((ArtistProviderInterface) targetFragment).getArtistById(artistId);

            if (artist != null) {
                String imageLink = artist.getCover().getBig();
                ImageLoader loader = ImageLoader.getInstance();
                loader.displayImage(imageLink, imageViewCover, new DisplayImageOptions.Builder().cacheOnDisk(true).build());

                genres.setText(artist.getGenresString());
                description.setText(artist.getDescription());
            }
        }
    }
}
