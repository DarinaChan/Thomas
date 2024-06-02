package edu.thomas.model.incident;
public class AccessIncident extends Incident {
    public AccessIncident(String trainId,TypeOfIncident type,String description){
        super(trainId,type,description);
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
