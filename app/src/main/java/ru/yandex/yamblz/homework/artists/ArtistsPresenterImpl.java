package ru.yandex.yamblz.homework.artists;

import java.util.List;

import ru.yandex.yamblz.homework.artists.interfaces.ArtistsPresenter;
import ru.yandex.yamblz.homework.artists.interfaces.ArtistsView;
import ru.yandex.yamblz.homework.data.entity.Artist;
import ru.yandex.yamblz.homework.data.source.IDataSource;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by platon on 15.07.2016.
 */
public class ArtistsPresenterImpl implements ArtistsPresenter
{
    private ArtistsView mArtistsView;
    private CompositeSubscription mSubscriptions;
    private IDataSource dataSource;

    public ArtistsPresenterImpl(IDataSource dataSource)
    {
        this.dataSource = dataSource;
        mSubscriptions = new CompositeSubscription();
    }

    @Override
    public void fetchArtists()
    {
        mArtistsView.showProgressView(true);
        dataSource.getArtists()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext, onError);
    }

    @Override
    public void bind(ArtistsView view)
    {
        this.mArtistsView = view;
    }

    @Override
    public void unbind()
    {
        mArtistsView.showProgressView(false);
        mArtistsView = null;
    }

    @Override
    public void unsubscribe()
    {
        mSubscriptions.clear();
    }

    private Action1<List<Artist>> onNext = artists ->
    {
        mArtistsView.showProgressView(false);
        mArtistsView.showArtists(artists);
    };

    private Action1<Throwable> onError = e ->
    {
        mArtistsView.showProgressView(false);
        mArtistsView.showError(e.getMessage());
    };
}
