package ru.yandex.yamblz.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.yandex.yamblz.R;
import ru.yandex.yamblz.model.ArtistLab;
import ru.yandex.yamblz.model.ArtistProvider;
import ru.yandex.yamblz.ui.adapters.ArtistPhotoPagerAdapter;

public class ArtistViewPagerFragment extends BaseFragment {
    private static final String POSITION_ARG = "position_arg";
    private ArtistPhotoPagerAdapter mPagerAdapter;
    private ArtistProvider mArtistProvider;

    @BindView(R.id.fragment_artist_view_pager_view_pager)
    ViewPager mViewPager;

    public static ArtistViewPagerFragment newInstance(int position) {
        Bundle args = new Bundle();
        args.putInt(POSITION_ARG, position);
        ArtistViewPagerFragment fragment = new ArtistViewPagerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_artist_view_pager, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // TODO: Даггер-даггер...
        mArtistProvider = ArtistLab.get(getContext());

        FragmentManager fragmentManager = getFragmentManager();
        mPagerAdapter = new ArtistPhotoPagerAdapter(fragmentManager, mArtistProvider);
        mViewPager.setAdapter(mPagerAdapter);

        int position = getArguments().getInt(POSITION_ARG);
        mViewPager.setCurrentItem(position);
    }

    public void setCurrentItem(int position) {
        mViewPager.setCurrentItem(position);
    }
}