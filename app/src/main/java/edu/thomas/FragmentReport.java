package edu.thomas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import edu.thomas.mvc.IncidentController;
import edu.thomas.mvc.IncidentModel;
import edu.thomas.mvc.IncidentView;
import edu.thomas.service.DatabaseService;

public class FragmentReport extends Fragment {
    IncidentView view;
    IncidentModel model;
    IncidentController controller;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View rootView = inflater.inflate(R.layout.fragment_report, container, false);

        model = new IncidentModel(new DatabaseService());
        controller = new IncidentController(getContext(), getActivity());
        view = new IncidentView(rootView, controller, model);
        controller.setView(view);
        controller.setModel(model);
        model.addObserver(view);

        return rootView;
    }

    public IncidentController getController() {
        return this.controller;
    }

    public IncidentModel getModel() {
        return this.model;
    }
}