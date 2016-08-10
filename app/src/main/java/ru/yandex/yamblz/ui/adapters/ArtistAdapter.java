package ru.yandex.yamblz.ui.adapters;

import android.content.Intent;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import ru.yandex.yamblz.Model.Artist;
import ru.yandex.yamblz.R;
import ru.yandex.yamblz.ui.fragments.ArtistClickListener;
import ru.yandex.yamblz.ui.fragments.ArtistFragment;
import ru.yandex.yamblz.ui.fragments.ArtistInfoActivity;

/**
 * Created by danil on 25.04.16.
 */
public class ArtistAdapter extends RecyclerView.Adapter<ArtistAdapter.ViewHolder> implements ArtistClickListener {

    private List<Artist> mDataset;
    private FragmentActivity context;

    public ArtistAdapter(FragmentActivity context) {
        mDataset = new ArrayList<>();
        this.context = context;
    }

    @Override
    public ArtistAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);

        // change layout's params. here

        ViewHolder vh = new ViewHolder(v, this);

        return vh;
    }

    public void setItems(List<Artist> items) {
        if (mDataset != null) {
            int x = mDataset.size();
            mDataset.clear();
            notifyItemRangeRemoved(0, x);
        }
        if (items != null && mDataset != null) {
            mDataset.addAll(items);
            notifyItemRangeInserted(0, items.size());
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.name.setText(mDataset.get(position).getName());
        holder.genres.setText(mDataset.get(position).getGenres());
        holder.info.setText(mDataset.get(position).getInfo());
        holder.artistId.setText(String.valueOf(mDataset.get(position).getId()));
        final String cover = mDataset.get(position).getCover().getSmallCoverImage();
        if (cover != null && !cover.isEmpty()) {
            Picasso.with(context).load(cover).into(holder.image);
        } else {
            Log.d("azaza", mDataset.get(position).toString());
        }

    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    @Override
    public void onArtistClicked(int position) {

        Intent intent = ArtistInfoActivity.getIntent(context, mDataset.get(position));
        Bundle bundle = new Bundle();
        bundle.putString("bio", mDataset.get(position).getDescription());
        bundle.putString("pic", mDataset.get(position).getCover().getBigCoverImage());
        ArtistFragment ArtistAct = new ArtistFragment();
        ArtistAct.setArguments(bundle);
        context.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, ArtistAct).addToBackStack(null).commit();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView name;
        public TextView genres;
        public TextView info;
        public TextView artistId;
        public ImageView image;
        private ArtistClickListener listener;

        public ViewHolder(View v, final ArtistClickListener listener) {
            super(v);
            this.listener = listener;

            name = (TextView) v.findViewById(R.id.recycler_item_name);
            genres = (TextView) v.findViewById(R.id.recycler_item_genres);
            image = (ImageView) v.findViewById(R.id.recycler_item_image);
            info = (TextView) v.findViewById(R.id.recycler_item_additional_info);
            artistId = (TextView) v.findViewById(R.id.artist_id);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onArtistClicked(getAdapterPosition());
        }
    }
}
