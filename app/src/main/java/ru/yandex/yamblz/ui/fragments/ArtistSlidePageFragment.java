package ru.yandex.yamblz.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.hannesdorfmann.fragmentargs.FragmentArgs;
import com.hannesdorfmann.fragmentargs.annotation.Arg;
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs;

import butterknife.BindView;
import ru.yandex.yamblz.R;
import ru.yandex.yamblz.data.models.Artist;

/**
 * Created by shmakova on 07.08.16.
 */

@FragmentWithArgs
public class ArtistSlidePageFragment extends BaseFragment {
    @Arg
    private Artist artist;

    @BindView(R.id.cover_big)
    ImageView cover;

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
        return inflater.inflate(R.layout.fragment_artist_slide_page, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Glide.with(getContext())
                .load(artist.getCover().getBig())
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .crossFade()
                .into(cover);
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }
}
