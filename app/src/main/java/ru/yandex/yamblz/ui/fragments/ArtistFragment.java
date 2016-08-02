package ru.yandex.yamblz.ui.fragments;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.hannesdorfmann.fragmentargs.FragmentArgs;
import com.hannesdorfmann.fragmentargs.annotation.Arg;
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs;
import com.squareup.picasso.Picasso;

import ru.yandex.yamblz.R;
import ru.yandex.yamblz.artists.ArtistModel;
import ru.yandex.yamblz.artists.DataSingleton;

@FragmentWithArgs
public class ArtistFragment extends Fragment {
    @Arg
    int position;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentArgs.inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_artist,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageView imageView= (ImageView) view.findViewById(R.id.artist_image);
        imageView.setBackgroundColor(Color.BLACK);
        ArtistModel artistModel= DataSingleton.get().getArtists().get(position);
        Picasso.with(getContext()).load(artistModel.getBigImage()).into(imageView);
        Button more= (Button) view.findViewById(R.id.btn_more);
        more.setOnClickListener(v -> {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout,
                    new ArtistMoreFragmentBuilder(position).build()).addToBackStack(null).commit();
        });

    }
}
