package edu.thomas.mvc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Observable;
import android.graphics.Bitmap;


import edu.thomas.model.incident.Incident;
import edu.thomas.model.incident.IncidentFactory;
import edu.thomas.model.incident.TypeOfIncident;
import edu.thomas.service.DatabaseService;
import edu.thomas.users.Train;

public class IncidentModel extends Observable implements IModel {
    private final DatabaseService databaseService;
    private final IncidentFactory incidentFactory = new IncidentFactory();
    private Bitmap bitmap;
    private String description = "";
    private int trainSpinnerPosition = 0;
    private int incidentSpinnerPosition = 0;
    private List<Train> trains = new ArrayList<>();

    public IncidentModel(DatabaseService databaseService) {
        this.databaseService = databaseService;
        fetchTrainNames();
    }

    public void fetchTrainNames() {
        setChanged();
        notifyObservers(UpdateType.BITMAP_UPDATED);

        databaseService.getMiguel(user -> {
            if (user != null) {
                trains = user.getTrains();
                setChanged();
                notifyObservers(UpdateType.TRAINS_LOADED);
            }
        });
    }

    public void sendIncident(){
        setChanged();
        if(getTrainSpinnerPosition() == 0) notifyObservers(UpdateType.MISSING_ROUTE);
        else if(getIncidentSpinnerPosition() == 0) notifyObservers(UpdateType.MISSING_CATEGORY);
        else {
            Incident incident = incidentFactory.createIncident(trains.get(trainSpinnerPosition - 1).getTrainId(),
                    getIncidentSpinnerPosition(), getDescription()).get();
            databaseService.addIncident(incident);
            description = "";
            incidentSpinnerPosition = 0;
            trainSpinnerPosition = 0;
            bitmap = null;
            addTrainToUser();
            notifyObservers(UpdateType.INCIDENT_SENT);
        }
    }

    public void addTrainToUser() {
        databaseService.getMiguel(user -> {
            if (user != null) {
                Train basicTrain = new Train(new Date(), new Date(), "Antibes", "Marseille", "TER", databaseService.getIdForTrain());
                user.addTrainToUser(basicTrain);
                databaseService.addTrain(basicTrain);
                databaseService.deleteAndReAddUser(user);
            }
        });
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
        setChanged();
        notifyObservers(UpdateType.BITMAP_UPDATED);
    }

    public void setTrainSpinnerPosition(int position) {
        this.trainSpinnerPosition = position;
    }

    public void setIncidentSpinnerPosition(int position) {
        this.incidentSpinnerPosition = position;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getTrainNames() {
        List<String> trainNames = new ArrayList<>();
        trainNames.add("-- Sélectionner un trajet --");
        for (Train t : trains) trainNames.add(t.getTrainName());
        return trainNames;
    }

    public List<String> getIncidentsNames() {
        List<String> incidentNames = new ArrayList<>();
        incidentNames.add("-- Sélectionner une catégorie --");
        for (TypeOfIncident ty : TypeOfIncident.values()) incidentNames.add(ty.getDescription());
        return incidentNames;
    }

    public int getTrainSpinnerPosition() {
        return trainSpinnerPosition;
    }

    public int getIncidentSpinnerPosition() {
        return incidentSpinnerPosition;
    }

    public enum UpdateType {
        DESCRIPTION_CHANGE,
        BITMAP_UPDATED,
        SHOW_PROCESS_DIALOG,
        TRAINS_LOADED,
        INCIDENT_SENT,
        MISSING_CATEGORY,
        MISSING_ROUTE
    }
}
