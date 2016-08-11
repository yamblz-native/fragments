package ru.yandex.yamblz.ui.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hannesdorfmann.fragmentargs.annotation.Arg;
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs;

import butterknife.BindView;
import ru.yandex.yamblz.R;
import ru.yandex.yamblz.euv.shared.model.Artist;
import ru.yandex.yamblz.ui.views.SquareDraweeView;

@FragmentWithArgs
public class ArtistShortInfo extends BaseFragment {

    @Arg Artist artist;
    @BindView(R.id.cover) SquareDraweeView cover;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_short_info, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cover.setImageURI(Uri.parse(artist.getCover().getBig()));
    }
}
