package ru.yandex.yamblz.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.hannesdorfmann.fragmentargs.annotation.Arg;
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import ru.yandex.yamblz.ApplicationComponent;
import ru.yandex.yamblz.R;
import ru.yandex.yamblz.domain.model.core.Bard;
import ru.yandex.yamblz.domain.model.presentation.BardUI;
import ru.yandex.yamblz.ui.contcract.BardDetailContract;
import ru.yandex.yamblz.ui.event.ToBardDetailsEvent;

/**
 * Created by Александр on 10.08.2016.
 */
@FragmentWithArgs
public class BardPreviewFragment extends BaseFragment {
    public static final String TAG = "BardPreviewFragment";

    @Arg
    BardUI bard;

    @Inject
    EventBus eventBus;

    @BindView(R.id.sdv_bard_photo)
    protected SimpleDraweeView sdvPhoto;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_preview_bard, container, false);
    }

    @Override
    protected void setUpComponent(ApplicationComponent applicationComponent) {
        eventBus = applicationComponent.eventBus();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sdvPhoto.setImageURI(bard.bigImage());
    }

    public static BardPreviewFragment newInstance(BardUI bard){
        return BardPreviewFragmentBuilder.newBardPreviewFragment(bard);
    }

    @OnClick(R.id.btn_more)
    public void onClick(View v){
        eventBus.post(ToBardDetailsEvent.create(BardDetailFragment.newInstance(bard.id())));
    }
}
