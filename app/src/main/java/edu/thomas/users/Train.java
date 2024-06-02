package edu.thomas.users;

import java.util.Date;

import edu.thomas.service.DatabaseService;

public class Train {
    private Date departureAt;

    public Date getDepartureAt() {
        return departureAt;
    }

    public void setDepartureAt(Date departureAt) {
        this.departureAt = departureAt;
    }

    public String getDepartureWhere() {
        return departureWhere;
    }

    public void setDepartureWhere(String departureWhere) {
        this.departureWhere = departureWhere;
    }

    public String getArrivalWhere() {
        return arrivalWhere;
    }

    public void setArrivalWhere(String arrivalWhere) {
        this.arrivalWhere = arrivalWhere;
    }

    public String getTrainId() {
        return trainId;
    }

    public String getTrainName() {
        return trainName;
    }

    public void setTrainName(String trainName) {
        this.trainName = trainName;
    }

    public String trainName;

    public void setTrainId(String trainId) {
        this.trainId = trainId;
    }

    private String departureWhere;
    private String arrivalWhere;
    private String trainId;
    private DatabaseService databaseService = new DatabaseService();
    public Train(Date departureAt, String departureWhere, String arrivalWhere, String trainId) {
        this.departureAt = departureAt;
        this.departureWhere = departureWhere;
        this.arrivalWhere = arrivalWhere;
        this.trainId = trainId;
        this.trainName = departureWhere + "-" + arrivalWhere + " " +  departureAt;
        databaseService.addTrain(this);
    }
    public Train(){}
}
