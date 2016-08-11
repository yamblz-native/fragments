package ru.yandex.yamblz.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ru.yandex.yamblz.R;
import ru.yandex.yamblz.model.Artist;
import ru.yandex.yamblz.ui.activities.MainActivity;
import ru.yandex.yamblz.ui.recycler_view.ArtistPreviewViewHolder;
import timber.log.Timber;

/**
 * Created by Aleksandra on 06/08/16.
 */
public class ArtistAdapter extends RecyclerView.Adapter<ArtistPreviewViewHolder> {
    private List<Artist> dataset;

    @Override
    public ArtistPreviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_item, parent, false);
        final ArtistPreviewViewHolder h = new ArtistPreviewViewHolder(v);
        v.setOnClickListener(view -> {
            int adapterPosition = h.getAdapterPosition();
            if (adapterPosition != RecyclerView.NO_POSITION) {
                final Context context = view.getContext();
                if (context instanceof MainActivity) {
                    ((MainActivity) context).onArtistSelected(dataset.get(adapterPosition));
                }
            }
        });
        return h;
    }

    @Override
    public void onBindViewHolder(ArtistPreviewViewHolder holder, int position) {
        holder.bind(dataset.get(position));
    }

    @Override
    public int getItemCount() {
        return dataset == null ? 0 : dataset.size();
    }

    public void setDataset(List<Artist> dataset) {
        this.dataset = dataset;
        notifyDataSetChanged();
    }
}
