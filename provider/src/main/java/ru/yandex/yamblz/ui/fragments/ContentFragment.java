package ru.yandex.yamblz.ui.fragments;

import android.content.SharedPreferences;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.OnClick;
import ru.yandex.yamblz.R;
import ru.yandex.yamblz.provider.MyContentProvider;

public class ContentFragment extends BaseFragment {

    private static final String URI_ARTIST = "content://ru.yandex.yamblz/artists";
    SharedPreferences sharedPreferences;

    @BindView(R.id.textview)
    TextView textView;
    @BindView(R.id.progress)
    ProgressBar progressBar;

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_content, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (sharedPreferences != null) {
            textView.setText(sharedPreferences.getString("date", null));
        }

        getContext().getContentResolver().registerContentObserver(Uri.parse(URI_ARTIST), true, new ContentObserver(new Handler()) {
            @Override
            public void onChange(boolean selfChange) {
                super.onChange(selfChange);
                progressBar.setVisibility(View.GONE);
                SimpleDateFormat dfDate_day = new SimpleDateFormat("dd.MM yyyy HH:mm:ss");
                Calendar c = Calendar.getInstance();
                sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                String str = "Данные обновлены : " + dfDate_day.format(c.getTime());
                sharedPreferences.edit().putString("date", str);
                textView.setText(str);
            }
        });

    }

    @OnClick(R.id.refresh_btn)
    void OnClickBtnRefresh() {
        ((MyContentProvider) getActivity()
                .getContentResolver()
                .acquireContentProviderClient(Uri.parse(URI_ARTIST))
                .getLocalContentProvider())
                .saveArtistsInDataBase();
        progressBar.setVisibility(View.VISIBLE);
        textView.setText("Обновляются");
    }
}
