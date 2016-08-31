package ru.yandex.yamblz.homework.artists;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.yandex.yamblz.R;
import ru.yandex.yamblz.homework.artists.interfaces.FragmentTransactionManager;
import ru.yandex.yamblz.homework.artists.interfaces.ToolbarProvider;

public class ArtistsActivity extends AppCompatActivity implements ToolbarProvider, FragmentTransactionManager
{
    @BindView(R.id.main_toolbar) Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artists);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        boolean twoPanel = getResources().getBoolean(R.bool.two_panel);

        if (savedInstanceState == null)
        {
            if (!twoPanel) addFragmentWithoutBackStack(ViewPagerFragment.newInstance());
        }
    }

    @Override
    public void addFragment(Fragment fragment)
    {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.content_fragment, fragment)
                .addToBackStack(null)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
    }

    @Override
    public void addFragmentWithoutBackStack(Fragment fragment)
    {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.content_fragment, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
    }

    @Override
    public void replaceFragment(Fragment fragment)
    {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_fragment, fragment)
                .addToBackStack(null)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
    }

    @Override
    public void replaceFragmentWithoutBackStack(Fragment fragment)
    {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_fragment, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
    }

    @Override
    public void removeFragment(Fragment fragment)
    {
        getSupportFragmentManager().beginTransaction()
                .remove(fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                .commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void updateToolbar(String title, boolean hasBackButton)
    {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(title);
        actionBar.setDisplayHomeAsUpEnabled(hasBackButton);
    }

    @Override
    public void updateToolbar(String title)
    {
        updateToolbar(title, false);
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        updateToolbar(getString(R.string.app_name));
    }
}
