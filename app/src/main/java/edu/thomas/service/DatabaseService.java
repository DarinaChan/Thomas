package edu.thomas.service;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import edu.thomas.model.incident.Incident;
import edu.thomas.users.Train;
import edu.thomas.users.User;

public class DatabaseService {
    FirebaseFirestore db;
    public DatabaseService(){
        db = FirebaseFirestore.getInstance();
    }
    public String getIdForIncident(){
        DocumentReference newIncidentRef = db.collection("incidents").document();
        return newIncidentRef.getId();
    }
    public String getIdForTrain(){
        DocumentReference newIncidentRef = db.collection("trains").document();
        return newIncidentRef.getId();
    }
    public void addIncident(Incident i){
        db.collection("incidents")
                .add(i)
                .addOnSuccessListener(documentReference -> System.out.println("DocumentSnapshot added with ID: "  +  documentReference.getId()))
                .addOnFailureListener(e -> System.out.println("Error adding document" + e));
    }
    public List<Incident> getIncidents(){
        return db.collection("incidents").get().getResult().toObjects(Incident.class);
    }

    public void addUser(User u) {
        db.collection("users")
                .add(u)
                .addOnSuccessListener(documentReference -> {
                    u.setId(documentReference.getId());
                    System.out.println("DocumentSnapshot added with ID: " + documentReference.getId());
                })
                .addOnFailureListener(e -> System.out.println("Error adding document: " + e));
    }
    public void getUsers(final FirestoreCallback firestoreCallback) {
        db.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<User> userList = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                User user = document.toObject(User.class);
                                userList.add(user);
                            }
                            firestoreCallback.onUserCallback(userList);
                        } else {
                            System.out.println("Error getting documents: " + task.getException());
                        }
                    }
                });
    }

    public void addTrain(Train t){
        db.collection("trains")
                .add(t)
                .addOnSuccessListener(documentReference -> System.out.println("DocumentSnapshot added with ID: "  +  documentReference.getId()))
                .addOnFailureListener(e -> System.out.println("Error adding document" + e));
    }
    public List<Train> getTrains(){
        return db.collection("trains").get().getResult().toObjects(Train.class);
    }
/*
    public Optional<Train> getTrainById(String trainId){
        for (Train t : getTrains()){
            if (t.getTrainId().equals(trainId)){
                return Optional.of(t);
            }
        }
        return Optional.empty();
    }*/
}
