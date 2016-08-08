package ru.yandex.yamblz.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.yandex.yamblz.R;
import ru.yandex.yamblz.data.Artist;
import ru.yandex.yamblz.ui.other.ArtistProviderInterface;

/**
 * Created by Volha on 07.08.2016.
 */

public class ArtistMainFragment extends Fragment {

    public static final String TAG_ARTIST_ID = "artist_id";
    public static final String TAG_ARTIST_IMGAE_LINK = "image_link";

    public static ArtistMainFragment newInstance(int id, String link) {
        ArtistMainFragment fragment = new ArtistMainFragment();
        Bundle args = new Bundle();
        args.putInt(TAG_ARTIST_ID, id);
        args.putString(TAG_ARTIST_IMGAE_LINK, link);
        fragment.setArguments(args);
        return fragment;
    }

    @BindView(R.id.artist_cover)
    ImageView imageViewCover;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_artist_main, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        int artistId = getArguments().getInt(TAG_ARTIST_ID);

        String imageLink = getArguments().getString(TAG_ARTIST_IMGAE_LINK);
        ImageLoader loader = ImageLoader.getInstance();
        loader.displayImage(imageLink, imageViewCover);
    }
}
