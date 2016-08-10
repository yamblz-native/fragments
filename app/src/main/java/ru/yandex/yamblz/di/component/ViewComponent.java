package ru.yandex.yamblz.di.component;

import dagger.Subcomponent;
import ru.yandex.yamblz.di.module.FragmentArgumentModule;
import ru.yandex.yamblz.di.module.PresenterModule;
import ru.yandex.yamblz.ui.fragments.BardDetailFragment;
import ru.yandex.yamblz.ui.fragments.BardListFragment;
import ru.yandex.yamblz.ui.fragments.BardViewPagerFragment;

/**
 * Created by Александр on 08.08.2016.
 */
@Subcomponent(modules = {
        FragmentArgumentModule.class,
        PresenterModule.class
})
public interface ViewComponent {
    void inject(BardDetailFragment bardFragment);
    void inject(BardListFragment bardListFragment);

    void inject(BardViewPagerFragment bardViewPagerFragment);
}
