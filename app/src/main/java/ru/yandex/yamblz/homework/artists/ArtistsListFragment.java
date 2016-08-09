package ru.yandex.yamblz.homework.artists;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import ru.yandex.yamblz.R;
import ru.yandex.yamblz.homework.Injection;
import ru.yandex.yamblz.homework.artists.adapter.ArtistsAdapter;
import ru.yandex.yamblz.homework.artists.interfaces.ArtistsPresenter;
import ru.yandex.yamblz.homework.artists.interfaces.ArtistsView;
import ru.yandex.yamblz.homework.artists.interfaces.FragmentTransactionManager;
import ru.yandex.yamblz.homework.artists.interfaces.ToolbarProvider;
import ru.yandex.yamblz.homework.base.BaseFragment;
import ru.yandex.yamblz.homework.data.entity.Artist;

/**
 * Created by platon on 06.08.2016.
 */
public class ArtistsListFragment extends BaseFragment implements ArtistsView,
        SwipeRefreshLayout.OnRefreshListener, ArtistsAdapter.OnItemClickListener {

    @BindView(R.id.recyclerview_artist)
    RecyclerView rv;

    @BindView(R.id.swipe_view)
    SwipeRefreshLayout swipeLayout;

    @BindView(R.id.artist_emptyview)
    View emptyView;

    private ArtistsAdapter artistsAdapter;
    private ArtistsPresenter presenter;


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        ToolbarProvider toolbarProvider = getToolbarProvider();
        if (toolbarProvider != null) toolbarProvider.updateToolbar(getString(R.string.app_name));
    }

    @Override
    protected int getLayout()
    {
        return R.layout.fragment_list_artist;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        presenter = Injection.providePresenter(Injection.provideDataSource(
                getContext().getApplicationContext()));

        artistsAdapter = new ArtistsAdapter(getContext());
        artistsAdapter.setOnItemClickListener(this);

        swipeLayout.setOnRefreshListener(this);
        rv.setAdapter(artistsAdapter);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onResume()
    {
        super.onResume();
        presenter.bind(this);
        presenter.fetchArtists();
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
        artistsAdapter.changeList(artists);
        emptyView.setVisibility(artistsAdapter.getItemCount() == 0 ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showProgressView(boolean show)
    {
        swipeLayout.setRefreshing(show);
    }

    @Override
    public void showError(String error)
    {
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRefresh()
    {
        presenter.fetchArtists();
    }

    @Override
    public void onItemClick(View view, int position)
    {
        Artist artist = artistsAdapter.getList().get(position);
        getFragmentTransactionManager().replaceFragment(ArtistsFragment.newInstance(artist), false);
    }
}
