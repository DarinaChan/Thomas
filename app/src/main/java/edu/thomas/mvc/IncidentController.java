package edu.thomas.mvc;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.MediaStore;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import edu.thomas.IPictureActivity;
import edu.thomas.model.incident.TypeOfIncident;

public class IncidentController {
    private IncidentView view;
    private IncidentModel model;
    private final Context context;
    private final Activity activity;

    public IncidentController(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
    }

    public void userActionClickCamera() {
        if(ContextCompat.checkSelfPermission(context, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(activity, new String[] {android.Manifest.permission.CAMERA}, IPictureActivity.REQUEST_CAMERA);
        }
        else { // Permission still granted
            takePicture();
        }
    }

    public void userActionClickGallery() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_DENIED) {
                ActivityCompat.requestPermissions(activity, new String[]{android.Manifest.permission.READ_MEDIA_IMAGES}, IPictureActivity.REQUEST_GALLERY);
            } else { // Permission still granted
                openGallery();
            }
        } else {
            if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                ActivityCompat.requestPermissions(activity, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, IPictureActivity.REQUEST_GALLERY);
            } else { // Permission still granted
                openGallery();
            }
        }
    }

    public void userActionSelectTrainSpinner(int position) {
        model.setTrainSpinnerPosition(position);
    }

    public void userActionSelectIncidentSpinner(int position) {
        model.setIncidentSpinnerPosition(position);
        if (position == TypeOfIncident.TrainOnTime.ordinal() + 1) { // Un train n'est jamais à l'heure
            view.easterEgg();
        }
    }

    public void userActionClickAddIncident() {
        model.sendIncident();
    }

    public void takePicture() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        activity.startActivityForResult(intent, IPictureActivity.REQUEST_CAMERA);
    }

    public void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        activity.startActivityForResult(intent, IPictureActivity.REQUEST_GALLERY);
    }

    public Context getContext() {
        return this.context;
    }

    public void setModel(IncidentModel model) {
        this.model = model;
    }

    public void setView(IncidentView view) {
        this.view = view;
    }
}
