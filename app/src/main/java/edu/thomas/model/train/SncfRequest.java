package edu.thomas.model.train;

import android.credentials.Credential;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import edu.thomas.CredentialsKey;

public class SncfRequest {
    private final String TAG = "THOMAS "+getClass().getSimpleName();
    String token = CredentialsKey.API_KEY;

    String gareDepart;

    String gareArrive;

    String dateDepart;
    SimpleDateFormat formatter;


    public SncfRequest(String gareDepart, String gareArrive, String date) throws ParseException {
        this.gareDepart = gareDepart;
        this.gareArrive = gareArrive;
        this.dateDepart = date;
    }

    public  String getToken() {
        return token;
    }

    public String getGareDepart() {
        return gareDepart;
    }

    public void setGareDepart(String gareDepart) {
        this.gareDepart = gareDepart;
    }

    public String getGareArrive() {
        return gareArrive;
    }

    public void setGareArrive(String gareArrive) {
        this.gareArrive = gareArrive;
    }

    public String getDateDepart() {
        return dateDepart;
    }

    public void setDateDepart(String dateDepart)  {
        this.dateDepart = dateDepart;
    }

    public String getDate() {
        try {
            SimpleDateFormat inputFormatter = new SimpleDateFormat("d/M/yyyy-HH:mm", Locale.FRANCE);
            // Analyser la date d'entrée en objet Date
            Date date = inputFormatter.parse(dateDepart);

            // Définir le formateur pour le format de sortie
            SimpleDateFormat outputFormatter = new SimpleDateFormat("yyyyMMdd'T'HHmm", Locale.FRANCE);

            // Formater l'objet Date dans le format de sortie
            return outputFormatter.format(date)+"00";
        } catch (ParseException | NullPointerException e) {
            Log.d(TAG, "getDate: erreur de format de date " + e.getMessage());
            return Calendar.getInstance().getTime().toString();
        }
    }

    public String getCodeFromName(String name){
       try {
           return TrainStationMap.getTrainStation(name).getCodeUic();
       }
       catch (NullPointerException e){
           Log.d(TAG, "getCodeFromName: erreur nom inconnue "+ e.getMessage());
       }
       return null;
    }

    public String getURL(){
        return "https://api.sncf.com/v1/coverage/sncf/journeys?from=stop_area%3ASNCF%3A"+getCodeFromName(gareDepart)+"&to=stop_area%3ASNCF%3A"+getCodeFromName(gareArrive)+"&datetime="+getDate()+"&count=1&max_nb_transfers=0&mode[]=train";
    }

    //https://api.navitia.io/v1/coverage/sncf/journeys?to=stop_area%3ASNCF%3A87686006&from=stop_area%3ASNCF%3A87751008&count=1

    //https://api.navitia.io/v1/coverage/sncf/places?q=nice&type%5B%5D=stop_area&

    //ttps://api.sncf.com/v1/coverage/sncf/journeys?from=stop_area%3ASNCF%3A87751404&to=stop_area%3ASNCF%3A87751750&datetime=20240604T003700&count=4&depth=1&
}
