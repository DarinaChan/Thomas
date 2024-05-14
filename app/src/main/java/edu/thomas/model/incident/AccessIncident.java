package edu.thomas.model.incident;
public class AccessIncident extends Incident {
    public AccessIncident(TypeOfIncident type,String description){
        super(type,description);
    }
    @Override
    public void notifyUsers(){
        switch(getType()){
            case NoEasyAcess:
                // Notify people with mobility problems
            case OvercrowdedTrain:
                super.notifyUsers();
        }
    }

}
