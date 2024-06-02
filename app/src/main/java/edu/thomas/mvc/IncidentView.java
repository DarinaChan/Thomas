package edu.thomas.mvc;

import android.content.pm.PackageManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.Observable;
import java.util.Observer;

import edu.thomas.IPictureActivity;
import edu.thomas.R;

public class IncidentView implements IView, Observer {
    private IncidentController controller;
    private IncidentModel model;
    private final View layout;
    ImageView imageView;

    public IncidentView(View layout) {
        this.layout = layout;
        imageView = layout.findViewById(R.id.photo);
        Spinner incidentSpinner = layout.findViewById(R.id.incident_spinner);
        Spinner trainSpinner = layout.findViewById(R.id.train_spinner);

        layout.findViewById(R.id.camera_button).setOnClickListener(view -> {
            controller.userActionClickCamera();
        });

        layout.findViewById(R.id.gallery_button).setOnClickListener(view -> {
            controller.userActionClickGallery();
        });
    }

    public void setController(IncidentController IController) {
        this.controller = IController;
    }

    public void setModel(IncidentModel model) {
        this.model = model;
    }

    @Override
    public void update(Observable observable, Object o) {
        imageView.setImageBitmap(model.getBitmap());
    }
}
