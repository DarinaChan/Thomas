package edu.thomas.model.train;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TrainStation {
    @JsonProperty("nom")
    private String nom;

    @JsonProperty("codes_uic")
    private String codeUic;

    @JsonProperty("codeinsee")
    private String codeInsee;


    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCodeUic() {
        return codeUic;
    }

    public void setCodeUic(String codeUic) {
        this.codeUic = codeUic;
    }

    public String getCodeInsee() {
        return codeInsee;
    }

    public void setCodeInsee(String codeInsee) {
        this.codeInsee = codeInsee;
    }
}
