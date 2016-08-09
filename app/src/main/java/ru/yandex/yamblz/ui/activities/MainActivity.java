package ru.yandex.yamblz.ui.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.ButterKnife;
import ru.yandex.yamblz.App;
import ru.yandex.yamblz.R;
import ru.yandex.yamblz.developer_settings.DeveloperSettingsModule;
import ru.yandex.yamblz.singerscontracts.Singer;
import ru.yandex.yamblz.ui.fragments.DescFragment;
import ru.yandex.yamblz.ui.fragments.ListFragment;
import ru.yandex.yamblz.ui.fragments.PreviewFragment;
import ru.yandex.yamblz.ui.fragments.TabsFragment;
import ru.yandex.yamblz.ui.other.ViewModifier;

public class MainActivity extends BaseActivity implements PreviewFragment.Callbacks, ListFragment.Callbacks {

    @Inject @Named(DeveloperSettingsModule.MAIN_ACTIVITY_VIEW_MODIFIER)
    ViewModifier viewModifier;

    private boolean mPhone;

    @SuppressLint("InflateParams") // It's okay in our case.
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.get(this).applicationComponent().inject(this);

        setContentView(viewModifier.modify(getLayoutInflater().inflate(R.layout.activity_main, null)));

        ButterKnife.bind(this);

        mPhone = findViewById(R.id.preview_fragment) == null;

        if(mPhone && savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, TabsFragment.newInstance())
                    .commit();
        }
    }


    @Override
    public void onMoreChosen(Singer singer) {
        if(mPhone) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, DescFragment.newInstance(singer))
                    .addToBackStack(null)
                    .commit();
        } else {
            DescFragment descFragment = DescFragment.newInstance(singer);
            descFragment.show(getSupportFragmentManager(), null);
        }
    }

    @Override
    public void onSingerChosen(Singer singer) {
        PreviewFragment previewFragment = (PreviewFragment)getSupportFragmentManager()
                .findFragmentById(R.id.preview_fragment);
        previewFragment.setSinger(singer);
    }

    @Override
    public void onSingersShown(@NonNull List<Singer> singers) {
        PreviewFragment previewFragment = (PreviewFragment)getSupportFragmentManager()
                .findFragmentById(R.id.preview_fragment);
        if(!previewFragment.hasSinger()) {
            previewFragment.setSinger(singers.get(0));
        }
    }
}
