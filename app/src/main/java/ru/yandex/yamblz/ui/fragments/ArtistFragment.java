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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ru.yandex.yamblz.R;
import ru.yandex.yamblz.artists.ArtistModel;
import ru.yandex.yamblz.artists.DataSingleton;

@FragmentWithArgs
public class ArtistFragment extends Fragment {
    @BindView(R.id.artist_image) ImageView imageView;
    @BindView(R.id.btn_more) Button btnMore;
    @Arg
    int position;
    private Unbinder unbinder;

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
        unbinder= ButterKnife.bind(this,view);
        imageView.setBackgroundColor(Color.BLACK);
        if(savedInstanceState!=null){
            position=savedInstanceState.getInt("position");
        }
        update(position);
        if(getContext().getResources().getBoolean(R.bool.is_tablet)){
            btnMore.setOnClickListener(v -> {
               ArtistMoreDialogFragment dialogFragment=ArtistMoreDialogFragmentBuilder.newArtistMoreDialogFragment(position);
                dialogFragment.show(getChildFragmentManager(),null);
            });
        }else{
            btnMore.setOnClickListener(v -> {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout,
                        new ArtistMoreFragmentBuilder(position).build()).addToBackStack(null).commit();
            });
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();

    }

    public void update(int position) {
        this.position=position;
        ArtistModel artistModel= DataSingleton.get().getArtists().get(position);
        Picasso.with(getContext()).load(artistModel.getBigImage()).into(imageView);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("position",position);
    }
}
