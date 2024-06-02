package edu.thomas;

import static edu.thomas.IPictureActivity.REQUEST_CAMERA;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.messaging.FirebaseMessaging;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.Date;

import edu.thomas.databinding.ActivityMainBinding;
import edu.thomas.service.DatabaseService;
import edu.thomas.users.Train;
import edu.thomas.users.User;

public class MainActivity extends AppCompatActivity {
    public final String TAG = "Thomas" + getClass().getSimpleName();
    public static final String CHANNEL_ID = "Notification channel";
    private ActivityMainBinding binding;
    private FragmentManager fm;
    private FragmentReport fr;
    DatabaseService databaseService = new DatabaseService();
    public User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_journeys, R.id.navigation_tickets, R.id.navigation_report, R.id.navigation_profile)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
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
        // Add some basic informations in the db
/*
        User basicUser = new User("Miguel", "Rodrigo");
        Train basicTrain = new Train(new Date(),"Rouen","Lyon",databaseService.getIdForTrain());
        currentUser = basicUser;
        currentUser.addTrainToUser(basicTrain);
        currentUser.addTrainToUser(basicTrain);
*/
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