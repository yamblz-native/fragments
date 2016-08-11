package ru.yandex.yamblz.ui.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hannesdorfmann.fragmentargs.annotation.Arg;
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs;

import butterknife.BindView;
import butterknife.OnClick;
import ru.yandex.yamblz.R;
import ru.yandex.yamblz.euv.shared.model.Artist;
import ru.yandex.yamblz.ui.views.SquareDraweeView;

@FragmentWithArgs
public class ArtistInfoShort extends BaseFragment {
    private MoreButtonClickListener callback;

    @Arg Artist artist;
    @BindView(R.id.cover) SquareDraweeView cover;

    public interface MoreButtonClickListener {
        void onMoreButtonClicked(Artist artist);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (!(context instanceof MoreButtonClickListener)) {
            throw new RuntimeException(context + " must implement MoreButtonClickListener interface!");
        }
        callback = (MoreButtonClickListener) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_artist_info_short, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cover.setImageURI(Uri.parse(artist.getCover().getBig()));
    }

    @OnClick(R.id.button_more)
    void more() {
        callback.onMoreButtonClicked(artist);
    }
}
