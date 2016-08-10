package ru.yandex.yamblz.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.yandex.yamblz.R;
import ru.yandex.yamblz.data.Artist;
import ru.yandex.yamblz.ui.other.OnArtistListItemClickListener;

/**
 * Created by Volha on 08.08.2016.
 */

public class ArtistsRecyclerAdapter extends RecyclerView.Adapter<ArtistsRecyclerAdapter.ArtistHolder> {

    List<Artist> items = new ArrayList<>();
    ImageLoader imageLoader;
    OnArtistListItemClickListener listener;

    public ArtistsRecyclerAdapter(OnArtistListItemClickListener listener) {
        imageLoader = ImageLoader.getInstance();
        this.listener = listener;
    }

    public void setData(List<Artist> artists) {
        this.items.clear();
        this.items.addAll(artists);
        notifyDataSetChanged();
    }

    @Override
    public ArtistHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ArtistHolder holder = new ArtistHolder(inflater.inflate(R.layout.list_artist_item, parent, false));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Artist artist = items.get(holder.getAdapterPosition());
                listener.onArtistClick(artist.getId());
            }
        });
        return holder;
    }

    public void onBindViewHolder(ArtistHolder holder, int position) {
            holder.bind(items.get(position));
    }

    @Override
    public long getItemId(int position) {
        return items.get(position).getId();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ArtistHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.artist_cover)
        ImageView artistCover;
        @BindView(R.id.artist_name)
        TextView artistName;

        public ArtistHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(Artist artist) {
            imageLoader.displayImage(artist.getCover().getSmall(), artistCover);
            artistName.setText(artist.getName());
        }
    }
}
