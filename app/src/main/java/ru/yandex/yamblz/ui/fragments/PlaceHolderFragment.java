package ru.yandex.yamblz.ui.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import ru.yandex.yamblz.R;

/**
 * Created by vorona on 03.08.16.
 */

public class PlaceholderFragment extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";
    OnMoreClicked mCallback;
    ListProvider mProvider;


    public PlaceholderFragment() {
    }

    public static PlaceholderFragment newInstance(int sectionNumber) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tabbed, container, false);
        Bundle args = getArguments();
        int pos = args.getInt(ARG_SECTION_NUMBER);

        mCallback = (OnMoreClicked) getActivity();
        mProvider = (ListProvider) getActivity();

        Picasso.with(getActivity()).load(mProvider.getList().get(pos).getCover_big())
                .into(((ImageView) rootView.findViewById(R.id.imgCover)));
        (rootView.findViewById(R.id.btnMore)).setOnClickListener(v -> mCallback.onArticleSelected(pos));

        return rootView;
    }
}