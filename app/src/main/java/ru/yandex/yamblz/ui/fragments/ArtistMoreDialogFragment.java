package ru.yandex.yamblz.ui.fragments;


import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
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

@FragmentWithArgs
public class ArtistMoreDialogFragment extends DialogFragment {
    @Arg
    int artistPosition;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentArgs.inject(this);
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        dismiss();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_artist_more,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageView imageView= (ImageView) view.findViewById(R.id.artist_image);
        imageView.setBackgroundColor(Color.BLACK);
        ArtistModel artistModel=null;
        Picasso.with(getContext()).load(artistModel.getBigImage()).into(imageView);

        StringBuilder genres=new StringBuilder();
        for(String genre :artistModel.getGenres()){
            genres.append(genre);
            genres.append(", ");
        }
        genres.delete(genres.length()-2,genres.length());
        TextView songs= (TextView) view.findViewById(R.id.artist_songs);
        String text = String.format(view.getContext().getResources().getString(R.string.artist_song),
                artistModel.getAlbums(), artistModel.getTracks());
        songs.setText(text);
        TextView bioText= (TextView) view.findViewById(R.id.artist_biography);
        bioText.setText(artistModel.getDescription());
    }
}
