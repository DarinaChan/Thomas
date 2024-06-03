package edu.thomas.service;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import edu.thomas.MainActivity;
import edu.thomas.R;
import edu.thomas.users.Train;
import edu.thomas.users.User;

public class NotificationService extends FirebaseMessagingService {
    public final String TAG = "Logs : " + getClass().getSimpleName();
    List<Train> listUserTrains = new ArrayList<>();
    DatabaseService databaseService = new DatabaseService();

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        if (remoteMessage.getNotification() != null) {
            Uri imageUri = remoteMessage.getNotification().getImageUrl();
            Bitmap image = null;
            if(imageUri != null) {
                String imageUrl = imageUri.toString();
                image = getBitmapFromUrl(imageUrl);

            } else {
                Log.d(TAG, "There is no image to display in the notification.");
            }
            if( remoteMessage.getNotification().getBody() != null){
                System.out.println(remoteMessage.getNotification().getBody().toString());
            }
            if (remoteMessage.getNotification().getChannelId().equals(MainActivity.INCIDENT_CHANNEL_ID)){
                Log.d(TAG,"Nous somme dans un canal d'incidents");
                sendNotifIfUserConcerned(image,remoteMessage);
                return;
            }

            sendNotification(image, remoteMessage);
        } else { // Numéro 0 pour l'instant (on peut les classer en fonction de leurs ids)
            Log.d(TAG, "The notification is empty.");
        }
    }
    public void sendNotifIfUserConcerned(Bitmap image, RemoteMessage remoteMessage){ //Check if the user is on the train the incident is reported at
        databaseService.getMiguel(new FirestoreMiguelCallback() {
            @Override
            public void onMiguelCallback(User user) {
                if (user != null) {
                    System.out.println("here");
                    listUserTrains = user.getTrains();
                    for (Train t : listUserTrains){
                        System.out.println(t.getTrainId() + remoteMessage.getNotification().getBody().toString());
                        if (t.getTrainId().equals(remoteMessage.getNotification().getBody().toString())){
                            System.out.println("done");
                            sendNotification(image,remoteMessage);
                            return;
                        }
                    }
                }
            }
        });
    }
    private void sendNotification(Bitmap image, RemoteMessage remoteMessage) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class); // Create an intent to go to MainActivity
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        // Prepares and sends the notification
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getApplicationContext(), remoteMessage.getNotification().getChannelId().toString())
                .setSmallIcon(R.drawable.ic_train_black_24dp) // Thomas icon
                .setLargeIcon(image) // Notification image
                .setContentTitle(Objects.requireNonNull(remoteMessage.getNotification()).getTitle())
                .setContentText(Objects.requireNonNull(remoteMessage.getNotification().getBody()))
                .setTimeoutAfter(1800000) // 30 minutes
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }

    private Bitmap getBitmapFromUrl(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            return BitmapFactory.decodeStream(url.openConnection().getInputStream());
        } catch (IOException e) {
            Log.e(TAG, "Erreur lors du téléchargement de l'image", e);
            return null;
        }
    }
    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
        Log.d(TAG, "Refreshed token: " + token);
    }

}
