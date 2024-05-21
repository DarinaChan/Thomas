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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PackageManagerCompat;
import androidx.fragment.app.Fragment;

import java.security.Permission;
import java.util.ArrayList;
import java.util.List;

public class FragmentReport extends Fragment {
    ImageView imageView;

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

        List<String> incidents = new ArrayList<>();
        incidents.add("Sélectionner une catégorie...");
        incidents.add("Pick pocket");
        incidents.add("Passager dangereux");
        incidents.add("Train en retard");
        incidents.add("Train à l heure");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, incidents);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (incidents.get(position).equals("Train à l heure")) { // Un train n'est jamais à l'heure
                    showPopup();
                    spinner.setSelection(0);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing
            }
        });


        return rootView;
    }
    private void showPopup() {
        Toast.makeText(getContext(), "Un train n'est jamais à l'heure !", Toast.LENGTH_LONG).show();
    }

    public void takePicture() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        getActivity().startActivityForResult(intent, IPictureActivity.REQUEST_CAMERA);
    }

    public void setImage(Bitmap bitmap) {
        imageView.setImageBitmap(bitmap);
    }
}