package ru.yandex.yamblz.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.yandex.yamblz.R;
import ru.yandex.yamblz.model.Artist;

/**
 * Created by SerG3z on 08.08.16.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private RecyclerViewListener listener;
    private List<Artist> dataArtists = new ArrayList<>();

    public void setOnItemClickListener(RecyclerViewListener myClickListener) {
        this.listener = myClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_recycler, parent, false), listener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(dataArtists.get(position));
    }

    public void setAllArtists(List<Artist> artistsList) {
        dataArtists.addAll(artistsList);
        notifyDataSetChanged();
    }

    public void clear() {
        dataArtists.clear();
        notifyDataSetChanged();
    }

    public Artist getItem(int position) {
        return dataArtists.get(position);
    }

    @Override
    public int getItemCount() {
        return dataArtists.size();
    }

    public interface RecyclerViewListener {
        void onItemClick(int position, View v);
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.item_image_recycler)
        ImageView imageView;
        @BindView(R.id.item_text_view_recycler)
        TextView textView;
        private RecyclerViewListener listener;

        ViewHolder(View itemView, RecyclerViewListener listener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.listener = listener;
            itemView.setOnClickListener(this);
        }

        void bind(Artist artist) {
            Glide.with(itemView.getContext())
                    .load(artist.getCover().getSmall())
                    .placeholder(R.drawable.google_chrome)
                    .centerCrop()
                    .crossFade()
                    .into(imageView);
            textView.setText(artist.getName());
        }

        @Override
        public void onClick(View view) {
            if (listener != null) {
                listener.onItemClick(getAdapterPosition(), view);
            }
        }
    }
}
