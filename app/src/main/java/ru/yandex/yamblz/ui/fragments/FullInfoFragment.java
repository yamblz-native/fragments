package ru.yandex.yamblz.ui.fragments;

import android.content.ContentValues;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.yandex.yamblz.R;
import ru.yandex.yamblz.model.Singer;

public class FullInfoFragment extends Fragment {

    @BindView(R.id.title)
    TextView txt;
    @BindView(R.id.bio)
    TextView bio;
    @BindView(R.id.tracks)
    TextView tracks;
    @BindView(R.id.cover_big)
    ImageView cover;
    @BindView(R.id.background)
    ImageView back;

    private Singer singer;

    public static FullInfoFragment newInstance(Singer singer) {
        Bundle args = new Bundle();
        args.putParcelable("Singer", singer);
        FullInfoFragment fragment = new FullInfoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info, null);
        ButterKnife.bind(this, view);
        if (getActivity().findViewById(R.id.tabs) != null)
            getActivity().findViewById(R.id.tabs).setVisibility(View.GONE);
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        singer = getArguments().getParcelable("Singer");
        setHasOptionsMenu(true);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        txt.setText(singer.getName());
        bio.setMovementMethod(new ScrollingMovementMethod());
        bio.setText(singer.getName() + " - " + singer.getBio());
        tracks.setText("Альбомов " + singer.getAlbums() + ", треков " + singer.getTracks());
        Context context = cover.getContext();
        Picasso.with(context).load(singer.getCover_big()).into(cover);
        Picasso.with(context).load(singer.getCover_big()).into(back);

    }
}
