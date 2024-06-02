package edu.thomas.model.incident;

import edu.thomas.service.DatabaseService;
import edu.thomas.service.NotificationService;
import java.util.Date;

public abstract class Incident {
    private TypeOfIncident type; // No SetType because it's only set at the initialization
    private String description;
    private String id;

    public String getTrainId() {
        return trainId;
    }

    public void setTrainId(String trainId) {
        this.trainId = trainId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    private String trainId;
    private Date createdAt;
    DatabaseService db = new DatabaseService();
    NotificationService notificationService = new NotificationService();

    public Incident(String trainId,TypeOfIncident type,String description){
        this.type = type;
        setDescription(description);
        this.id = db.getIdForIncident();
        this.trainId = trainId;
        this.createdAt = new Date();
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public TypeOfIncident getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }
    public void notifyUsers(){

    }
    public int getTypeId(){
        return this.type.ordinal();
    }
}

