package ru.yandex.yamblz.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import ru.yandex.yamblz.App;
import ru.yandex.yamblz.R;
import ru.yandex.yamblz.domain.DataManager;
import ru.yandex.yamblz.model.Artist;
import ru.yandex.yamblz.ui.interfaces.ArtistPreviewNavigator;

public class ArtistPreviewFragment extends Fragment {

    public static final String TAG = ArtistPreviewFragment.class.getName();

    private static final String ARG_POSITION  = "position";

    private ArtistPreviewNavigator navigator;
    private int position;
    private ImageView ivCover;
    private Button btnMore;

    @Inject
    DataManager dataManager;

    public static ArtistPreviewFragment newInstance(int position) {
        ArtistPreviewFragment fragment = new ArtistPreviewFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.get(getContext()).applicationComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_artist_preview, container, false);

        initViews(view);

        navigator = (ArtistPreviewNavigator) getActivity();

        position = getArguments().getInt(ARG_POSITION);
        Artist artist = dataManager.getArtists().get(position);
        showArtistPreview(artist);

        return view;
    }

    private void initViews(View view) {
        ivCover = (ImageView) view.findViewById(R.id.iv_cover);
        btnMore = (Button) view.findViewById(R.id.btn_more);

        btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigator.navigateToArtistDetails(position);
            }
        });
    }

    private void showArtistPreview(Artist artist) {
        Picasso.with(getContext())
                .load(artist.getBigCoverUrl())
                .into(ivCover);
    }

}
