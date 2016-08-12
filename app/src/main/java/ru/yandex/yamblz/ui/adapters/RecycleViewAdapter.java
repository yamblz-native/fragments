package ru.yandex.yamblz.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.yandex.yamblz.R;
import ru.yandex.yamblz.artistmodel.Artist;
import ru.yandex.yamblz.util.function.Consumer;

/**
 * Created by root on 8/10/16.
 */
public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.ContentHolder> {

    private List<Artist> artists;
    private final Consumer<Artist> onClickCallable;

    public RecycleViewAdapter(Artist[] artists, Consumer<Artist> onClickCallable) {
        this.onClickCallable = onClickCallable;
        this.artists = Arrays.asList(artists);
    }

    @Override
    public ContentHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        ContentHolder contentHolder = new ContentHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.artist_item, parent, false));

        contentHolder.itemView.setOnClickListener((v) -> onClickCallable.apply(contentHolder.artist));

        return contentHolder;
    }

    @Override
    public void onBindViewHolder(ContentHolder holder, int position) {
        holder.bind(artists.get(position));
    }

    @Override
    public int getItemCount() {
        return artists.size();
    }

    static class ContentHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.name) TextView name;
        @BindView(R.id.photo) ImageView photo;
        Artist artist;

        ContentHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(Artist artist) {
            this.artist = artist;
            Picasso.with(itemView.getContext()).load(artist.getCover().getSmall()).into(photo);
            name.setText(artist.getName());
        }

    }

}
