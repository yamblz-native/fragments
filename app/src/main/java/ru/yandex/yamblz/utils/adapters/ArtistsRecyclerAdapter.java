package ru.yandex.yamblz.utils.adapters;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.yandex.yamblz.R;
import ru.yandex.yamblz.model.Artist;
import ru.yandex.yamblz.ui.fragments.ArtistClickHandler;
import ru.yandex.yamblz.ui.fragments.tabs.ArtistsTabsFragment;
import ru.yandex.yamblz.utils.ImageLoader;
import ru.yandex.yamblz.utils.Utils;

/**
 * Created by aleien on 24.04.16.
 * Адаптер для отображения списка исполнителей.
 */
public class ArtistsRecyclerAdapter extends RecyclerView.Adapter<ArtistsRecyclerAdapter.ArtistHolder> {

    @NonNull private final List<Artist> artists;
    @Nullable private WeakReference<ArtistClickHandler> clickHandler;

    public ArtistsRecyclerAdapter(@NonNull List<Artist> artists, ArtistClickHandler clickHandler) {
        this.artists = artists;
        this.clickHandler = new WeakReference<>(clickHandler);
    }

    @Override
    public ArtistHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_artist, parent, false);
        return new ArtistHolder(view);
    }

    @Override
    public void onBindViewHolder(ArtistHolder holder, int position) {
        Artist artist = artists.get(position);
        ImageLoader.getInstance().loadImageCropped(holder.cover.getContext(), holder.cover, Uri.parse(artist.cover.small));
        holder.name.setText(artist.name);
        holder.genres.setText(Utils.convertToString(artist.genres, ','));
        holder.musicInfo.setText(String.format(Locale.getDefault(),
                holder.musicInfo.getResources().getString(R.string.item_music_info),
                artist.albums,
                artist.tracks));
        holder.container.setOnClickListener(l -> performClick(artist));
    }

    private void performClick(Artist artist) {
        if (clickHandler != null && clickHandler.get() != null) {
            clickHandler.get().artistClicked(artist);
        }
    }

    @Override
    public int getItemCount() {
        return artists.size();
    }

    public void setClickHandler(@Nullable ArtistClickHandler clickHandler) {
        this.clickHandler = new WeakReference<>(clickHandler);
    }

    static class ArtistHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_container)
        RelativeLayout container;
        @BindView(R.id.cover)
        ImageView cover;
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.genres)
        TextView genres;
        @BindView(R.id.music_info)
        TextView musicInfo;

        public ArtistHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
