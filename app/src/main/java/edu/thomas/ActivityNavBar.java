package edu.thomas;

import static edu.thomas.R.id.frame_fragment_container;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ActivityNavBar extends AppCompatActivity implements BottomNavigationView.OnItemSelectedListener {
    private int currentSelectedItemId;
    private BottomNavigationView bottomNavigationView;
    private FragmentManager fm;
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_nav_bar);
        bottomNavigationView = findViewById(R.id.nav_view);

        fm = getSupportFragmentManager();

        currentSelectedItemId = bottomNavigationView.getMenu().getItem(0).getItemId();
        bottomNavigationView.setOnItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int clickedItemId = menuItem.getItemId();
        if (clickedItemId == R.id.navigation_train) {
            return true;
        }  else if (clickedItemId == R.id.navigation_ticket) {
            return true;
        } else if (clickedItemId == R.id.navigation_report) {
            fm.beginTransaction().replace(frame_fragment_container, new FragmentIncidentReporting()).commit();
            return true;
        } else if (clickedItemId == R.id.navigation_profile) {
            return true;
        }
        return false;
    }
}
