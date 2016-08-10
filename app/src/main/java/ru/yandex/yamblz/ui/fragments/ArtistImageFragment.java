package ru.yandex.yamblz.ui.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;


import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import butterknife.BindView;
import ru.yandex.yamblz.App;
import ru.yandex.yamblz.R;
import ru.yandex.yamblz.model.Artist;
import ru.yandex.yamblz.ui.other.ShowDetailsCallback;

public class ArtistImageFragment extends BaseFragment {

    public static final String EXTRA_ARTIST = "com.austry.mobilization.ARTIST";

    @BindView(R.id.ivArtistCover)
    NetworkImageView ivCover;

    @BindView(R.id.pbLoading)
    ProgressBar pbLoading;

    private Artist artist;
    private ShowDetailsCallback clickCallback;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            clickCallback = (ShowDetailsCallback) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity needs to implement ShowDetailsCallback!");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_artist, container, false);
        Bundle args = getArguments();
        if (args != null) {
            artist = args.getParcelable(EXTRA_ARTIST);
        }
        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageLoader imageLoader = App.from(getContext()).getVolley().getImageLoader();
        ivCover.setOnClickListener(v -> clickCallback.showArtistDetails(artist));
        //костыль для Volley чтобы скрыть прогрессБар загрузки картинки
        ivCover.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                if (ivCover.getDrawable() != null) {
                    pbLoading.setVisibility(View.GONE);
                }
            }
        });
        ivCover.setImageUrl(artist.getCover().getBig(), imageLoader);
    }
}
