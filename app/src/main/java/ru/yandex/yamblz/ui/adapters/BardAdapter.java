package ru.yandex.yamblz.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.yandex.yamblz.R;
import ru.yandex.yamblz.domain.model.core.Bard;
import ru.yandex.yamblz.domain.model.presentation.BardUI;
import rx.Observable;
import rx.subjects.PublishSubject;

/**
 * Created by Александр on 07.08.2016.
 */

public class BardAdapter extends RecyclerView.Adapter<BardAdapter.BardViewHolder> {

    private List<BardUI> dataSet = new ArrayList<>();
    private final PublishSubject<BardUI> clicks = PublishSubject.create();

    @Override
    public BardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BardViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_bard, parent, false));
    }

    @Override
    public void onBindViewHolder(BardViewHolder holder, int position) {
        holder.bind();
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public Observable<BardUI> clicks(){
        return clicks.asObservable();
    }

    public void setData(List<BardUI> data){
        dataSet = data;
        notifyDataSetChanged();
    }

    class BardViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.sdv_bard_photo)
        SimpleDraweeView sdvPoster;
        @BindView(R.id.tv_bard_name)
        TextView tvName;
        @BindView(R.id.tv_bard_genres)
        TextView tvGenres;
        @BindView(R.id.tv_bard_albums)
        TextView tvAlbums;
        @BindView(R.id.tv_bard_song) TextView tvSongs;


        public BardViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(v -> onItemClick());
        }

        public void bind(){
            BardUI bardUI = dataSet.get(getAdapterPosition());
            sdvPoster.setImageURI(bardUI.smallImage());
            tvName.setText(bardUI.name());
            tvGenres.setText(TextUtils.join(", ", bardUI.genres()));
            tvAlbums.setText(String.format(itemView.getResources().getQuantityString(R.plurals.plur_albums, bardUI.albums()), bardUI.albums()));
            tvSongs.setText(String.format(itemView.getResources().getQuantityString(R.plurals.plur_song, bardUI.tracks()), bardUI.tracks()));
        }

        public void onItemClick(){
            clicks.onNext(dataSet.get(getAdapterPosition()));
        }
    }
}
