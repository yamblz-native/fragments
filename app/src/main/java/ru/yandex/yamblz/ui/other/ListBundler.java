package ru.yandex.yamblz.ui.other;

import android.os.Bundle;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

import icepick.Bundler;
import ru.yandex.yamblz.domain.model.presentation.BardUI;

/**
 * Created by Александр on 10.08.2016.
 */

public class ListBundler implements Bundler<List<BardUI>> {
    @Override
    public void put(String s, List<BardUI> data, Bundle bundle) {
        if (data != null){
            bundle.putParcelableArrayList(s, new ArrayList<>(data));
        }
    }

    @Override
    public List<BardUI> get(String s, Bundle bundle) {
        return bundle.getParcelableArrayList(s);
    }
}
