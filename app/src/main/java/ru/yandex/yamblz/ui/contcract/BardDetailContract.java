package ru.yandex.yamblz.ui.contcract;

import ru.yandex.yamblz.domain.model.core.Bard;
import ru.yandex.yamblz.domain.model.presentation.BardUI;

/**
 * Created by Александр on 08.08.2016.
 */

public final class BardDetailContract extends BaseContract {

    public interface BardDetailView extends MvpView{
        void showBard(BardUI bard);
    }

    public interface BardsDetailPresenter extends MvpPresenter<BardDetailView>{
    }
}
