package edu.thomas;

import static edu.thomas.IPictureActivity.REQUEST_CAMERA;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ActivityNavBar extends AppCompatActivity implements BottomNavigationView.OnItemSelectedListener {
    private FragmentManager fm;
    private FragmentReport fr;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_nav_bar);
        BottomNavigationView bottomNavigationView = findViewById(R.id.nav_view);

        fm = getSupportFragmentManager();

        int currentSelectedItemId = bottomNavigationView.getMenu().getItem(0).getItemId();
        bottomNavigationView.setOnItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int clickedItemId = menuItem.getItemId();
        if (clickedItemId == R.id.navigation_journeys) {
            fm.beginTransaction().replace(frame_fragment_container, new FragmentJourneys()).commit();
            return true;
        }  else if (clickedItemId == R.id.navigation_tickets) {
            fm.beginTransaction().replace(frame_fragment_container, new FragmentTickets()).commit();
            return true;
        } else if (clickedItemId == R.id.navigation_report) {
            fr = new FragmentReport();
            fm.beginTransaction().replace(frame_fragment_container, fr).commit();
            return true;
        } else if (clickedItemId == R.id.navigation_profile) {
            fm.beginTransaction().replace(frame_fragment_container, new FragmentProfile()).commit();
            return true;
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CAMERA: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast toast = Toast.makeText(getApplicationContext(), "CAMERA authorization granted", Toast.LENGTH_LONG);
                    toast.show();
                    fr.takePicture();
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), "CAMERA authorization not granted", Toast.LENGTH_LONG);
                    toast.show();
                }
            }
            break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CAMERA) {
            if(resultCode == RESULT_OK) {
                fr.setImage((Bitmap) data.getExtras().get("data"));
            }
        }
    }
}
