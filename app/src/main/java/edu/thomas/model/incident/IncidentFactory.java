package edu.thomas.model.incident;
import java.util.Optional;
public class IncidentFactory {

    public Optional<Incident> createIncident(int typeId, String description){
        TypeOfIncident type = getTypeFromId(typeId).get();
        switch (type){
            case PickPocket:
            case DangerousPassenger:
                return Optional.of(new PassengerIncident(type,description));
            case TrainLate:
            case TrainOnTime:
            case TrainCancelled:
                return Optional.of(new TrainIncident(type,description));
            case TemperatureTooLow:
                return Optional.of(new TemperatureIncident(type,"La température ressentie à bord est basse."));
            case TemperatureTooHigh:
                return Optional.of(new TemperatureIncident(type, "La température ressentie à bord est haute."));
            case NoEasyAcess:
            case OvercrowdedTrain:
                return Optional.of(new AccessIncident(type,description));
            default:
                System.out.println("Aucun incident n'a été trouvé.");
                return Optional.empty();
        }
    }
    public Optional<TypeOfIncident> getTypeFromId(int id){
        for (TypeOfIncident t: TypeOfIncident.values()){
            if (t.ordinal() == id){return Optional.of(t);}
        }
        return Optional.empty();
    }
}