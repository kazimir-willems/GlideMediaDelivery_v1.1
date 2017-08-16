package delivery.com.ui;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import butterknife.Bind;
import butterknife.ButterKnife;
import delivery.com.R;
import delivery.com.application.DeliveryApplication;
import delivery.com.consts.StateConsts;
import delivery.com.fragment.DespatchFragment;
import delivery.com.fragment.HomeFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Bind(R.id.toolbar)
    Toolbar toolBar;
    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @Bind(R.id.nav_view)
    NavigationView navigationView;

    private ActionBarDrawerToggle toggle;

    public ProgressDialog dlgProg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        setSupportActionBar(toolBar);

        toggle = new ActionBarDrawerToggle(MainActivity.this, drawerLayout, toolBar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close) {

            public void onDrawerOpened(View drawerView) {
            }
        };

        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(MainActivity.this);
        navigationView.getMenu().getItem(0).setChecked(true);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        FragmentManager manager = getFragmentManager();
        if(DeliveryApplication.nAccess == StateConsts.USER_ADMIN) {
            manager.beginTransaction()
                    .replace(R.id.main_frame, HomeFragment.newInstance())
                    .commit();
        } else {
            manager.beginTransaction()
                    .replace(R.id.main_frame, DespatchFragment.newInstance())
                    .commit();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int selectedIndex = -1;

        Fragment fragment = null;
        switch (item.getItemId()) {
            case R.id.nav_home:
                fragment = HomeFragment.newInstance();
                getSupportActionBar().setTitle(R.string.app_name);
                break;
            case R.id.nav_despatch:
                fragment = DespatchFragment.newInstance();
                getSupportActionBar().setTitle(R.string.title_despatch_fragment);
                break;
            default:
                break;
        }

        invalidateOptionsMenu();

        if (fragment != null) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.main_frame, fragment)
                    .commit();
        }

        drawerLayout.closeDrawers();

        return true;
    }

    public void showDespatchFragment() {
        Fragment fragment = null;
        fragment = DespatchFragment.newInstance();

        if (fragment != null) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.main_frame, fragment)
                    .commit();
        }
    }

}