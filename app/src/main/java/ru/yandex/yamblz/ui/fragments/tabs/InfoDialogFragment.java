package ru.yandex.yamblz.ui.fragments.tabs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hannesdorfmann.fragmentargs.FragmentArgs;
import com.hannesdorfmann.fragmentargs.annotation.Arg;
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ru.yandex.yamblz.R;
import ru.yandex.yamblz.model.Artist;
import ru.yandex.yamblz.utils.ImageLoader;

import static ru.yandex.yamblz.utils.Utils.convertToString;


@FragmentWithArgs
public class InfoDialogFragment extends DialogFragment {
    @Arg
    @NonNull // Студия подчеркивает, что not initialized. Как бороться?
            Artist artist;
    Unbinder unbinder;

    @BindView(R.id.info_cover)
    ImageView cover;
    @BindView(R.id.info_genres)
    TextView genres;
    @BindView(R.id.info_music)
    TextView infoMusic;
    @BindView(R.id.info_bio)
    TextView bio;


    int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
    int padding = (int) (Resources.getSystem().getDisplayMetrics().density * 32);


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentArgs.inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragment = inflater.inflate(R.layout.fragment_artist_page, container, false);
        unbinder = ButterKnife.bind(this, fragment);

        return fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setup();

    }

    public void onResume() {
        super.onResume();
        Dialog dialog = getDialog();
        if (dialog != null) {
            Window window = getDialog().getWindow();
            assert window != null;
            window.setLayout(screenWidth - 2 * padding, screenHeight - 2 * padding);
            window.setGravity(Gravity.CENTER);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void setup() {
        ImageLoader.getInstance().loadImageCropped(getContext(), cover, artist.cover.big);
        genres.setText(convertToString(artist.genres, ','));
        // Надо заюзать plurals
        infoMusic.setText(String.format(Locale.getDefault(), getResources().getString(R.string.music_info), artist.albums, artist.tracks));
        bio.setText(artist.description);

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View fragment = LayoutInflater.from(getContext()).inflate(R.layout.fragment_artist_page, null); // T___T Как здесь по-нормальному сделать-то?
        unbinder = ButterKnife.bind(this, fragment);
        setup();
        ((LinearLayout.LayoutParams) cover.getLayoutParams()).height = screenHeight / 2;

        return new AlertDialog.Builder(getActivity())
                .setTitle(artist.name)
                .setView(fragment)
                .setPositiveButton(R.string.alert_dialog_ok,
                        (dialog, whichButton) -> dismiss()
                )
                .create();
    }
}
