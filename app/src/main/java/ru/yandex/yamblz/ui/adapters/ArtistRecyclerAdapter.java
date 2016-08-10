package ru.yandex.yamblz.ui.adapters;


import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.yandex.yamblz.R;
import ru.yandex.yamblz.lib.ContentProviderContract;
import ru.yandex.yamblz.ui.fragments.ContentFragment;

public class ArtistRecyclerAdapter extends RecyclerView.Adapter<ArtistRecyclerAdapter.MyViewHolder> {
    private Cursor cursor;
    private ContentFragment.OnItemClicked onItemClicked;

    public ArtistRecyclerAdapter(ContentFragment.OnItemClicked onItemClicked,Cursor cursor) {
        this.onItemClicked = onItemClicked;
        this.cursor = cursor;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.artist_item, parent, false),onItemClicked);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        cursor.moveToPosition(position);
        holder.bind(cursor);
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.text_view) TextView textView;
        @BindView(R.id.image_view) ImageView imageView;
        private String name;

        MyViewHolder(View itemView, ContentFragment.OnItemClicked onItemClicked) {
            super(itemView);
            itemView.setOnClickListener(v ->
                    onItemClicked.itemClicked(name));
            ButterKnife.bind(this,itemView);
        }

        void bind(Cursor cursor) {
            name=cursor.getString(cursor.getColumnIndex(ContentProviderContract.Artists.NAME));
            String imageBig=cursor.getString(cursor.getColumnIndex(ContentProviderContract.Artists.IMAGE_BIG));
            Picasso.with(imageView.getContext()).load(imageBig).into(imageView);
            textView.setText(name);
        }

    }
}
