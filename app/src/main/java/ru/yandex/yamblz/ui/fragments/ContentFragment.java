package ru.yandex.yamblz.ui.fragments;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
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

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_content, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getLoaderManager().initLoader(1, null, this);
        if (savedInstanceState == null) {
            //artistFragment = ArtistFragmentBuilder.newArtistFragment(0);
//            getChildFragmentManager().beginTransaction().replace(R.id.container, artistFragment, "artist")
                    //.commit();
        } else {
           // artistFragment = (ArtistFragment) getChildFragmentManager().findFragmentByTag("artist");
        }
        if (getResources().getBoolean(R.bool.is_tablet)) {
            recyclerView = (RecyclerView) getView().findViewById(R.id.recycler);
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        artistFragment = null;
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
        if (getResources().getBoolean(R.bool.is_tablet)) {
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(new ArtistRecyclerAdapter(position -> {
                artistFragment.update(position);
            }));
        } else {
            ViewPager viewPager = (ViewPager) getView().findViewById(R.id.view_pager);
            viewPager.setAdapter(new ArtistsPagerAdapter(getChildFragmentManager(), data));
            TabLayout tabLayout = (TabLayout) getView().findViewById(R.id.tab_layout);
            tabLayout.setupWithViewPager(viewPager);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }


    public interface OnItemClicked {
        void itemClicked(String name);
    }
}
