package edu.thomas.model.train;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

import edu.thomas.R;

public class TrainInfo {
    @JsonProperty("arrival_date_time")
    String horaireDepart;
    @JsonProperty("departure_date_time")
    String horaireArrivee;
    String gareDepart;
    String gareArrivee;
    String dureeTrajet;
    String TypeTrain;
    Boolean incidents;
    String descIncidents;
    String nouvelleHoraireDepart;
    String nouvelleHoraireArrivee;
    public TrainInfo(String horaireDepart, String horaireArrivee, String gareDepart, String gareArrivee, String dureeTrajet, String typeTrain, Boolean incidents, String descIncidents, String nouvelleHoraireDepart, String nouvelleHoraireArrivee) {
        this.horaireDepart = horaireDepart;
        this.horaireArrivee = horaireArrivee;
        this.gareDepart = gareDepart;
        this.gareArrivee = gareArrivee;
        this.dureeTrajet = dureeTrajet;
        TypeTrain = typeTrain;
        this.incidents = incidents;
        this.descIncidents = descIncidents;
        this.nouvelleHoraireArrivee=nouvelleHoraireArrivee;
        this.nouvelleHoraireDepart=nouvelleHoraireDepart;
    }

    public TrainInfo(String horaireDepart, String horaireArrivee, String gareDepart, String gareArrivee, String dureeTrajet, String typeTrain){
        this(horaireDepart,horaireArrivee,gareDepart,gareArrivee,dureeTrajet,typeTrain,false,null,null,null);
    }

    public String getHoraireDepart() {
        return horaireDepart;
    }

    public void setHoraireDepart(String horaireDepart) {
        this.horaireDepart = horaireDepart;
    }

    public String getHoraireArrivee() {
        return horaireArrivee;
    }

    public void setHoraireArrivee(String horaireArrivee) {
        this.horaireArrivee = horaireArrivee;
    }

    public String getGareDepart() {
        return gareDepart;
    }

    public void setGareDepart(String gareDepart) {
        this.gareDepart = gareDepart;
    }

    public String getGareArrivee() {
        return gareArrivee;
    }

    public void setGareArrivee(String gareArrivee) {
        this.gareArrivee = gareArrivee;
    }

    public String getDureeTrajet() {
        return dureeTrajet;
    }

    public void setDureeTrajet(String dureeTrajet) {
        this.dureeTrajet = dureeTrajet;
    }

    public String getTypeTrain() {
        return TypeTrain;
    }

    public void setTypeTrain(String typeTrain) {
        TypeTrain = typeTrain;
    }

    public Boolean getIncidents() {
        return incidents;
    }

    public void setIncidents(Boolean incidents) {
        this.incidents = incidents;
    }

    public String getDescIncidents() {
        return descIncidents;
    }

    public void setDescIncidents(String descIncidents) {
        this.descIncidents = descIncidents;
    }

    public String getNouvelleHoraireDepart() {
        return nouvelleHoraireDepart;
    }

    public void setNouvelleHoraireDepart(String nouvelleHoraireDepart) {
        this.nouvelleHoraireDepart = nouvelleHoraireDepart;
    }

    public String getNouvelleHoraireArrivee() {
        return nouvelleHoraireArrivee;
    }

    public void setNouvelleHoraireArrivee(String nouvelleHoraireArrivee) {
        this.nouvelleHoraireArrivee = nouvelleHoraireArrivee;
    }

    public int getImage(){
        switch (getTypeTrain()){
            case "TER":
                return R.drawable.logo_ter;
            case "TGV":
                return R.drawable.logo_tgv;
            default:
                return R.drawable.logo_ter;
        }
    }
}
