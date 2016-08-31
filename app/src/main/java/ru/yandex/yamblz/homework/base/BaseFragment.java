package ru.yandex.yamblz.homework.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import ru.yandex.yamblz.homework.artists.ArtistsActivity;
import ru.yandex.yamblz.homework.artists.interfaces.FragmentTransactionManager;
import ru.yandex.yamblz.homework.artists.interfaces.ToolbarProvider;

/**
 * Created by platon on 06.08.2016.
 */
public abstract class BaseFragment extends Fragment
{
    private Unbinder unbinder;
    private ToolbarProvider toolbarProvider;
    private FragmentTransactionManager fragmentTransactionManager;

    protected abstract int getLayout();

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        if (getActivity() instanceof ArtistsActivity)
        {
            toolbarProvider = (ToolbarProvider) getActivity();
            fragmentTransactionManager = (FragmentTransactionManager) getActivity();
        }
    }

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

    @Override
    public void onDetach()
    {
        toolbarProvider = null;
        fragmentTransactionManager = null;
        super.onDetach();
    }

    protected FragmentTransactionManager getFragmentTransactionManager()
    {
        return fragmentTransactionManager;
    }

    protected ToolbarProvider getToolbarProvider()
    {
        return toolbarProvider;
    }
}
