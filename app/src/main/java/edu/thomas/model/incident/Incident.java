package edu.thomas.model.incident;

import java.util.Optional;

import edu.thomas.Database;

public abstract class Incident {
    private TypeOfIncident type; // No SetType because it's only set at the initialization
    private String description;
    private String id;
    Database db = new Database();

    public Incident(TypeOfIncident type,String description){
        this.type = type;
        setDescription(description);
        this.id = db.getIdForIncident();
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
        // Notify everyone
    }
    public int getTypeId(){
        return this.type.ordinal();
    }
}

