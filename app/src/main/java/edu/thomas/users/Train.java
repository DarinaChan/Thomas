package edu.thomas.users;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import edu.thomas.R;
import edu.thomas.service.DatabaseService;

public class Train {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd'T'HHmmss")
    private Date departureAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd'T'HHmmss")
    private Date arrivalAt;
    private String departureWhere;
    private String arrivalWhere;
    private String trainId;
    private String trainType;
    public String trainName;

    public Date getDepartureAt() {
        return departureAt;
    }

    public void setDepartureAt(Date departureAt) {
        this.departureAt = departureAt;
    }
    public void setDepartureAtString(String departureAt){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd'T'HHmmss", Locale.FRANCE);
        try {
            this.departureAt = dateFormat.parse(departureAt);
        } catch (ParseException e) {
        }
    }

    public Date getArrivalAt() { return arrivalAt;}

    public void setArrivalAt(Date arrivalAt) {
        this.arrivalAt = arrivalAt;
    }
    public void getArrivalAtString(String departureAt){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd'T'HHmmss", Locale.FRANCE);
        try {
            this.arrivalAt = dateFormat.parse(departureAt);
        } catch (ParseException e) {
        }
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

    public void setTrainId(String trainId) {
        this.trainId = trainId;
    }

    public String getTrainType() { return trainType; }
    public void setTrainType(String trainType) { this.trainType = trainType; }

    public String getTrainName() {
        return trainName;
    }

    public void setTrainName(String trainName) {
        this.trainName = trainName;
    }


    private DatabaseService databaseService = new DatabaseService();
    public Train(Date departureAt, Date arrivalAt, String departureWhere, String arrivalWhere, String trainType, String trainId) {
        this.departureAt = departureAt;
        this.arrivalAt = arrivalAt;
        this.departureWhere = departureWhere;
        this.arrivalWhere = arrivalWhere;
        this.trainId = trainId;
        this.trainType = trainType;
        this.trainName = departureWhere + "-" + arrivalWhere + " " +  departureAt;
        databaseService.addTrain(this);
    }
    public Train(){}

    public static String[] formatDateAndTime(Date date) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("EEEE dd MMMM", Locale.FRENCH);
        SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm", Locale.FRENCH);

        String formattedDate = dateFormatter.format(date);
        formattedDate = formattedDate.substring(0, 1).toUpperCase() + formattedDate.substring(1);
        String formattedTime = timeFormatter.format(date);

        return new String[]{formattedDate, formattedTime};
    }

    public String getTravelTime() {
        long diffInMillis = arrivalAt.getTime() - departureAt.getTime();
        long hours = diffInMillis / (1000 * 60 * 60);
        long minutes = (diffInMillis / (1000 * 60)) % 60;

        return String.format(Locale.FRANCE, "%02d:%02d", hours, minutes);
    }
    public int getImage(){
        if (getTrainType() == null){
            return R.drawable.ic_train_black_24dp;
        }
        switch (getTrainType()){
            case "TER":
                return R.drawable.logo_ter;
            case "TGV":
                return R.drawable.logo_tgv;
            default:
                return R.drawable.ic_train_black_24dp;
        }
    }
}
