package edu.thomas.model.journey;

public class Journey {
    private String departureStation;
    private String arrivalStation;
    private String departureDate;
    private String arrivalDate;

    // Constructors, getters, and setters
    public Journey(String departureStation, String arrivalStation, String departureDate, String arrivalDate) {
        this.departureStation = departureStation;
        this.arrivalStation = arrivalStation;
        this.departureDate = departureDate;
        this.arrivalDate = arrivalDate;
    }

    public String getDepartureStation() {
        return departureStation;
    }

    public String getArrivalStation() {
        return arrivalStation;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public String getArrivalDate() {
        return arrivalDate;
    }
}
