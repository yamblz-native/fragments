package ru.yandex.yamblz.ui.fragments;


import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.hannesdorfmann.fragmentargs.FragmentArgs;
import com.hannesdorfmann.fragmentargs.annotation.Arg;
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ru.yandex.yamblz.R;
import ru.yandex.yamblz.lib.ContentProviderContract;

@FragmentWithArgs
public class ArtistFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    @BindView(R.id.artist_image) ImageView imageView;
    @BindView(R.id.btn_more) Button btnMore;
    @Arg
    String name;
    private Unbinder unbinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentArgs.inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_artist, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        imageView.setBackgroundColor(Color.BLACK);
        if (savedInstanceState != null) {
            name = savedInstanceState.getString("name");
        }
        update(name);
        if (getContext().getResources().getBoolean(R.bool.is_tablet)) {
            btnMore.setOnClickListener(v -> {
                ArtistMoreDialogFragment dialogFragment = ArtistMoreDialogFragmentBuilder.newArtistMoreDialogFragment(name);
                dialogFragment.show(getChildFragmentManager(), null);
            });
        } else {
            btnMore.setOnClickListener(v -> {
                ArtistMoreDialogFragment dialogFragment = ArtistMoreDialogFragmentBuilder.newArtistMoreDialogFragment(name);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout,
                        dialogFragment).addToBackStack(null).commit();
            });
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();

    }

    public void update(String name) {
        this.name = name;
      //  getLoaderManager().initLoader(1, null, this);
        getLoaderManager().restartLoader(1,null,this);
        //ArtistModel artistModel = DataSingleton.get().getArtists().get(position);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("name", name);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getContext(),
                Uri.parse(ContentProviderContract.URL+"/"+name)
                , null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        data.moveToFirst();
        Picasso.with(getContext()).load(data.getString(data.getColumnIndex(ContentProviderContract.Artists.IMAGE_BIG))).placeholder(new ColorDrawable(Color.WHITE)).into(imageView);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

}
