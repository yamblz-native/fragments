package ru.yandex.yamblz.ui.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import javax.inject.Inject;
import javax.inject.Named;

import ru.yandex.yamblz.App;
import ru.yandex.yamblz.R;
import ru.yandex.yamblz.developer_settings.DeveloperSettingsModule;
import ru.yandex.yamblz.ui.event.ToBardDetailsEvent;
import ru.yandex.yamblz.ui.event.ToBardPreviewEvent;
import ru.yandex.yamblz.ui.fragments.BardDetailFragment;
import ru.yandex.yamblz.ui.fragments.BardListFragment;
import ru.yandex.yamblz.ui.fragments.BardPreviewFragment;
import ru.yandex.yamblz.ui.fragments.BardViewPagerFragment;
import ru.yandex.yamblz.ui.fragments.ContentFragment;
import ru.yandex.yamblz.ui.other.ViewModifier;

public class MainActivity extends BaseActivity {

    @Inject @Named(DeveloperSettingsModule.MAIN_ACTIVITY_VIEW_MODIFIER)
    ViewModifier viewModifier;
    @Inject
    EventBus eventBus;

    boolean isTwoPane = false;

    @SuppressLint("InflateParams") // It's okay in our case.
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.get(this).applicationComponent().inject(this);
        isTwoPane = getResources().getBoolean(R.bool.is_two_pane);
        setContentView(viewModifier.modify(getLayoutInflater().inflate(R.layout.activity_main, null)));

        if(savedInstanceState == null){
            if(isTwoPane){
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_frame_layout, new BardListFragment())
                        .commit();
            }else {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_frame_layout, new BardViewPagerFragment())
                        .commit();
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        eventBus.register(this);
    }

    @Override
    protected void onStop() {
        eventBus.unregister(this);
        super.onStop();
    }

    @Subscribe
    public void onEvent(ToBardDetailsEvent event){
        if(isTwoPane){
            event.bardDetailFragment().show(getSupportFragmentManager().beginTransaction().addToBackStack(null), BardDetailFragment.TAG);
        }else {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_frame_layout, event.bardDetailFragment(), BardDetailFragment.TAG)
                    .addToBackStack(null)
                    .commit();
        }
    }

    @Subscribe
    public void onEvent(ToBardPreviewEvent event){
        if(isTwoPane){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detail_frame_layout, event.bardPreviewFragment(), BardPreviewFragment.TAG)
                    .commit();
        }
    }
}
