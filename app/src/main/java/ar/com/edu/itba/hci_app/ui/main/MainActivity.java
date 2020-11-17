package ar.com.edu.itba.hci_app.ui.main;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;
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
    private LinkedList<String> tags;
    private LinkedList<Fragment> fragments;

    private final BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment = null;
            String c = null;
            //navego por la nav_bar con los ids
            switch (item.getItemId()) {
                case R.id.nav_home:
                    fragment = HomeFragment.getHomeFragment();
                    c = "A";
                    break;
                case R.id.nav_statistics:
                    fragment = StatisticsFragment.getStatisticsFragment();
                    c = "B";
                    break;
                case R.id.nav_search:
                    fragment = SearchFragment.getSearchFragment();
                    c = "C";
                    break;
                case R.id.nav_notifications:
                    fragment = NotificationsFragment.getNotificationsFragment();
                    c = "D";
                    break;
                case R.id.nav_profile:
                    fragment = ProfileFragment.getProfileFragment();
                    c = "E";
                    break;
            }

//            if (fragment == null || c == null) {
//                throw new NullPointerException("Bottom navigation bar");
//            }
//
//            if (tags.contains(c) && fragments.contains(fragment)) {
//                tags.remove(c);
//                fragments.remove(fragment);
//            }
//            tags.push(c);
//            fragments.push(fragment);
            if (fragment == null) {
                throw new NullPointerException("Bottom navigation bar");
            }

            if ( fragments.contains(fragment)) {
                fragments.remove(fragment);
            }
            fragments.push(fragment);
            if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
                getSupportFragmentManager().popBackStack();
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
            //cambio fragments y ejecuto
            //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_nav_bar);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);

        //asi se conserva el fragmento al rotar la pantalla, etc
        if (savedInstanceState == null) {
//            tags = new LinkedList<>();
//            tags.push("A");
            fragments = new LinkedList<>();
            fragments.push(HomeFragment.getHomeFragment());
            if (fragments.peek() == null) {
                throw new NullPointerException("Creating main activity");
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragments.peek()).commit();

//            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragments.peek()).addToBackStack(null).commit();
        }
    }

//    private void changeChecked(String tag, Boolean value) {
//        switch (tag) {
//            case "A":
//                bottomNavigationView.getMenu().getItem(0).setChecked(value);
//                break;
//            case "B":
//                bottomNavigationView.getMenu().getItem(1).setChecked(value);
//                break;
//            case "C":
//                bottomNavigationView.getMenu().getItem(2).setChecked(value);
//                break;
//            case "D":
//                bottomNavigationView.getMenu().getItem(3).setChecked(value);
//                break;
//            case "E":
//                bottomNavigationView.getMenu().getItem(4).setChecked(value);
//                break;
//        }
//    }

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

        if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
            getSupportFragmentManager().popBackStack();
            return;
        }


//        int count = getSupportFragmentManager().getBackStackEntryCount();
        int count = fragments.size();
        //aca vuelve para atras, hay que decirle que destruya el dispatcher
        if (count <= 1) {
            finish();
            onDestroy();
        } else {
//            getSupportFragmentManager().popBackStack();
            change(fragments.pop(), false);
//            String tag = tags.pop();
//            changeChecked(tag, false);
//            changeChecked(tag, true);
            change(fragments.peek(), true);
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragments.peek()).commit();
    }

    @Override
    protected void onDestroy() {
        //android.os.Process.killProcess(android.os.Process.myPid());
        super.onDestroy();
    }
}