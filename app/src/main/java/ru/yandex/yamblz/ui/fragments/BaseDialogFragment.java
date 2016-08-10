package ru.yandex.yamblz.ui.fragments;

import android.support.v4.app.DialogFragment;

import ru.yandex.yamblz.App;
import ru.yandex.yamblz.ApplicationComponent;

public class BaseDialogFragment extends DialogFragment {

    protected ApplicationComponent getAppComponent() {
        return App.get(getContext()).applicationComponent();
    }

}
