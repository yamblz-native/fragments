package ru.yandex.yamblz.ui.adapters;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.squareup.picasso.Picasso;

import java.util.List;

import ru.yandex.yamblz.R;
import ru.yandex.yamblz.model.Singer;

/**
 * Adapter for given list of performers
 */
public class FirstRecyclerAdapter extends RecyclerAdapter{

    protected PerformerSelectedListener performerSelectedListener;

    /**
     * Will show list of performers in RecycleView
     */
    protected List<Singer> singers;

    /**
     * Creates an instance of FirstRecyclerAdapter on specified list
     * @param s list of perfomers to show in RecycleView
     */
    public FirstRecyclerAdapter(List<Singer> s) {
        singers = s;
    }

    /**
     * Sets specified listener for RecyclerView
     * @param listener given listener
     */
    public void setPerformerSelectedListener (PerformerSelectedListener listener) {
        performerSelectedListener = listener;
    }

    /**
     * Fills single card with performer's information(name, genres, cover)
     * @param holder holder for card(view)
     * @param position position of performer in given list
     */
    @Override
    public void onBindViewHolder(GroupsViewHolder holder, int position) {
        holder.groupName.setText(singers.get(position).getName());
        holder.genres.setText(singers.get(position).getGenres());
        holder.itemView.setTag(R.id.tag, position);
    }

    /**
     * Returns number of performers in list
     * @return number of performers in list
     */
    @Override
    public int getItemCount() {
        if (singers == null) return 0;
        return singers.size();
    }

    /**
     * Calls listener's method handling clicks on views
     * @param v view on which clicked
     */
    @Override
    public void onClick(View v) {
        Log.w("Adapter", "OnClick");
        int singer = (int) v.getTag(R.id.tag);
        if (performerSelectedListener != null) {
            performerSelectedListener.onPerformerSelected(singer);
        }
    }

}
