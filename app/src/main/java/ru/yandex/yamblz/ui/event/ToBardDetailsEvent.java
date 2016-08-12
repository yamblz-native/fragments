package ru.yandex.yamblz.ui.event;

import com.google.auto.value.AutoValue;

import ru.yandex.yamblz.ui.fragments.BardDetailFragment;

/**
 * Created by Александр on 10.08.2016.
 */
@AutoValue
public abstract class ToBardDetailsEvent {
    public abstract BardDetailFragment bardDetailFragment();

    public static ToBardDetailsEvent create(BardDetailFragment bardDetailFragment){
        return new AutoValue_ToBardDetailsEvent(bardDetailFragment);
    }
}
