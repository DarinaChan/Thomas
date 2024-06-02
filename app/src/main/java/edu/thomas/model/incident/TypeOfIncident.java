package edu.thomas.model.incident;

public enum TypeOfIncident {
    PickPocket("Pick-Pocket"),
    DangerousPassenger("Passager potentiellement dangereux"),
    TrainLate("Train en retard"),
    TrainOnTime("Train à l'heure"),
    TrainCancelled("Train annulé"),
    TemperatureTooHigh("Il fait très chaud"),
    TemperatureTooLow("Il fait très froid"),
    NoEasyAcess("Pas accès PMR"),
    OvercrowdedTrain("Voiture très remplie");

    private final String description;

    TypeOfIncident(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}