package ru.yandex.yamblz.ui.contcract;

import android.support.annotation.NonNull;

/**
 * Created by Александр on 08.08.2016.
 */

public class BaseContract {
    public interface MvpView{
        void showLoading(boolean isLoading);
    }

    public interface MvpPresenter<View extends MvpView>{
        void bindView(@NonNull View view);
        void unbindView(@NonNull View view);
        View view();
    }
}
