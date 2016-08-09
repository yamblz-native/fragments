package ru.yandex.yamblz.homework.artists;

import java.util.List;

import ru.yandex.yamblz.homework.artists.interfaces.ArtistsPresenter;
import ru.yandex.yamblz.homework.artists.interfaces.ArtistsView;
import ru.yandex.yamblz.homework.data.entity.Artist;
import ru.yandex.yamblz.homework.data.source.IDataSource;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by platon on 15.07.2016.
 */
public class ArtistsPresenterImpl implements ArtistsPresenter
{
    private ArtistsView artistsView;
    private CompositeSubscription subscriptions;
    private IDataSource dataSource;

    public ArtistsPresenterImpl(IDataSource dataSource)
    {
        this.dataSource = dataSource;
        subscriptions = new CompositeSubscription();
    }

    @Override
    public void fetchArtists()
    {
        artistsView.showProgressView(true);
        subscriptions.add(dataSource.getArtists()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext, onError));
    }

    @Override
    public void bind(ArtistsView view)
    {
        this.artistsView = view;
    }

    @Override
    public void unbind()
    {
        artistsView = null;
    }

    @Override
    public void unsubscribe()
    {
        subscriptions.clear();
    }

    private Action1<List<Artist>> onNext = artists ->
    {
        artistsView.showProgressView(false);
        artistsView.showArtists(artists);
    };

    private Action1<Throwable> onError = e ->
    {
        artistsView.showProgressView(false);
        artistsView.showError(e.getMessage());
    };
}
