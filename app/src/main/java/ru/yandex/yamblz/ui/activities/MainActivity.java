package ru.yandex.yamblz.ui.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import ru.yandex.yamblz.App;
import ru.yandex.yamblz.R;
import ru.yandex.yamblz.developer_settings.DeveloperSettingsModule;
import ru.yandex.yamblz.model.Artist;
import ru.yandex.yamblz.ui.fragments.ArtistDetailsFragment;
import ru.yandex.yamblz.ui.fragments.ArtistImageFragment;
import ru.yandex.yamblz.ui.other.ArtistsContainer;
import ru.yandex.yamblz.ui.fragments.ArtistsListFragment;
import ru.yandex.yamblz.ui.fragments.ContentFragment;
import ru.yandex.yamblz.ui.other.ShowDetailsCallback;
import ru.yandex.yamblz.ui.other.ShowImageCallback;
import ru.yandex.yamblz.ui.other.ViewModifier;

public class MainActivity extends BaseActivity implements ShowDetailsCallback, ArtistsContainer, ShowImageCallback {

    private static final String TAG = "MainActivity";

    @Inject
    @Named(DeveloperSettingsModule.MAIN_ACTIVITY_VIEW_MODIFIER)
    ViewModifier viewModifier;

    private FrameLayout flSideMenu;
    private ContentFragment contentFragment;
    private FragmentManager fm;
    private List<Artist> artists;
    private boolean isBigscreen;

    @SuppressLint("InflateParams") // It's okay in our case.
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.get(this).applicationComponent().inject(this);
        //TODO вынести в другой поток. может в лоадер
        artists = downloadArtists();

        setContentView(viewModifier.modify(getLayoutInflater().inflate(R.layout.activity_main, null)));
        flSideMenu = (FrameLayout) findViewById(R.id.flSideMenu);
        fm = getSupportFragmentManager();
        isBigscreen = flSideMenu != null;
        if (savedInstanceState == null) {
            Bundle args = new Bundle();
            if (isBigscreen) {
                showListAndDetailsFragments();
                args.putBoolean(ContentFragment.EXTRA_ARTISTS_LIST, false);
            } else {
                showViewPagerFragment();
            }
        }
    }

    @Override
    public void showArtistDetails(Artist artist) {
        ArtistDetailsFragment fragment = new ArtistDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ArtistDetailsFragment.EXTRA_ARTIST, artist);
        fragment.setArguments(bundle);
        if(isBigscreen){
            fragment.show(fm, "dialog");
        }else{
            fm.beginTransaction()
                    .addToBackStack(null)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .hide(contentFragment)
                    .add(R.id.main_frame_layout, fragment)
                    .commit();
        }
    }

    @Override
    public List<Artist> getArtists() {
        return artists;
    }

    @Override
    public void showImage(Artist artist) {
        showArtistImageFragment(artist);
    }

    private List<Artist> downloadArtists(){
        ObjectMapper mapper = new ObjectMapper();
        InputStream jsonInputStream = getResources().openRawResource(R.raw.artists);
        ArrayList<Artist> artists = new ArrayList<>();

        try {
            artists = mapper.readValue(jsonInputStream, new TypeReference<List<Artist>>() {});
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close(jsonInputStream);
        }
        return artists;
    }

    private void close(InputStream is) {
        if (is != null) {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void showArtistImageFragment() {
        showArtistImageFragment(artists.get(0));
    }

    private void showArtistImageFragment(Artist artist){
        ArtistImageFragment fragment = new ArtistImageFragment();
        Bundle args = new Bundle();
        args.putParcelable(ArtistImageFragment.EXTRA_ARTIST, artist);
        fragment.setArguments(args);

        fm.beginTransaction()
                .replace(R.id.main_frame_layout, fragment)
                .commit();
    }

    private void showViewPagerFragment() {
        contentFragment = new ContentFragment();
        fm.beginTransaction()
                .replace(R.id.main_frame_layout, contentFragment)
                .commit();
    }

    private void showListAndDetailsFragments() {
        fm.beginTransaction()
                .replace(R.id.flSideMenu, new ArtistsListFragment())
                .commit();

        showArtistImageFragment();
    }
}
