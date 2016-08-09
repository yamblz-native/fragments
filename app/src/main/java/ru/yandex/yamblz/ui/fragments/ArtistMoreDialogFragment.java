package ru.yandex.yamblz.ui.fragments;


import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hannesdorfmann.fragmentargs.FragmentArgs;
import com.hannesdorfmann.fragmentargs.annotation.Arg;
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs;
import com.squareup.picasso.Picasso;

import ru.yandex.yamblz.R;
import ru.yandex.yamblz.lib.ArtistModel;
import ru.yandex.yamblz.lib.ContentProviderContract;

@FragmentWithArgs
public class ArtistMoreDialogFragment extends DialogFragment implements LoaderManager.LoaderCallbacks<Cursor> {
    @Arg
    String artistName;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentArgs.inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_artist_more,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageView imageView = (ImageView) view.findViewById(R.id.artist_image);
        imageView.setBackgroundColor(Color.BLACK);
        getLoaderManager().initLoader(1, null, this);
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getContext(),
                Uri.parse(ContentProviderContract.URL+"/"+artistName)
                , null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        data.moveToFirst();
        ArtistModel artistModel=ArtistModel.cursorToModel(data);
        ImageView imageView = (ImageView) getView().findViewById(R.id.artist_image);
        imageView.setBackgroundColor(Color.BLACK);
        Picasso.with(getContext()).load(artistModel.getBigImage()).into(imageView);

        StringBuilder genres=new StringBuilder();
        if(artistModel.getGenres().size()!=0){
            for(String genre :artistModel.getGenres()){
                genres.append(genre);
                genres.append(", ");
            }
            genres.delete(genres.length()-2,genres.length());
        }
        TextView songs= (TextView) getView().findViewById(R.id.artist_songs);
        String text = String.format(getView().getContext().getResources().getString(R.string.artist_song),
                artistModel.getAlbums(), artistModel.getTracks());
        songs.setText(text);
        TextView bioText= (TextView) getView().findViewById(R.id.artist_biography);
        bioText.setText(artistModel.getDescription());
        TextView genresText= (TextView) getView().findViewById(R.id.artist_genres);
        genresText.setText(genres);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

}
