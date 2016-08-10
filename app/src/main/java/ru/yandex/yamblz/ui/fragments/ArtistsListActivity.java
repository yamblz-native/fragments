package ru.yandex.yamblz.ui.fragments;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;




import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ru.yandex.yamblz.Model.Artist;
import ru.yandex.yamblz.MyJsonParser;
import ru.yandex.yamblz.R;
import ru.yandex.yamblz.database.ArtistDataSource;
import ru.yandex.yamblz.ui.adapters.ArtistAdapter;

/**
 * Created by danil on 25.04.16.
 */
public class ArtistsListActivity extends Fragment{
    private DownloadTask downloadTask;
    private RecyclerView mRecyclerView;
    private ArtistAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public List<Artist> artists;
    private ArtistDataSource dataSource = null;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        artists = new ArrayList<>();
        LinearLayout linearLayout = (LinearLayout) inflater.inflate(R.layout.activity_artist_list, container, false);

        mRecyclerView = (RecyclerView) linearLayout.findViewById(R.id.my_recycler_view);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new ArtistAdapter(getActivity());


        dataSource = new ArtistDataSource(getActivity());
        dataSource.open();
        List<Artist> tmp = dataSource.getAllArtists();
        if (tmp.size() == 0) {
            Log.i("DOWNLOADDATBEACHES", "YEAY");
            downloadTask = new DownloadTask(this);
            downloadTask.execute();
        } else {
            Log.i("ADDDATBEACHES", "WOOP");
            artists.addAll(tmp);
            mAdapter.setItems(artists);
        }



        if (savedInstanceState == null) {
            dataSource.open();
            artists.addAll(dataSource.getAllArtists());
            dataSource.close();
        } else {
            artists = (List<Artist>) savedInstanceState.getSerializable("listArtist");
            mAdapter.setItems(artists);
        }



        mRecyclerView.setAdapter(mAdapter);
        return linearLayout;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("listArtist", (Serializable) artists);
    }


    public Object onRetainCustomNonConfigurationInstance() {
        return downloadTask;
    }

    public void onClick(View view) {

    }

    private enum Result {
        INPROGRESS, OK, NOARTIST, ERROR
    }









    private class DownloadTask extends AsyncTask<Void, Void, Result> {

        private ArtistsListActivity activity = null;
        private Artist artist = null;
        private Result result = Result.INPROGRESS;

        public DownloadTask(ArtistsListActivity activity) {
            this.activity = activity;
        }

        public void attachActivity(ArtistsListActivity activity) {
            this.activity = activity;
            publishProgress();
        }

        @Override
        protected Result doInBackground(Void... params) {

            dataSource = new ArtistDataSource(getActivity());
            dataSource.open();
            Log.i("fxf", "Task started");
            try {

                MyJsonParser parser = new MyJsonParser();

                List<Artist> list = parser.parse();
                Log.i("zaza", "Artists parsed " + list.size());
                if (list.size() == 317) {
                    for (int i = 0; i < list.size(); i++) {
                        dataSource.createArtist(String.valueOf(list.get(i).getId()), list.get(i).getDescription(), list.get(i).getName(),
                                list.get(i).getCover().getSmallCoverImage(), list.get(i).getCover().getBigCoverImage(), String.valueOf(list.get(i).getTracks()),
                                String.valueOf(list.get(i).getAlbums()), list.get(i).getGenres());
                        Log.i("bd", "Artists write in bd " + i);
                    }
                    dataSource.close();
                }
                if (list == null) {
                    result = Result.ERROR;
                    return result;
                } else if (list.size() == 0) {
                    result = Result.NOARTIST;
                    return result;
                }
                artists.addAll(list);


            } catch (Exception e) {
                return Result.ERROR;
            }
            result = Result.OK;
            return result;
        }

        @Override
        protected void onPostExecute(Result res) {
            result = res;

            updateUI();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            updateUI();
        }

        private void updateUI() {
            if (result == Result.OK) {
                mAdapter.setItems(artists);
            }
        }

    }


}
