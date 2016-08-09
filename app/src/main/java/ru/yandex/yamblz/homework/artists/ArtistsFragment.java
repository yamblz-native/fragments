package ru.yandex.yamblz.homework.artists;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.OnClick;
import ru.yandex.yamblz.R;;
import ru.yandex.yamblz.homework.artists.interfaces.FragmentTransactionManager;
import ru.yandex.yamblz.homework.artists.interfaces.ToolbarProvider;
import ru.yandex.yamblz.homework.base.BaseFragment;
import ru.yandex.yamblz.homework.data.entity.Artist;

public class ArtistsFragment extends BaseFragment
{
    public static final String KEY_ARTIST = "ru.yandex.yamblz.ARTIST";

    @BindView(R.id.iv_artist_cover_big)
    ImageView cover;

    @BindView(R.id.btn_artist_details)
    Button details;


    public static ArtistsFragment newInstance(Artist artist)
    {
        ArtistsFragment fragment = new ArtistsFragment();
        Bundle args = new Bundle();
        args.putParcelable(KEY_ARTIST, artist);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    protected int getLayout()
    {
        return R.layout.fragment_artist;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        Artist artist = getArguments().getParcelable(KEY_ARTIST);
        if (artist != null)
        {
            Glide.with(this).load(artist.getCoverSmall()).into(cover);
        }
    }

    @OnClick(R.id.btn_artist_details)
    void onClickDetails()
    {
        Artist artist = getArguments().getParcelable(KEY_ARTIST);
        boolean isLargeLayout = getResources().getBoolean(R.bool.large_layout);

        if (isLargeLayout) DetailsFragment.newInstance(artist).show(getChildFragmentManager(), "details");

        else getFragmentTransactionManager().addFragment(DetailsFragment.newInstance(artist), true);
    }
}
