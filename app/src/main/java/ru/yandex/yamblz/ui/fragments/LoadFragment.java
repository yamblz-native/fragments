package ru.yandex.yamblz.ui.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ru.yandex.yamblz.R;
import ru.yandex.yamblz.artists.DataSingleton;

public class LoadFragment extends Fragment {
    @BindView(R.id.btn_load) Button buttonLoad;
    private Unbinder unbinder;
    private DataSingleton.LoadFromProviderTask loadFromProviderTask;
    private ProgressDialog dialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_load,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        dialog=new ProgressDialog(getContext());
        dialog.setCancelable(false);
        buttonLoad.setOnClickListener(v -> {
            loadFromProviderTask = DataSingleton.get().loadFromContentProvider(getContext(), () -> {
                dialog.hide();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, new ContentFragment()).commit();
            });
            dialog.show();
            loadFromProviderTask.execute();
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        dialog=null;
    }

    @Override
    public void onPause() {
        super.onPause();
        if(loadFromProviderTask!=null){
            dialog.cancel();
            loadFromProviderTask.cancel(true);
        }
    }
}
