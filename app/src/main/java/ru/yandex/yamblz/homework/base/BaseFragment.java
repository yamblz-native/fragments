package ru.yandex.yamblz.homework.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import ru.yandex.yamblz.R;
import ru.yandex.yamblz.homework.artists.ArtistsActivity;

/**
 * Created by platon on 06.08.2016.
 */
public abstract class BaseFragment extends Fragment
{
    private Unbinder unbinder;

    protected abstract int getLayout();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle bundle)
    {
        return inflater.inflate(getLayout(), container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
    }

    @Override
    public void onDestroyView()
    {
        if (unbinder != null) unbinder.unbind();
        super.onDestroyView();
    }

    protected void updateToolbar(String name, boolean hasBackButton)
    {
        if (name == null) name = getString(R.string.app_name);
        ((ArtistsActivity) getActivity()).setupActionBar(name, hasBackButton);
    }

    protected void replaceFragment(Fragment fragment, boolean toBackStack)
    {
        ((ArtistsActivity) getActivity()).showFragment(fragment, toBackStack);
    }
}
