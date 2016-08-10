package ru.yandex.yamblz.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ru.yandex.yamblz.R;
import ru.yandex.yamblz.model.Artist;
import ru.yandex.yamblz.ui.other.ShowImageCallback;


public class ArtistsRecyclerAdapter extends RecyclerView.Adapter<ArtistsRecyclerAdapter.ArtistViewHolder> {

    private List<Artist> data;
    private ShowImageCallback clickCallback;

    public ArtistsRecyclerAdapter(List<Artist> data, ShowImageCallback clickCallback) {
        this.data = data;
        this.clickCallback = clickCallback;
    }

    @Override
    public ArtistViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_artist, parent, false);
        return new ArtistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ArtistViewHolder holder, int position) {
        Artist currentArtist = data.get(position);
        holder.itemView.setOnClickListener((v -> {
            clickCallback.showImage(currentArtist);
        }));
        holder.tvName.setText(currentArtist.getName());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ArtistViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName;

        public ArtistViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tvArtistName);
        }
    }
}
