package com.dini.stop.bean;

import java.util.Date;

public class TrajetBean {

    private String idTrajet;
    private String adresseDepart;
    private String adresseArrive;
    private String heureDepart;
    private String heureArrivee;
    private Date dateDepart;
    private float posXDepart;
    private float posYDepart;
    private float posXArrivee;
    private float posYArrivee;
    private String idUtilisateur;

    public TrajetBean() {
    }


    public String getIdTrajet() {
        return idTrajet;
    }

    public void setIdTrajet(String idTrajet) {
        this.idTrajet = idTrajet;
    }

    public String getAdresseDepart() {
        return adresseDepart;
    }

    public void setAdresseDepart(String adresseDepart) {
        this.adresseDepart = adresseDepart;
    }

    public String getAdresseArrive() {
        return adresseArrive;
    }

    public void setAdresseArrive(String adresseArrive) {
        this.adresseArrive = adresseArrive;
    }

    public String getHeureDepart() {
        return heureDepart;
    }

    public void setHeureDepart(String heureDepart) {
        this.heureDepart = heureDepart;
    }

    public String getHeureArrivee() {
        return heureArrivee;
    }

    public void setHeureArrivee(String heureArrivee) {
        this.heureArrivee = heureArrivee;
    }

    public Date getDateDepart() {
        return dateDepart;
    }

    public void setDateDepart(Date dateDepart) {
        this.dateDepart = dateDepart;
    }

    public float getPosXDepart() {
        return posXDepart;
    }

    public void setPosXDepart(float posXDepart) {
        this.posXDepart = posXDepart;
    }

    public float getPosYDepart() {
        return posYDepart;
    }

    public void setPosYDepart(float posYDepart) {
        this.posYDepart = posYDepart;
    }

    public float getPosXArrivee() {
        return posXArrivee;
    }

    public void setPosXArrivee(float posXArrivee) {
        this.posXArrivee = posXArrivee;
    }

    public float getPosYArrivee() {
        return posYArrivee;
    }

    public void setPosYArrivee(float posYArrivee) {
        this.posYArrivee = posYArrivee;
    }

    public String getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(String idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }
}
