package ru.yandex.yamblz.ui.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.OnClick;
import ru.yandex.yamblz.R;
import ru.yandex.yamblz.model.Artist;

public class ArtistPhotoFragment extends BaseFragment {
    private static final String ARTIST_ARG = "artist_arg";
    private Artist mArtist;

    @BindView(R.id.fragment_artist_photo_image_cover)
    ImageView mCover;

    public static ArtistPhotoFragment newInstance(Artist artist) {
        Bundle args = new Bundle();
        args.putParcelable(ARTIST_ARG, artist);
        ArtistPhotoFragment fragment = new ArtistPhotoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public interface Callbacks {
        void onClickMoreInformation(Artist artist);
    }

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_artist_photo, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mArtist = getArguments().getParcelable(ARTIST_ARG);
        Picasso.with(getContext())
                .load(mArtist.getUrlOfBigCover())
                .placeholder(R.drawable.ic_album_black_400dp)
                .error(R.drawable.ic_album_black_400dp)
                .into(mCover);
    }

    @OnClick(R.id.fragment_artist_photo_button_more)
    void showInformation() {
        Callbacks callbacks = (Callbacks) getActivity();
        if (callbacks != null) {
            callbacks.onClickMoreInformation(mArtist);
        }
    }
}