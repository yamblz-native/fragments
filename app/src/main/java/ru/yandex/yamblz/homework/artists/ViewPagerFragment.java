package ru.yandex.yamblz.homework.artists;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import ru.yandex.yamblz.R;;
import ru.yandex.yamblz.homework.Injection;
import ru.yandex.yamblz.homework.artists.adapter.PagerFragmentAdapter;
import ru.yandex.yamblz.homework.artists.interfaces.ArtistsPresenter;
import ru.yandex.yamblz.homework.artists.interfaces.ArtistsView;
import ru.yandex.yamblz.homework.artists.interfaces.ToolbarProvider;
import ru.yandex.yamblz.homework.base.BaseFragment;
import ru.yandex.yamblz.homework.data.entity.Artist;

/**
 * Created by platon on 06.08.2016.
 */
public class ViewPagerFragment extends BaseFragment implements ArtistsView, SwipeRefreshLayout.OnRefreshListener
{
    private ArtistsPresenter presenter;

    @BindView(R.id.view_pager)
    ViewPager viewPager;

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;

    @BindView(R.id.swipe_view)
    SwipeRefreshLayout swipeLayout;

    public static ViewPagerFragment newInstance()
    {
        return new ViewPagerFragment();
    }

    @Override
    protected int getLayout()
    {
        return R.layout.fragment_view_pager;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        presenter = Injection.providePresenter(Injection.provideDataSource(
                getContext().getApplicationContext()));
        swipeLayout.setOnRefreshListener(this);

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onResume()
    {
        super.onResume();
        presenter.bind(this);
        presenter.fetchArtists();
        getToolbarProvider().updateToolbar(getString(R.string.app_name));
    }

    @Override
    public void onPause()
    {
        presenter.unsubscribe();
        presenter.unbind();
        super.onPause();
    }

    @Override
    public void showArtists(List<Artist> artists)
    {
        viewPager.setAdapter(new PagerFragmentAdapter(getChildFragmentManager(), artists));
    }

    @Override
    public void showProgressView(boolean show)
    {
        swipeLayout.setRefreshing(show);
    }

    @Override
    public void showError(String error)
    {
        Toast.makeText(getContext(), error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRefresh()
    {
        presenter.fetchArtists();
    }
}
