package edu.thomas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class FragmentReport extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_report2, container, false);

        Spinner spinner = rootView.findViewById(R.id.incident_spinner);

        List<String> incidents = new ArrayList<>();
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

}