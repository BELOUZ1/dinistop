package com.dini.stop.bean;

public class VehiculeBean {

    private String idVehicule;
    private String idUtilisateur;
    private String matricule;
    private String marque;
    private String modele;
    private String couleur;
    private String anneeImatriculation;

    public VehiculeBean() {
    }

    public String getIdVehicule() {
        return idVehicule;
    }

    public void setIdVehicule(String idVehicule) {
        this.idVehicule = idVehicule;
    }

    public String getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(String idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public String getModele() {
        return modele;
    }

    public void setModele(String modele) {
        this.modele = modele;
    }

    public String getCouleur() {
        return couleur;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }

    public String getAnneeImatriculation() {
        return anneeImatriculation;
    }

    public void setAnneeImatriculation(String anneeImatriculation) {
        this.anneeImatriculation = anneeImatriculation;
    }
}
