package edu.thomas;

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

public class NotificationService extends FirebaseMessagingService {
    public final String TAG = "Logs : " + getClass().getSimpleName();

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        if (remoteMessage.getNotification() != null) {
            Uri imageUri = remoteMessage.getNotification().getImageUrl();
            String imageUrl = "";
            if(imageUri != null) {
                Log.d(TAG, "L'image est : " + imageUri.toString());
                // Récupère l'URL de l'image
                imageUrl = remoteMessage.getNotification().getImageUrl().toString();
            } else {
                Log.d(TAG, "L'image est null.");
            }
            // Télécharge l'image à partir de l'URL
            Bitmap image = getBitmapFromUrl(imageUrl);
            Intent intent = new Intent(getApplicationContext(), MainActivity.class); // Créé un intent pour aller sur MainActivity
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            // Send notification prépare et envoie la notification
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getApplicationContext(), MainActivity.CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_train_black_24dp)
                    .setLargeIcon(image)
                    .setContentTitle(remoteMessage.getNotification().getTitle())
                    .setContentText(remoteMessage.getNotification().getBody())
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
        } else { // Numéro 0 pour l'instant (on peut les classer en fonction de leurs ids)
            Log.d(TAG, "Le message est null.");
        }
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
}
