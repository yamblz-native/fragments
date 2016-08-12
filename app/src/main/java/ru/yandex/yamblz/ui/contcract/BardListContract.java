package ru.yandex.yamblz.ui.contcract;

import java.util.List;

import ru.yandex.yamblz.domain.model.core.Bard;
import ru.yandex.yamblz.domain.model.presentation.BardUI;
import ru.yandex.yamblz.ui.fragments.BaseFragment;
import rx.Observable;

/**
 * Created by Александр on 08.08.2016.
 */

public final class BardListContract extends BaseContract {

    public interface BardListView extends MvpView {
        void showData(List<BardUI> data);
    }

    public interface BardListPresenter extends MvpPresenter<BardListView>{}
}
