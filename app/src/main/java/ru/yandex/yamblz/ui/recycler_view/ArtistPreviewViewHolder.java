package ru.yandex.yamblz.ui.recycler_view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.yandex.yamblz.R;
import ru.yandex.yamblz.model.Artist;

/**
 * Created by Aleksandra on 06/08/16.
 */
public class ArtistPreviewViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.artist_small_cover)
    ImageView cover;

    @BindView(R.id.preview_artist_name)
    TextView artistName;

    public ArtistPreviewViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(Artist artist) {
        artistName.setText(artist.name());

        Picasso.with(cover.getContext())
                .load(artist.cover().smallCover())
                .into(cover);
    }
}
