package edu.thomas.model.journey;

public class Journey {
    private final String date;
    private final String departureStation;
    private final String arrivalStation;
    private final String departure;
    private final String arrival;

    // Constructors, getters, and setters
    public Journey(String date, String departureStation, String arrivalStation, String departure, String arrival) {
        this.date = date;
        this.departureStation = departureStation;
        this.arrivalStation = arrivalStation;
        this.departure = departure;
        this.arrival = arrival;
    }

    public String getDate() {
        return date;
    }

    public String getDepartureStation() {
        return departureStation;
    }

    public String getArrivalStation() {
        return arrivalStation;
    }

    public String getDeparture() {
        return departure;
    }

    public String getArrival() {
        return arrival;
    }
}
