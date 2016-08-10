package ru.yandex.yamblz.di.module;

import dagger.Module;
import dagger.Provides;
import ru.yandex.yamblz.ui.presenters.BardDetailPresenter;

/**
 * Created by Александр on 07.08.2016.
 */
@Module
public class FragmentArgumentModule {
    private BardDetailPresenter.BardDetailPresenterArgument bardDetailPresenterArgument;

    private FragmentArgumentModule() {
    }

    @Provides
    BardDetailPresenter.BardDetailPresenterArgument bardDetailPresenterArgument(){
        return bardDetailPresenterArgument;
    }

    public static FragmentArgumentModule empty(){
        return new FragmentArgumentModule();
    }


    public static FragmentArgumentModule bardDetailPresenterArgument(long idBard){
        FragmentArgumentModule fragmentArgumentModule = new FragmentArgumentModule();
        fragmentArgumentModule.bardDetailPresenterArgument = new BardDetailPresenter.BardDetailPresenterArgument(idBard);
        return fragmentArgumentModule;
    }
}
