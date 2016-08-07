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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import ru.yandex.yamblz.R;
import ru.yandex.yamblz.model.Artist;
import ru.yandex.yamblz.ui.activities.MainActivity;

/**
 * Created by Aleksandra on 06/08/16.
 */
public class CoverFragment extends BaseFragment {
    public static final String FRAGMENT_TAG = "CoverFragment";
    private static final String ARTIST_EXTRA = "ARTIST_EXTRA";

    @BindView(R.id.artist_big_cover)
    ImageView cover;

    @BindView(R.id.button_more)
    Button buttonMore;

    private Unbinder unbinder;

    private Artist artist;

    public static Fragment newInstance(Artist artist) {
        Bundle args = new Bundle();
        args.putParcelable(ARTIST_EXTRA, artist);

        Fragment fragment = new CoverFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_cover, container, false);
        unbinder = ButterKnife.bind(this, v);

        Bundle args = getArguments();
        if (args != null) {
            artist = args.getParcelable(ARTIST_EXTRA);
            Picasso.with(getActivity())
                    .load(artist.cover().bigCover())
                    .error(R.drawable.leak_canary_icon) //TODO: delete canary icon
                    .into(cover);
        }
        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.button_more)
    public void onMoreClick() {
        ((MainActivity) getActivity()).showDetailedFragment(artist);
    }
}
