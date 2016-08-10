package ru.yandex.yamblz.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.hannesdorfmann.fragmentargs.FragmentArgs;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import ru.yandex.yamblz.App;
import ru.yandex.yamblz.ApplicationComponent;
import ru.yandex.yamblz.ui.contcract.BaseContract;
import rx.Observable;
import rx.subjects.BehaviorSubject;
import rx.subjects.PublishSubject;
import timber.log.Timber;

@SuppressWarnings("PMD.AbstractClassWithoutAnyMethod")
public abstract class BaseFragment extends Fragment
        implements BaseContract.MvpView {

    private Handler mainThreadHandler;
    private Unbinder viewBinder;

    private final PublishSubject<Boolean> clickNetworkProblemSubject = PublishSubject.create();

    @CallSuper
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpComponent(App.get(getContext()).applicationComponent());
        FragmentArgs.inject(this);
    }

    protected void setUpComponent(ApplicationComponent applicationComponent){
        
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainThreadHandler = App.get(context).applicationComponent().mainThreadHandler();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewBinder = ButterKnife.bind(this, view);
    }

    protected void runOnUiThreadIfFragmentAlive(@NonNull Runnable runnable) {
        if (Looper.myLooper() == Looper.getMainLooper() && isFragmentAlive()) {
            runnable.run();
        } else {
            assert mainThreadHandler != null;
            mainThreadHandler.post(() -> {
                if (isFragmentAlive()) {
                    runnable.run();
                }
            });
        }
    }

    private boolean isFragmentAlive() {
        return getActivity() != null && isAdded() && !isDetached() && getView() != null && !isRemoving();
    }

    @Override
    public void onDestroyView() {
        if (viewBinder != null) {
            viewBinder.unbind();
        }
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        App.get(getContext()).applicationComponent().leakCanaryProxy().watch(this);
        super.onDestroy();
    }


    @Override
    public void showLoading(boolean isLoading) {
        Timber.d("showLoading: isloading %b", isLoading);
    }
}
