package ru.yandex.yamblz.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ru.yandex.yamblz.R;
import ru.yandex.yamblz.data.Artist;
import ru.yandex.yamblz.ui.other.ArtistProviderInterface;

/**
 * Created by Volha on 07.08.2016.
 */

public class ArtistDialogFragment extends AppCompatDialogFragment {

    public static final String TAG = "artist_dialog_fragment";
    private static final String TAG_ARTIST_ID = "artist_id";
    private Unbinder viewBinder;

    @BindView(R.id.artist_cover)
    ImageView imageViewCover;
    @BindView(R.id.artist_genres)
    TextView genres;
    @BindView(R.id.artist_description)
    TextView description;

    public static ArtistDialogFragment newInstance(int artistId, Fragment targetFragment) {
        ArtistDialogFragment fragment = new ArtistDialogFragment();
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
        viewBinder = ButterKnife.bind(this, view);

        Fragment targetFragment = getTargetFragment();
        int artistId = getArguments().getInt(TAG_ARTIST_ID);

        if (targetFragment instanceof ArtistProviderInterface) {
            Artist artist = ((ArtistProviderInterface) targetFragment).getArtistById(artistId);

            if (artist != null) {
                String imageLink = artist.getCover().getBig();
                ImageLoader loader = ImageLoader.getInstance();
                loader.displayImage(imageLink, imageViewCover, new DisplayImageOptions.Builder().cacheOnDisk(true).build());

                genres.setText(artist.getGenresString());
                description.setText(artist.getDescription());
            }
        } else {
            throw new ClassCastException(getString(R.string.not_implemented_artist_interface_exception));
        }
    }

    @Override
    public void onDestroyView() {
        viewBinder.unbind();
        super.onDestroyView();
    }
}
