package ru.yandex.yamblz.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.yandex.yamblz.R;
import ru.yandex.yamblz.model.Artist;
import ru.yandex.yamblz.model.ArtistProvider;
import ru.yandex.yamblz.ui.fragments.ArtistListFragment;


public class ArtistListRecyclerAdapter extends RecyclerView.Adapter<ArtistListRecyclerAdapter.ArtistHolder> {
    private ArtistProvider mArtistProvider;
    private Picasso mPicasso;
    private WeakReference<ArtistListFragment.Callbacks> mCallbacks;

    public ArtistListRecyclerAdapter(ArtistProvider artistProvider, Picasso picasso, ArtistListFragment.Callbacks callbacks) {
        mArtistProvider = artistProvider;
        mPicasso = picasso;
        mCallbacks = new WeakReference<>(callbacks);
    }

    @Override
    public ArtistHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_artist_item, parent, false);
        return new ArtistHolder(view, mPicasso, mCallbacks.get());
    }

    @Override
    public void onBindViewHolder(ArtistHolder holder, int position) {
        holder.bind(mArtistProvider.getArtist(position));
    }

    @Override
    public int getItemCount() {
        return mArtistProvider.getArtistCount();
    }

    public static class ArtistHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Picasso mPicasso;
        private Artist mArtist;
        private WeakReference<ArtistListFragment.Callbacks> mCallbacks;

        @BindView(R.id.artist_list_item_name)
        TextView mName;

        @BindView(R.id.artist_list_item_cover)
        ImageView mCover;

        public ArtistHolder(View itemView, Picasso picasso, ArtistListFragment.Callbacks callbacks) {
            super(itemView);
            mPicasso = picasso;
            mCallbacks = new WeakReference<>(callbacks);

            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        public void bind(Artist artist) {
            mArtist = artist;
            mName.setText(mArtist.getName());
            mPicasso.load(mArtist.getUrlOfSmallCover()).into(mCover);
        }

        @Override
        public void onClick(View v) {
            ArtistListFragment.Callbacks callbacks = mCallbacks.get();
            if (callbacks != null) {
                callbacks.onArtistInListSelected(mArtist);
            }
        }
    }
}
