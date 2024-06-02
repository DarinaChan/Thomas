package edu.thomas;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import static edu.thomas.IPictureActivity.REQUEST_CAMERA;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.firebase.messaging.FirebaseMessaging;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import edu.thomas.databinding.ActivityMainBinding;
import edu.thomas.service.DatabaseService;
import edu.thomas.users.Train;
import edu.thomas.users.User;

public class MainActivity extends AppCompatActivity {
    private final int REQUEST_CALENDAR_PERMISSION = 1;
    public final String TAG = "Thomas" + getClass().getSimpleName();
    public static final String CHANNEL_ID = "Notification channel";
    private ActivityMainBinding binding;
    DatabaseService databaseService = new DatabaseService();
    public User currentUser;
    NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestCalendarPermission();
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        edu.thomas.databinding.ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_journeys, R.id.navigation_tickets, R.id.navigation_report, R.id.navigation_profile)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        // Notifications
        String channelName = "Notification channel";
        NotificationChannel channel;
        // Create channel
        channel = new NotificationChannel(CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_DEFAULT);
        // Ask user if we can send notifications
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);

        // Get the token
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task -> {
            if(!task.isSuccessful()) {
                Log.d(TAG, "Failed to obtain the token : " + task.getResult());
            } else {
                Log.d(TAG, "Token = " + task.getResult());
            }
        });

//         Add some basic informations in the db
//        User basicUser = new User("Miguel", "Rodrigo");
//        Train basicTrain = new Train(new Date(),"Rouen","Lyon",databaseService.getIdForTrain());
//        currentUser = basicUser;
//        currentUser.addTrainToUser(basicTrain);
//        currentUser.addTrainToUser(basicTrain);
//
//        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task -> {
//            if(!task.isSuccessful()) {
//                Log.d(TAG, "Failed to obtain the token : " + task.getResult());
//            } else {
//                Log.d(TAG, "Token = " + task.getResult());
//            }
//        });

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CAMERA: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast toast = Toast.makeText(getApplicationContext(), "CAMERA authorization granted", Toast.LENGTH_LONG);
                    toast.show();
                    getFragmentReport().takePicture();
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), "CAMERA authorization not granted", Toast.LENGTH_LONG);
                    toast.show();
                }
            }
            case REQUEST_CALENDAR_PERMISSION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission granted
                    Toast.makeText(this, "Calendar permission granted", Toast.LENGTH_SHORT).show();
                } else {
                    // Permission denied
                    Toast.makeText(this, "Calendar permission is required to add events", Toast.LENGTH_SHORT).show();
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
                getFragmentReport().setImage((Bitmap) Objects.requireNonNull(data.getExtras()).get("data"));
            }
        }
    }

    private void requestCalendarPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_CALENDAR, Manifest.permission.READ_CALENDAR},
                    REQUEST_CALENDAR_PERMISSION);
        }
    }

    private FragmentReport getFragmentReport() {
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_activity_main);
        if(navHostFragment == null) {
            throw new IllegalStateException("Cannot find navHostFragment");
        }
        List<Fragment> fragments = navHostFragment.getChildFragmentManager().getFragments();
        return (FragmentReport) fragments.get(0);
    }
}