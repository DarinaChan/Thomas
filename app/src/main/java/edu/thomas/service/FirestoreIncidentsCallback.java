package edu.thomas.service;

import java.util.List;

import edu.thomas.model.incident.Incident;

public interface FirestoreIncidentsCallback {
    void onIncidentsCallback(List<Incident> incidentList);
}
