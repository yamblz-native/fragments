package ru.yandex.yamblz.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.yandex.yamblz.R;
import ru.yandex.yamblz.provider.DataProvider;
import ru.yandex.yamblz.singerscontracts.Singer;

public class DescFragment extends BaseDialogFragment {

    private static final String SINGER_ID_EXTRA = "singer_id";
    private static final String SINGER_EXTRA = "singer";
    private static final int NO_SINGER = -1;

    private Singer mSinger;
    private int mSingerId = NO_SINGER;

    @Inject
    DataProvider dataProvider;

    @BindView(R.id.name)
    TextView name;

    @BindView(R.id.genres)
    TextView genres;

    @BindView(R.id.tracks)
    TextView tracks;

    @BindView(R.id.albums)
    TextView albums;

    @BindView(R.id.more)
    TextView more;

    private DataProvider.Callback<Singer> mSingerCallback = new DataProvider.Callback<Singer>() {
        @Override
        public void postResult(@Nullable Singer result) {
            setSinger(result);
        }
    };

    public static DescFragment newInstance(int singerId) {
        Bundle bundle = new Bundle();
        bundle.putInt(SINGER_ID_EXTRA, singerId);

        DescFragment descFragment = new DescFragment();
        descFragment.setArguments(bundle);

        return descFragment;
    }

    public static DescFragment newInstance(Singer singer) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(SINGER_EXTRA, singer);
        bundle.putInt(SINGER_ID_EXTRA, singer.getId());

        DescFragment descFragment = new DescFragment();
        descFragment.setArguments(bundle);

        return descFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = (savedInstanceState != null ? savedInstanceState : getArguments());
        if(bundle != null) {
            if(bundle.containsKey(SINGER_EXTRA)) {
                mSinger = bundle.getParcelable(SINGER_EXTRA);
            }
            if(bundle.containsKey(SINGER_ID_EXTRA)) {
                mSingerId = bundle.getInt(SINGER_ID_EXTRA);
            }
        }

        getAppComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.desc_fragment, container, false);

        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        if(mSinger == null) {
            if(mSingerId != NO_SINGER) {
                dataProvider.getSinger(mSingerId, mSingerCallback);
            }
        } else {
            onSingerSet();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if(mSinger != null) {
            outState.putParcelable(SINGER_EXTRA, mSinger);
        }
        if(mSingerId != NO_SINGER) {
            outState.putInt(SINGER_ID_EXTRA, mSingerId);
        }
    }

    private void setSinger(@Nullable Singer singer) {
        if(singer == null) {
            hideData();
        } else {
            mSinger = singer;
            onSingerSet();
        }
    }

    private void hideData() {
        name.setText("");
        genres.setText("");
        tracks.setText("");
        albums.setText("");
        more.setText("");
    }

    private void onSingerSet() {
        showData();
    }

    private void showData() {
        assert mSinger != null;

        name.setText(mSinger.getName());
        genres.setText(TextUtils.join(", ", mSinger.getGenres()));
        tracks.setText(String.valueOf(mSinger.getTracks()));
        albums.setText(String.valueOf(mSinger.getAlbums()));
        more.setText(String.valueOf(mSinger.getDescription()));
    }
}
