package edu.thomas.model.incident;

public abstract class Incident {
    private TypeOfIncident type; // No SetType because it's only set at the initialization
    private String description;

    public Incident(TypeOfIncident type,String description){
        this.type = type;
        setDescription(description);
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
}

