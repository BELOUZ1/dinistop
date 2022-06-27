package com.dini.stop.bean;

import java.util.Date;

public class UserBean {

    private String idUtilisateur;
    private String nom;
    private String prenom;
    private Date dateNaissance;
    private String telephone;
    private String email;
    private String motDePasse;
    private boolean emailValide;
    private boolean telephoneValide;
    private VehiculeBean vehicule;


    public UserBean() {
    }

    public String getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(String idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public Date getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(Date dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public boolean isEmailValide() {
        return emailValide;
    }

    public void setEmailValide(boolean emailValide) {
        this.emailValide = emailValide;
    }

    public boolean isTelephoneValide() {
        return telephoneValide;
    }

    public void setTelephoneValide(boolean telephoneValide) {
        this.telephoneValide = telephoneValide;
    }

    public VehiculeBean getVehicule() {
        return vehicule;
    }

    public void setVehicule(VehiculeBean vehicule) {
        this.vehicule = vehicule;
    }
}
