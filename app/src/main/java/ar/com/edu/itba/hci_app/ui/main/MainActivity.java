package ar.com.edu.itba.hci_app.ui.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;


import android.os.Bundle;

import android.util.Log;
import android.view.MenuItem;


import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.LinkedList;

import ar.com.edu.itba.hci_app.R;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private static LinkedList<String> tags;

    private final BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment = null;

            //navego por la nav_bar con los ids
            switch (item.getItemId()) {
                case R.id.nav_home:
                    fragment = HomeFragment.getHomeFragment();
                    tags.push("A");
                    break;
                case R.id.nav_statistics:
                    fragment = StatisticsFragment.getStatisticsFragment();
                    tags.push("B");
                    break;
                case R.id.nav_search:
                    fragment = SearchFragment.getSearchFragment();
                    tags.push("C");
                    break;
                case R.id.nav_notifications:
                    fragment = NotificationsFragment.getNotificationsFragment();
                    tags.push("D");
                    break;
                case R.id.nav_profile:
                    fragment = ProfileFragment.getProfileFragment();
                    tags.push("E");
                    break;
            }

            //cambio fragments y ejecuto
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        secondActivityBinding = SecondActivityBinding.inflate(getLayoutInflater());
//        secondActivityBinding.bottomNavBar.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        bottomNavigationView = findViewById(R.id.bottom_nav_bar);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        tags = new LinkedList<>();

        getSupportActionBar().setLogo(R.drawable.ic_baseline_home_24);

        //asi se conserva el fragmento al rotar la pantalla, etc
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    HomeFragment.getHomeFragment()).addToBackStack(null).commit();
            tags.push("A");
        }
    }

    private void changeChecked(String tag, Boolean value) {
        switch (tag) {
            case "A":
                bottomNavigationView.getMenu().getItem(0).setChecked(value);
                break;
            case "B":
                bottomNavigationView.getMenu().getItem(1).setChecked(value);
                break;
            case "C":
                bottomNavigationView.getMenu().getItem(2).setChecked(value);
                break;
            case "D":
                bottomNavigationView.getMenu().getItem(3).setChecked(value);
                break;
            case "E":
                bottomNavigationView.getMenu().getItem(4).setChecked(value);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();

        //aca vuelve para atras, hay que decirle que destruya el dispatcher
        if (count == 1) {
            finish();
            onDestroy();
        } else {
            getSupportFragmentManager().popBackStack();
            String tag = tags.pop();
            changeChecked(tag, false);
            if (tags.size() <= 0) {
                changeChecked(tag, true);
                return;
            }
            changeChecked(tags.peek(), true);
        }
    }

    @Override
    protected void onDestroy() {
        android.os.Process.killProcess(android.os.Process.myPid());
        super.onDestroy();
    }
}