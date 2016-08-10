package ru.yandex.yamblz.ui.fragments;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.yandex.yamblz.R;
import ru.yandex.yamblz.lib.ContentProviderContract;
import ru.yandex.yamblz.ui.adapters.ArtistRecyclerAdapter;
import ru.yandex.yamblz.ui.adapters.ArtistsPagerAdapter;

public class ContentFragment extends BaseFragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private ArtistFragment artistFragment;
    private RecyclerView recyclerView;
    private ArtistsPagerAdapter adapter;
    private Cursor cursor;
    private ViewPager viewPager;

    private int viewPosition;
    private Handler handler; //need to set viewpager position

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_content, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler = new Handler();
        getLoaderManager().initLoader(1, null, this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getResources().getBoolean(R.bool.is_tablet)) {
            recyclerView = (RecyclerView) getView().findViewById(R.id.recycler);
            if (savedInstanceState != null)
                viewPosition = savedInstanceState.getInt("viewPagerPosition");
        } else {
            viewPager = (ViewPager) getView().findViewById(R.id.view_pager);
            if (savedInstanceState != null)
                viewPosition = savedInstanceState.getInt("viewPagerPosition");
        }
        if (cursor != null) {
            setData();
        }

    }

    private void setData() {
        if (getResources().getBoolean(R.bool.is_tablet)) {
            artistFragment = (ArtistFragment) getChildFragmentManager().findFragmentByTag("artist");
            if (artistFragment == null) {
                cursor.moveToFirst();
                artistFragment = ArtistFragmentBuilder.newArtistFragment(cursor.getString(cursor.getColumnIndex(ContentProviderContract.Artists.NAME)));
                getChildFragmentManager().beginTransaction().replace(R.id.container, artistFragment, "artist").commit();
            }
            LinearLayoutManager layout = new LinearLayoutManager(getContext());

            recyclerView.setLayoutManager(layout);
            recyclerView.setAdapter(new ArtistRecyclerAdapter(name -> {
                artistFragment.update(name);
            }, cursor));
            layout.scrollToPosition(viewPosition);

        } else {
            adapter = new ArtistsPagerAdapter(getChildFragmentManager(), cursor);
            viewPager.setAdapter(adapter);
            handler.post(() -> viewPager.setCurrentItem(viewPosition, false));
            TabLayout tabLayout = (TabLayout) getView().findViewById(R.id.tab_layout);
            tabLayout.setupWithViewPager(viewPager);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        artistFragment = null;
        if (viewPager != null) viewPosition = viewPager.getCurrentItem();
        viewPager = null;
        recyclerView = null;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getContext(),
                Uri.parse(ContentProviderContract.URL)
                , null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        setCursor(data);
    }

    private void setCursor(Cursor data) {
        this.cursor = data;
        if (getView() != null) {
            setData();
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (viewPager != null) viewPosition = viewPager.getCurrentItem();
        if (recyclerView != null && recyclerView.getLayoutManager() != null) {
            LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
            viewPosition = manager.findFirstVisibleItemPosition();
        }
        outState.putInt("viewPagerPosition", viewPosition);
        super.onSaveInstanceState(outState);

    }


    public interface OnItemClicked {
        void itemClicked(String name);
    }
}
