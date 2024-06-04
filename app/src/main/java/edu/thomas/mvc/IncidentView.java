package edu.thomas.mvc;

import android.app.ProgressDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import edu.thomas.R;

public class IncidentView implements Observer {
    private final IncidentController controller;
    private final IncidentModel model;
    private ProgressDialog progressDialog;
    private final ImageView imageView;
    private final TextInputEditText description;
    private final Spinner incidentSpinner;
    private final Spinner trainSpinner;

    public IncidentView(View layout, IncidentController controller, IncidentModel model) {
        this.model = model;
        this.controller = controller;
        imageView = layout.findViewById(R.id.photo);
        incidentSpinner = layout.findViewById(R.id.incident_spinner);
        trainSpinner = layout.findViewById(R.id.train_spinner);
        description = layout.findViewById(R.id.incident_desc);

        ArrayAdapter<String> incidentAdapter = new ArrayAdapter<>(controller.getContext(), android.R.layout.simple_spinner_item, model.getIncidentsNames());
        incidentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        incidentSpinner.setAdapter(incidentAdapter);

        layout.findViewById(R.id.camera_button).setOnClickListener(view -> {
            controller.userActionClickCamera();
        });

        layout.findViewById(R.id.gallery_button).setOnClickListener(view -> {
            controller.userActionClickGallery();
        });

        trainSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                controller.userActionSelectTrainSpinner(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing
            }
        });

        incidentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                controller.userActionSelectIncidentSpinner(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing
            }
        });

        layout.findViewById(R.id.addIncident).setOnClickListener(v -> {
            controller.userActionClickAddIncident();
        });
    }

    private void showProgressDialog() {
        progressDialog = new ProgressDialog(controller.getContext());
        progressDialog.setMessage("Loading...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    private void dismissProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    public void easterEgg() {
        showPopup("Un train n'est jamais à l'heure !");
        incidentSpinner.setSelection(0);
    }

    private void showPopup(String text) {
        Toast.makeText(controller.getContext(), text, Toast.LENGTH_LONG).show();
    }

    private void updateUIWithTrainNames(List<String> trainNames) {
        ArrayAdapter<String> trainAdapter = new ArrayAdapter<>(controller.getContext(), android.R.layout.simple_spinner_item, trainNames);
        trainAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        trainSpinner.setAdapter(trainAdapter);
    }

    private void incidentSent() {
        trainSpinner.setSelection(model.getTrainSpinnerPosition());
        incidentSpinner.setSelection(model.getIncidentSpinnerPosition());
        description.setText(model.getDescription());
        imageView.setImageBitmap(model.getBitmap());
        showPopup("Incident envoyé avec succès !");
    }

    @Override
    public void update(Observable observable, Object arg) {
        if (arg instanceof IncidentModel.UpdateType) {
            IncidentModel.UpdateType updateType = (IncidentModel.UpdateType) arg;
            switch (updateType) {
                case DESCRIPTION_CHANGE:
                    description.setText(model.getDescription());
                    break;
                case BITMAP_UPDATED:
                    imageView.setImageBitmap(model.getBitmap());
                    break;
                case SHOW_PROCESS_DIALOG:
                    showProgressDialog();
                    break;
                case TRAINS_LOADED:
                    dismissProgressDialog();
                    updateUIWithTrainNames(model.getTrainNames());
                    break;
                case INCIDENT_SENT:
                    incidentSent();
                    break;
                case MISSING_ROUTE:
                    showPopup("Veuillez sélectionner un trajet");
                    break;
                case MISSING_CATEGORY:
                    showPopup("Veuillez sélectionner une catégorie");
                    break;
            }
        }
    }
}
