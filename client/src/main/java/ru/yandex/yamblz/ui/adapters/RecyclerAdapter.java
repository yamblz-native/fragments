package ru.yandex.yamblz.ui.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.yandex.yamblz.R;
import ru.yandex.yamblz.euv.shared.model.Artist;
import ru.yandex.yamblz.ui.adapters.RecyclerAdapter.ArtistViewHolder;
import ru.yandex.yamblz.ui.views.SquareDraweeView;

public class RecyclerAdapter extends Adapter<ArtistViewHolder> {
    private ArtistSelectedListener callback;
    private RecyclerView recycler;
    private List<Artist> artists = new ArrayList<>();

    public RecyclerAdapter(Context context, RecyclerView recycler) {
        if (!(context instanceof ArtistSelectedListener)) {
            throw new RuntimeException(context + " must implement ArtistSelectedListener interface!");
        }
        callback = (ArtistSelectedListener) context;
        this.recycler = recycler;
    }


    public interface ArtistSelectedListener {
        void onArtistSelected(Artist artist);
    }


    @Override
    public ArtistViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View artistView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_artist_list_item, parent, false);
        artistView.setOnClickListener(v ->
                callback.onArtistSelected(artists.get(recycler.getChildAdapterPosition(v))));
        return new ArtistViewHolder(artistView);
    }


    @Override
    public int getItemCount() {
        return artists.size();
    }


    @Override
    public void onBindViewHolder(ArtistViewHolder holder, int position) {
        holder.bind(artists.get(position));
    }


    public void setArtists(@NonNull List<Artist> artists) {
        this.artists = artists;
        notifyDataSetChanged();
    }


    static class ArtistViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.cover) SquareDraweeView cover;
        @BindView(R.id.name) TextView name;

        public ArtistViewHolder(View artistView) {
            super(artistView);
            ButterKnife.bind(this, artistView);
        }

        public void bind(Artist artist) {
            cover.setImageURI(Uri.parse(artist.getCover().getSmall()));
            name.setText(artist.getName());
        }
    }
}
