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

public class IncidentController implements IController {
    private final IView view;
    private final IModel model;
    private final Context context;
    private final Activity activity;

    public IncidentController(IView view, IModel model, Context context, Activity activity) {
        this.view = view;
        this.model = model;
        this.context = context;
        this.activity = activity;
    }

    @Override
    public void userActionClickCamera() {
        if(ContextCompat.checkSelfPermission(context, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(activity, new String[] {android.Manifest.permission.CAMERA}, IPictureActivity.REQUEST_CAMERA);
        }
        else { // Permission still granted
            takePicture();
        }
    }

    @Override
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

    public void takePicture() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        activity.startActivityForResult(intent, IPictureActivity.REQUEST_CAMERA);
    }

    public void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        activity.startActivityForResult(intent, IPictureActivity.REQUEST_GALLERY);
    }
}
