package ru.yandex.yamblz.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.yandex.yamblz.R;
import ru.yandex.yamblz.ui.adapters.ArtistRecyclerAdapter;
import ru.yandex.yamblz.ui.adapters.ArtistsPagerAdapter;

public class ContentFragment extends BaseFragment {
    private ArtistFragment artistFragment;

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_content, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(getResources().getBoolean(R.bool.is_tablet)){
            RecyclerView recyclerView= (RecyclerView) view.findViewById(R.id.recycler);
            recyclerView.setAdapter(new ArtistRecyclerAdapter(position -> {
                artistFragment.update(position);
            }));
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            if(savedInstanceState==null){
                artistFragment=ArtistFragmentBuilder.newArtistFragment(0);
                getChildFragmentManager().beginTransaction().replace(R.id.container,artistFragment,"artist")
                .commit();
            }else{
                artistFragment= (ArtistFragment) getChildFragmentManager().findFragmentByTag("artist");
            }


        }else{
            ViewPager viewPager = (ViewPager) view.findViewById(R.id.view_pager);
            viewPager.setAdapter(new ArtistsPagerAdapter(getChildFragmentManager()));
            TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tab_layout);
            // tabLayout.setTabTextColors(Color.BLACK, Color.BLUE);
            tabLayout.setupWithViewPager(viewPager);
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        artistFragment=null;
    }

    public static interface OnItemClicked{
        void itemClicked(int position);
    }
}
