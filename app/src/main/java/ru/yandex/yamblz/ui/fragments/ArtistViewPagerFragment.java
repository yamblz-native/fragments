package ru.yandex.yamblz.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import ru.yandex.yamblz.Provider;
import ru.yandex.yamblz.R;
import ru.yandex.yamblz.ui.adapters.ArtistPhotoPagerAdapter;

public class ArtistViewPagerFragment extends BaseFragment {
    private static final String POSITION_ARG = "position_arg";

    @BindView(R.id.fragment_artist_view_pager_view_pager)
    ViewPager mViewPager;

    public static ArtistViewPagerFragment newInstance(int position) {
        Bundle args = new Bundle();
        args.putInt(POSITION_ARG, position);
        ArtistViewPagerFragment fragment = new ArtistViewPagerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public interface Callbacks {
        void onScrollViewPager(int position);
    }

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_artist_view_pager, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Callbacks callbacks = (Callbacks) getActivity(); // Можно положить в onAttach(Activity), но оно deprecated
        Provider provider = (Provider) getActivity();

        FragmentManager fragmentManager = getFragmentManager();
        ArtistPhotoPagerAdapter pagerAdapter = new ArtistPhotoPagerAdapter(fragmentManager, provider.provideArtistProvider());

        mViewPager.setAdapter(pagerAdapter);
        mViewPager.addOnPageChangeListener(new CallbacksPageChangeListener(callbacks));

        int position = getArguments().getInt(POSITION_ARG);
        mViewPager.setCurrentItem(position);
    }

    public void setCurrentItem(int position) {
        if (mViewPager != null) {
            mViewPager.setCurrentItem(position);
        }
    }

    private class CallbacksPageChangeListener implements ViewPager.OnPageChangeListener {
        private WeakReference<Callbacks> mCallbacks;

        public CallbacksPageChangeListener(Callbacks callbacks) {
            mCallbacks = new WeakReference<>(callbacks);
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            Callbacks callbacks = mCallbacks.get();
            if (callbacks != null) {
                callbacks.onScrollViewPager(position);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
}