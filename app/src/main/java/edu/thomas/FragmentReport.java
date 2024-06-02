package edu.thomas;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.thomas.model.incident.Incident;
import edu.thomas.model.incident.IncidentFactory;
import edu.thomas.model.incident.TypeOfIncident;
import edu.thomas.service.DatabaseService;
import edu.thomas.service.FirestoreCallback;
import edu.thomas.users.Train;
import edu.thomas.users.User;

public class FragmentReport extends Fragment {
    DatabaseService db = new DatabaseService();
    int spinnerPosition = 0;
    String incident_desc;

    ImageView imageView;
    IncidentFactory facto = new IncidentFactory();
    String trainId = "";
    List<Train> trains;
    int trainSpinnerPosition = 0;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View rootView = inflater.inflate(R.layout.fragment_report, container, false);
        imageView = rootView.findViewById(R.id.photo);
        Spinner spinner = rootView.findViewById(R.id.incident_spinner);
        ImageButton cameraButton = rootView.findViewById(R.id.camera_button);
        cameraButton.setOnClickListener(view -> {
            if(ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
                ActivityCompat.requestPermissions(getActivity(), new String[] {android.Manifest.permission.CAMERA}, IPictureActivity.REQUEST_CAMERA);
            }
            else { // Permission still granted
                takePicture();
            }
        });

        Spinner trainSpinner = rootView.findViewById(R.id.train_spinner);
        fetchTrainNames(trainSpinner);

        List<String> incidents = new ArrayList<>();
        incidents.add("-- Selectionnez une catégorie --");
        for (TypeOfIncident ty : TypeOfIncident.values()){
            incidents.add(ty.getDescription());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, incidents);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        MaterialButton addIncidentButton = rootView.findViewById(R.id.addIncident);

        addIncidentButton.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextInputEditText incidentDesc = rootView.findViewById(R.id.incident_desc);
                incident_desc = incidentDesc.getText().toString();
                if ((spinnerPosition != 0) && (trainSpinnerPosition !=0)) {
                    sendIncident(); // Send the incident to the db
                    showPopup("Incident envoyé avec succès !");
                    incidentDesc.setText(""); //Reset the text
                    spinner.setSelection(0); // Reset the spinner
                    trainSpinner.setSelection(0);
                    addTrainToUser();
                }
                else{
                    showPopup(spinnerPosition != 0 ?"Veuillez choisir une catégorie":"Veuillez choisir le trajet correspondant");
                }
            }
        });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                spinnerPosition = position; //get the position of the spinner
                if (spinnerPosition == TypeOfIncident.TrainOnTime.ordinal() + 1) { // Un train n'est jamais à l'heure
                    showPopup("Un train n'est jamais à l'heure !");
                    spinner.setSelection(0); //Reset the spinner
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing
            }
        });
        trainSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                trainSpinnerPosition = position;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing
            }
        });


        return rootView;
    }
    private void showPopup(String text) {
        Toast.makeText(getContext(), text, Toast.LENGTH_LONG).show();
    }
    public void sendIncident(){
        System.out.println(FirebaseDatabase.getInstance());
        Incident incident = facto.createIncident(trains.get(trainSpinnerPosition - 1).getTrainId(),spinnerPosition,incident_desc).get();
        System.out.println(incident);
        db.addIncident(incident);
    }

    public void takePicture() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        getActivity().startActivityForResult(intent, IPictureActivity.REQUEST_CAMERA);
    }

    public void setImage(Bitmap bitmap) {
        imageView.setImageBitmap(bitmap);
    }
    public void fetchTrainNames(Spinner trainSpinner) {
        db.getMiguel(new FirestoreCallback() {
            @Override
            public void onMiguelCallback(User user) {
                if (user != null) {
                    trains = user.getTrains();
                    List<String> trainNames = new ArrayList<>();
                    trainNames.add("--Sélectionnez un trajet--");
                    for (Train t : trains) {
                        trainNames.add(t.getTrainName());
                    }
                    updateUIWithTrainNames(trainNames,trainSpinner);
                }
            }
        });
    }
    public void addTrainToUser() {
        db.getMiguel(new FirestoreCallback() {
            @Override
            public void onMiguelCallback(User user) {
                if (user != null) {
                    Train basicTrain = new Train(new Date(), new Date(),"Prout","Marseille",db.getIdForTrain());
                    user.addTrainToUser(basicTrain);
                    db.addTrain(basicTrain);
                    db.deleteAndReAddUser(user);
                }
            }
        });
    }

    public void updateUIWithTrainNames(List<String> trainNames, Spinner trainSpinner) {
        ArrayAdapter<String> trainAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, trainNames);
        trainAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        trainSpinner.setAdapter(trainAdapter);
    }


}