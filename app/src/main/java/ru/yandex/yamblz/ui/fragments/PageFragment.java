package ru.yandex.yamblz.ui.fragments;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.Random;

import butterknife.BindView;
import butterknife.OnClick;
import ru.yandex.yamblz.R;
import ru.yandex.yamblz.model.Artist;

/**
 * Created by SerG3z on 08.08.16.
 */

public class PageFragment extends BaseFragment {

    public interface PageFragmentListener {
        void onPageFragmentClicked(Artist artist);

        void onPageDialogFragmentClicked(Artist artist, boolean visible);
    }

    private static final String TAG_ARTIST = "item_artist";
    @BindView(R.id.item_image)
    ImageView imageView;
    private int backColor;
    private Artist artist;
    private PageFragmentListener listener;

    static PageFragment newInstance(Artist artist) {
        PageFragment pageFragment = new PageFragment();
        Bundle arguments = new Bundle();
        arguments.putParcelable(TAG_ARTIST, artist);
        pageFragment.setArguments(arguments);
        return pageFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            artist = bundle.getParcelable(TAG_ARTIST);
        }
        Random rnd = new Random();
        backColor = Color.argb(100, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
    }

    @SuppressLint("InflateParams")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_view_pager, null);
        view.setBackgroundColor(backColor);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        setData();
    }

    private void setData() {
        listener = (PageFragmentListener) getActivity();
        if (artist != null) {
            Glide.with(getContext())
                    .load(artist.getCover().getBig())
                    .centerCrop()
                    .into(imageView);
        }
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
        setData();
    }

    @OnClick(R.id.item_btn)
    public void onClickMoreInfoBtn() {
        boolean flagDialogFragment = getResources().getBoolean(R.bool.flag_page_dialog_fragment);
        if (flagDialogFragment) {
            listener.onPageDialogFragmentClicked(artist, true);
        } else {
            listener.onPageFragmentClicked(artist);
        }
    }
}