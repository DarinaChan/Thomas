package edu.thomas.database;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Firebase {
    FileInputStream serviceAccount = new FileInputStream("thomasDB.json");

    FirebaseOptions options = new FirebaseOptions.Builder().setApiKey("AIzaSyCstfcefZWzfVR0Nq6NoDNY6xekPwrKU0g").build();
    public Firebase(){
        FirebaseApp.initializeApp()
    }



}
