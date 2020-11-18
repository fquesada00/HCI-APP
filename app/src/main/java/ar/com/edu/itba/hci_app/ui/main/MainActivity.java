package ar.com.edu.itba.hci_app.ui.main;

import androidx.annotation.NonNull;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;


import android.os.Bundle;


import android.util.Log;
import android.view.MenuItem;

import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.LinkedList;

import ar.com.edu.itba.hci_app.R;
import ar.com.edu.itba.hci_app.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private LinkedList<Fragment> fragments;

    private final BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment = null;
            //navego por la nav_bar con los ids
            switch (item.getItemId()) {
                case R.id.nav_home:
                    fragment = HomeFragment.getHomeFragment();
                    break;
                case R.id.nav_statistics:
                    fragment = StatisticsFragment.getStatisticsFragment();
                    break;
                case R.id.nav_search:
                    fragment = SearchFragment.getSearchFragment();
                    break;
                case R.id.nav_notifications:
                    fragment = NotificationsFragment.getNotificationsFragment();
                    break;
                case R.id.nav_profile:
                    fragment = ProfileFragment.getProfileFragment();
                    break;
            }
            if (fragment == null) {
                throw new NullPointerException("Bottom navigation bar");
            }

            if (fragments.contains(fragment)) {
                fragments.remove(fragment);
            }

            fragments.push(fragment);

            while (getSupportFragmentManager().getBackStackEntryCount() >= 1){
                getSupportFragmentManager().popBackStackImmediate();
            }

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        getSupportActionBar().setLogo(R.drawable.app_logo);
//        getSupportActionBar().setDisplayUseLogoEnabled(true);
//        getSupportActionBar().setTitle("FitBo");

//        this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
//        getSupportActionBar().setDisplayShowCustomEnabled(true);
//        getSupportActionBar().setCustomView(R.layout.toolbar_top);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

//        getSupportActionBar().setElevation(0);

        bottomNavigationView = findViewById(R.id.bottom_nav_bar);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);

        //asi se conserva el fragmento al rotar la pantalla, etc
        if (savedInstanceState == null) {
            fragments = new LinkedList<>();
            fragments.push(HomeFragment.getHomeFragment());
            if (fragments.peek() == null) {
                throw new NullPointerException("Creating main activity");
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragments.peek()).commit();
        }
    }


    private void change(Fragment fragment, Boolean value) {
        if (fragment.getClass().toString().equals(HomeFragment.class.toString())) {
            bottomNavigationView.getMenu().getItem(0).setChecked(value);
        } else if (fragment.getClass().toString().equals(StatisticsFragment.class.toString())) {
            bottomNavigationView.getMenu().getItem(1).setChecked(value);
        } else if (fragment.getClass().toString().equals(SearchFragment.class.toString())) {
            bottomNavigationView.getMenu().getItem(2).setChecked(value);
        } else if (fragment.getClass().toString().equals(NotificationsFragment.class.toString())) {
            bottomNavigationView.getMenu().getItem(3).setChecked(value);
        } else if (fragment.getClass().toString().equals(ProfileFragment.class.toString())) {
            bottomNavigationView.getMenu().getItem(4).setChecked(value);
        }
        else {
            throw new IllegalArgumentException("Wrong class in change");
        }
    }

    @Override
    public void onBackPressed() {

        if (getSupportFragmentManager().getBackStackEntryCount() >= 1) {
                getSupportFragmentManager().popBackStack();
            return;
        }

        int count = fragments.size();
        //aca vuelve para atras, hay que decirle que destruya el dispatcher
        if (count == 1 && !fragments.peek().getClass().toString().equals(HomeFragment.class.toString())) {
            change(fragments.pop(), false);
            fragments.push(HomeFragment.getHomeFragment());
            change(fragments.peek(), true);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragments.peek()).commit();
        }
        else if (count == 1){
            fragments.pop();
            finish();
        }
        else {
            change(fragments.pop(), false);
            change(fragments.peek(), true);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragments.peek()).commit();
        }
    }
}