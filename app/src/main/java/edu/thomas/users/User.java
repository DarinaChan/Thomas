package edu.thomas.users;

import java.util.ArrayList;
import java.util.List;

import edu.thomas.service.DatabaseService;

public class User {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public List<Train> getTrains() {
        return trains;
    }
    public void setTrains(List<Train> t){
        this.trains = t;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public void setHasNotifications(boolean n){hasNotifications = n;}
    public boolean getHasNotifications(){return hasNotifications;}
    public boolean getIsPMR() {
        return isPMR;
    }

    public void setIsPMR(boolean PMR) {
        isPMR = PMR;
    }

    private String name;
    private String firstName;
    private List<Train> trains = new ArrayList<>();
    private String id;
    private boolean hasNotifications;

    private boolean isPMR;
    DatabaseService databaseService = new DatabaseService();

    public User(String name, String firstName){
        this.name = name;
        this.firstName = firstName;
        //this.id = databaseService.getIdForUser();
        this.id = "0";
        //databaseService.addUser(this);
    }
    public void addTrainToUser(Train train){
        this.trains.add(train);
        databaseService.addUser(this);
    }
    public User(){
        // Need this for firebase
    }
}
