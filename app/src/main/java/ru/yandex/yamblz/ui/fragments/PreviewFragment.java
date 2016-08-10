package ru.yandex.yamblz.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import ru.yandex.yamblz.R;
import ru.yandex.yamblz.provider.DataProvider;
import ru.yandex.yamblz.singerscontracts.Singer;

public class PreviewFragment extends BaseFragment {

    private static final String SINGER_EXTRA = "singer";
    private static final String SINGER_ID_EXTRA = "singer_id";

    private static final int NO_SINGER = -1;

    private int mSingerId = NO_SINGER;
    private Singer mSinger;

    @BindView(R.id.photo)
    ImageView photo;

    @Inject
    DataProvider dataProvider;

    private Callbacks mCallbacks;

    private Unbinder mUnbinder;

    public interface Callbacks {
        void onMoreChosen(Singer singer);
    }

    private DataProvider.Callback<Singer> mSingerCallback = new DataProvider.Callback<Singer>() {
        @Override
        public void postResult(@Nullable Singer result) {
            if(result == null) {
                showSingerLoadError();
            } else {
                setSinger(result);
            }
        }
    };

    public static PreviewFragment newInstance(Singer singer) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(SINGER_EXTRA, singer);
        bundle.putInt(SINGER_ID_EXTRA, singer.getId());

        PreviewFragment previewFragment = new PreviewFragment();
        previewFragment.setArguments(bundle);

        return previewFragment;
    }

    public static PreviewFragment newInstance(int singerId) {
        Bundle bundle = new Bundle();
        bundle.putInt(SINGER_ID_EXTRA, singerId);

        PreviewFragment previewFragment = new PreviewFragment();
        previewFragment.setArguments(bundle);

        return previewFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof Callbacks) {
            mCallbacks = (Callbacks)context;
        } else {
            throw new RuntimeException("Must implement " + Callbacks.class.getName());
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getAppComponent().inject(this);

        Bundle bundle = (savedInstanceState != null ? savedInstanceState : getArguments());

        if(bundle != null) {
            if (bundle.containsKey(SINGER_EXTRA)) {
                mSinger = bundle.getParcelable(SINGER_EXTRA);
            }
            if(bundle.containsKey(SINGER_ID_EXTRA)) {
                mSingerId = bundle.getInt(SINGER_ID_EXTRA);
            }
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.preview_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mUnbinder = ButterKnife.bind(this, view);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(mSinger != null) {
            onSingerSet();
        } else if(mSingerId != NO_SINGER) {
            dataProvider.getSinger(mSingerId, mSingerCallback);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        dataProvider.cancel(mSingerCallback);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    public void setSinger(@NonNull Singer singer) {
        mSinger = singer;
        mSingerId = singer.getId();
        onSingerSet();
    }

    public boolean hasSinger() {
        return mSingerId != NO_SINGER;
    }


    @OnClick(R.id.more)
    void onMoreClick() {
        mCallbacks.onMoreChosen(mSinger);
    }

    public void setSingerId(int singerId) {
        mSingerId = singerId;
        mSinger = null;

        dataProvider.getSinger(mSingerId, mSingerCallback);
    }

    private void onSingerSet() {
        if(mSinger == null) {
            hideInfo();
        } else {
            showSingerInfo();
        }
    }

    private void showSingerLoadError() {
        Snackbar.make(photo, getString(R.string.error), Snackbar.LENGTH_LONG).show();
    }

    private void hideInfo() {
        photo.setImageDrawable(null);
    }

    private void showSingerInfo() {
        photo.setImageDrawable(null);
        Picasso.with(getContext()).load(mSinger.getCover().getSmall()).fit().centerInside().into(photo);
    }


}
