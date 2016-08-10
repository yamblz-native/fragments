package ru.yandex.yamblz.ui.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import butterknife.BindView;
import ru.yandex.yamblz.App;
import ru.yandex.yamblz.R;
import ru.yandex.yamblz.model.Artist;

public class ArtistFragment extends BaseFragment {

    public static final String EXTRA_ARTIST = "com.austry.mobilization.ARTIST";

    @BindView(R.id.ivArtistCover)
    NetworkImageView ivCover;

    private Artist artist;
    private ShowDetailsCallback clickCallback;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            clickCallback = (ShowDetailsCallback) getActivity();
        }catch (ClassCastException e){
            throw new ClassCastException("Activity needs to implement ShowDetailsCallback!");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_artist, container, false);
        Bundle args = getArguments();
        if (args != null) {
            artist = (Artist) args.getSerializable(EXTRA_ARTIST);
        }

        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ivCover.setOnClickListener(v -> clickCallback.showArtistDetails(artist));
        ImageLoader imageLoader = App.from(getContext()).getVolley().getImageLoader();
        ivCover.setImageUrl(artist.getCover().getBig(), imageLoader);
    }
}
