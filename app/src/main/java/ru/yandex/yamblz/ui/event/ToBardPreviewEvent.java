package ru.yandex.yamblz.ui.event;

import com.google.auto.value.AutoValue;

import ru.yandex.yamblz.ui.fragments.BardPreviewFragment;

/**
 * Created by Александр on 10.08.2016.
 */
@AutoValue
public abstract class ToBardPreviewEvent {
    public abstract BardPreviewFragment bardPreviewFragment();

    public static ToBardPreviewEvent create(BardPreviewFragment bardPreviewFragment){
        return new AutoValue_ToBardPreviewEvent(bardPreviewFragment);
    }
}
