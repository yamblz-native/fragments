package ru.yandex.yamblz.ui.adapters;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.yandex.yamblz.R;
import ru.yandex.yamblz.lib.ArtistModel;
import ru.yandex.yamblz.ui.fragments.ContentFragment;

public class ArtistRecyclerAdapter extends RecyclerView.Adapter<ArtistRecyclerAdapter.MyViewHolder> {
    private List<ArtistModel> artistModels;
    private ContentFragment.OnItemClicked onItemClicked;

    public ArtistRecyclerAdapter(ContentFragment.OnItemClicked onItemClicked) {
        this.onItemClicked = onItemClicked;
        artistModels = null;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.artist_item, parent, false),onItemClicked);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.bind(artistModels.get(position));
    }

    @Override
    public int getItemCount() {
        return artistModels.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.text_view) TextView textView;
        @BindView(R.id.image_view) ImageView imageView;

        MyViewHolder(View itemView, ContentFragment.OnItemClicked onItemClicked) {
            super(itemView);
            //itemView.setOnClickListener(v -> onItemClicked.itemClicked();
            ButterKnife.bind(this,itemView);
        }

        void bind(ArtistModel artistModel) {
            Picasso.with(imageView.getContext()).load(artistModel.getBigImage()).into(imageView);
            textView.setText(artistModel.getName());
        }

    }
}
