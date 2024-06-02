package edu.thomas;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Collection;
import java.util.List;

import edu.thomas.model.incident.Incident;

public class Database {
    FirebaseFirestore db;
    public Database(){
        db = FirebaseFirestore.getInstance();
    }
    public String getIdForIncident(){
        DocumentReference newIncidentRef = db.collection("incidents").document();
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
}
