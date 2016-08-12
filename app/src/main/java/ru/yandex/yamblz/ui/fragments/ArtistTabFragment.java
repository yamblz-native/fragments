package ru.yandex.yamblz.ui.fragments;

import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.transition.ChangeBounds;
import android.transition.ChangeImageTransform;
import android.transition.ChangeTransform;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.TransitionSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import icepick.Icepick;
import icepick.State;
import ru.yandex.yamblz.R;
import ru.yandex.yamblz.artistmodel.Artist;

/**
 * Created by root on 8/10/16.
 */
public class ArtistTabFragment extends BaseFragment implements View.OnClickListener {

    @State Artist artist;

    @BindView(R.id.photo) ImageView photo;
    @BindView(R.id.more_button) Button moreButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_artist_tab, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(!(getActivity() instanceof OnDetailClickListener)) {
            throw new IllegalStateException("Host activity must implement " + OnDetailClickListener.class.getCanonicalName());
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        artist = (Artist) getArguments().getSerializable(Artist.class.getCanonicalName());

        if(artist != null) {
            Picasso.with(getContext()).load(artist.getCover().getBig()).into(photo);
        }

        moreButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        Map<String, View> transitionViews = new HashMap<>();

        transitionViews.put(getResources().getString(R.string.artist_photo_transition_name), photo);

        setSharedElementReturnTransition(new TransitionSet()
                .setOrdering(TransitionSet.ORDERING_TOGETHER)
                .addTransition(new ChangeBounds())
                .addTransition(new ChangeImageTransform())
                .addTransition(new ChangeTransform()));

        TransitionSet transition = new TransitionSet()
                .addTransition(new Fade())
                .addTransition(new Slide());

        setEnterTransition(transition);
        setExitTransition(transition);

        ((OnDetailClickListener) getActivity()).onDetailClick(artist, transitionViews);
    }

    public interface OnDetailClickListener {
        void onDetailClick(Artist artist, Map<String, View> transitionViews);
    }

}
