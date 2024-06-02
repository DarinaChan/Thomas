package edu.thomas.service;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
                    System.out.println("DocumentSnapshot added with ID: " + documentReference.getId());
                })
                .addOnFailureListener(e -> System.out.println("Error adding document: " + e));
    }
    public void getMiguel(final FirestoreCallback listener) {
        db.collection("users").document("ItqhRjzL6e1gNvUkz8Tj")
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            // Document exists, map it to a User object
                            User user = documentSnapshot.toObject(User.class);
                            listener.onMiguelCallback(user);
                        } else {
                            // Document does not exist
                            listener.onMiguelCallback(null);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.err.println("Error getting user: " + e.getMessage());
                    }
                });
    }
    public void addTrain(Train t){
        db.collection("trains")
                .add(t)
                .addOnSuccessListener(documentReference -> System.out.println("DocumentSnapshot added with ID: "  +  documentReference.getId()))
                .addOnFailureListener(e -> System.out.println("Error adding document" + e));
    }
    public void deleteAndReAddUser(User newData) {
        String userId = "ItqhRjzL6e1gNvUkz8Tj";
        DocumentReference userRef = db.collection("users").document(userId);
        userRef.delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        System.out.println("User successfully deleted!");

                        // Re-add the user with new data
                        userRef.set(newData)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        System.out.println("User successfully re-added!");
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        System.err.println("Error re-adding user: " + e.getMessage());
                                    }
                                });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.err.println("Error deleting user: " + e.getMessage());
                    }
                });
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
