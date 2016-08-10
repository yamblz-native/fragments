package ru.yandex.yamblz.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

import java.util.List;

import ru.yandex.yamblz.R;
import ru.yandex.yamblz.model.Artist;
import ru.yandex.yamblz.ui.interfaces.OnItemClickListener;

public class ArtistsListAdapter extends RecyclerView.Adapter<ArtistsListAdapter.ArtistViewHolder> {

    private Context context;
    private List<Artist> artists;
    private OnItemClickListener onItemClickListener;

    public ArtistsListAdapter(Context context, List<Artist> artists, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.artists = artists;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ArtistViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_artists_list, parent, false);
        return new ArtistViewHolder(view, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(ArtistViewHolder holder, int position) {
        Artist artist = artists.get(position);

        Picasso.with(context)
                .load(artist.getSmallCoverUrl())
                .placeholder(R.drawable.cover_placeholder_small)
                .into(holder.ivCover);
        holder.tvName.setText(artist.getName());
    }

    @Override
    public int getItemCount() {
        return artists.size();
    }

    public void setData(List<Artist> artists) {
        this.artists = artists;
        notifyDataSetChanged();
    }

    public static class ArtistViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        LinearLayout container;
        ImageView ivCover;
        TextView tvName;

        private OnItemClickListener listener;

        public ArtistViewHolder(View itemView, OnItemClickListener listener) {
            super(itemView);

            container = (LinearLayout) itemView.findViewById(R.id.ll_item_container);
            ivCover = (ImageView) itemView.findViewById(R.id.iv_cover);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);

            this.listener = listener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onItemClick(getAdapterPosition());
        }

    }

}
