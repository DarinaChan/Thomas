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
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import edu.thomas.MainActivity;
import edu.thomas.R;

public class NotificationService extends FirebaseMessagingService {
    public final String TAG = "Logs : " + getClass().getSimpleName();

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        if (remoteMessage.getNotification() != null) {
            // Notification image processing
            Uri imageUri = remoteMessage.getNotification().getImageUrl();
            Bitmap image = null;
            if(imageUri != null) {
                // If the notification contains an image, retrieve the image URL
                String imageUrl = imageUri.toString();
                image = getBitmapFromUrl(imageUrl);
            } else {
                Log.d(TAG, "There is no image to display in the notification.");
            }
            // Create and send the notification
            sendNotification(image, remoteMessage);
        } else {
            Log.d(TAG, "The notification is empty.");
        }
    }

    private void sendNotification(Bitmap image, RemoteMessage remoteMessage) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class); // Create an intent to go to MainActivity
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        // Prepares and sends the notification
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getApplicationContext(), MainActivity.CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_train_black_24dp) // Thomas icon
                .setLargeIcon(image) // Notification image
                .setContentTitle(Objects.requireNonNull(remoteMessage.getNotification()).getTitle())
                .setContentText(Objects.requireNonNull(remoteMessage.getNotification().getBody()))
                .setTimeoutAfter(1800000) // 30 minutes
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
        Log.d(TAG, "Refreshed token: " + token);
    }

    private Bitmap getBitmapFromUrl(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            return BitmapFactory.decodeStream(url.openConnection().getInputStream());
        } catch (IOException e) {
            Log.e(TAG, "Error while downloading the image", e);
            return null;
        }
    }
}
