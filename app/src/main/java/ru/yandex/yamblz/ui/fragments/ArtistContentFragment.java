package ru.yandex.yamblz.ui.fragments;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import butterknife.BindView;
import ru.yandex.yamblz.R;
import ru.yandex.yamblz.ui.other.OnArtistMoreClickListener;

/**
 * Created by Volha on 07.08.2016.
 */

public class ArtistContentFragment extends BaseFragment {

    public static final String TAG_ARTIST_ID = "artist_id";
    public static final String TAG_ARTIST_IMAGE_LINK = "image_link";

    public static ArtistContentFragment newInstance(int id, String link) {
        ArtistContentFragment fragment = new ArtistContentFragment();
        Bundle args = new Bundle();
        args.putInt(TAG_ARTIST_ID, id);
        args.putString(TAG_ARTIST_IMAGE_LINK, link);
        fragment.setArguments(args);
        return fragment;
    }

    @BindView(R.id.artist_cover)
    ImageView imageViewCover;
    @BindView(R.id.progress)
    ProgressBar progressBar;
    @BindView(R.id.btmMore)
    FloatingActionButton btnMore;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_artist_main, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String imageLink = getArguments().getString(TAG_ARTIST_IMAGE_LINK);
        ImageLoader loader = ImageLoader.getInstance();
        int id = getArguments().getInt(TAG_ARTIST_ID);
        ViewCompat.setTransitionName(imageViewCover, id + getString(R.string.transition_cover_name));

        loader.displayImage(
                imageLink,
                imageViewCover,
                new DisplayImageOptions.Builder().cacheOnDisk( true ).build(),
                new SimpleImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                btnMore.setScaleX(0f);
                btnMore.setScaleY(0f);
                progressBar.setVisibility(View.VISIBLE);
            }
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                progressBar.setVisibility(View.GONE);
                btnMore.animate()
                        .scaleX(1f)
                        .scaleY(1f)
                        .setInterpolator(new AccelerateInterpolator())
                        .setDuration(400)
                        .start();
            }
        });

        btnMore.setOnClickListener(onMoreClickListener);
    }

    View.OnClickListener onMoreClickListener = v -> {
        Activity parent = getActivity();
        if (parent instanceof OnArtistMoreClickListener) {
            ((OnArtistMoreClickListener)parent).onMoreClick(getArguments().getInt(TAG_ARTIST_ID), imageViewCover);
        } else {
            throw new ClassCastException(getString(R.string.not_implemented_on_artist_more_click_listener));
        }
    };

}
