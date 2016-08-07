package ru.yandex.yamblz.homework.artists.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;

import java.lang.ref.WeakReference;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.yandex.yamblz.R;
import ru.yandex.yamblz.homework.data.entity.Artist;

public class ArtistsAdapter extends RecyclerView.Adapter<ArtistsAdapter.ArtistsViewHolder>
{
    private View emptyView;
    private List<Artist> artists;
    private OnItemClickListener onItemClickListener;
    private WeakReference<Context> context;

    public interface OnItemClickListener
    {
        void onItemClick(View view, int position);
    }

    public ArtistsAdapter(Context context, View emptyView)
    {
        this.emptyView = emptyView;
        this.context = new WeakReference<>(context);
    }

    @Override
    public ArtistsViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_artist, parent, false);
        return new ArtistsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ArtistsViewHolder holder, int position)
    {
        Artist artist = getList().get(position);
        holder.bind(artist);
    }

    @Override
    public int getItemCount()
    {
        if (null == artists) return 0;
        return artists.size();
    }

    public void changeList(List<Artist> newList)
    {
        artists = newList;
        notifyDataSetChanged();
        emptyView.setVisibility(getItemCount() == 0 ? View.VISIBLE : View.GONE);
    }

    public List<Artist> getList()
    {
        return artists;
    }

    public class ArtistsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        @BindView(R.id.item_artist_cover) ImageView artistImage;
        @BindView(R.id.item_artist_name) TextView artistName;

        public ArtistsViewHolder(View view)
        {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v)
        {
            int adapterPosition = getAdapterPosition();
            onItemClickListener.onItemClick(v, adapterPosition);
        }

        public void bind(Artist artist)
        {
            artistName.setText(artist.getName());
            Glide.with(context.get())
                    .load(artist.getCoverSmall())
                    .override(100, 100)
                    .into(artistImage);
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener)
    {
        onItemClickListener = listener;
    }
}
