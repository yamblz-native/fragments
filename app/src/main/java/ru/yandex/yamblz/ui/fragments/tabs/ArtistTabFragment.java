package ru.yandex.yamblz.ui.fragments.tabs;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.hannesdorfmann.fragmentargs.FragmentArgs;
import com.hannesdorfmann.fragmentargs.annotation.Arg;
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs;

import butterknife.BindBool;
import butterknife.BindView;
import butterknife.OnClick;
import ru.yandex.yamblz.R;
import ru.yandex.yamblz.model.Artist;
import ru.yandex.yamblz.ui.fragments.BaseFragment;
import ru.yandex.yamblz.utils.ImageLoader;

@FragmentWithArgs
public class ArtistTabFragment extends BaseFragment {
    @BindView(R.id.tab_image)
    ImageView artistImage;
    @BindBool(R.bool.is_tablet) boolean isTablet;

    @Arg
    Artist artist;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentArgs.inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tab, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageLoader.getInstance().loadImageCropped(getContext(), artistImage, artist.cover.big);
    }

    @OnClick(R.id.tab_more_button)
    public void onMoreButtonClicked() {
        InfoDialogFragment infoFragment = new InfoDialogFragmentBuilder(artist).build();
        if (!isTablet) {
            getParentFragment().getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, infoFragment)
                    .addToBackStack(null)
                    .commit();

        } else {
            infoFragment.show(getFragmentManager(), "dialog");
        }
    }
}
