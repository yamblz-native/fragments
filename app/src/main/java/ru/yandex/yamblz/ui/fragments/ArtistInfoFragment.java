package ru.yandex.yamblz.ui.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewCompat;
import android.transition.ChangeBounds;
import android.transition.ChangeImageTransform;
import android.transition.Fade;
import android.transition.TransitionSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import icepick.Icepick;
import icepick.State;
import ru.yandex.yamblz.R;
import ru.yandex.yamblz.artistmodel.Artist;
import ru.yandex.yamblz.util.StringUtils;

/**
 * Created by root on 8/10/16.
 */
public class ArtistInfoFragment extends DialogFragment {

    @BindView(R.id.image) ImageView image;
    @BindView(R.id.link) TextView link;
    @BindView(R.id.genres) TextView genres;
    @BindView(R.id.counter) TextView counter;
    @BindView(R.id.bio_title) TextView bioTitle;
    @BindView(R.id.bio_text) TextView bioText;

    @State Artist artist;

    private Unbinder viewBinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Icepick.restoreInstanceState(this, savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_artist_info, null, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewBinder = ButterKnife.bind(this, view);
        artist = (Artist) getArguments().getSerializable(Artist.class.getCanonicalName());
        fillPage();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void fillPage() {

        Picasso.with(getContext()).load(artist.getCover().getBig()).centerCrop().fit().into(image);

        //genres.setText(StringUtils.getGenresString(artist.getGenres()));
        counter.setText(StringUtils.getCounterInfoString(getContext(), artist));
        bioText.setText(artist.getDescription());

        link.setOnClickListener((v) -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW);
            browserIntent.setData(Uri.parse(artist.getLink()));
            startActivity(browserIntent);
        });

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Icepick.saveInstanceState(this, outState);
    }

    @Override
    public void onDestroyView() {
        if (viewBinder != null) {
            viewBinder.unbind();
        }
        super.onDestroyView();
    }

}
