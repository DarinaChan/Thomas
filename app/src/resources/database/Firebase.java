package edu.thomas.database;

import java.io.FileInputStream;

public class firebase {
    FileInputStream serviceAccount =
            new FileInputStream("path/to/serviceAccountKey.json");
    FirebaseOptions options = new FirebaseOptions.Builder()
            .setCredentials(GoogleCredentials.fromStream(serviceAccount))
            .build();
    FirebaseApp.initializeApp(options);

}
