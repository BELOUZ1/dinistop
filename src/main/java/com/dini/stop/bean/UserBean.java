package com.dini.stop.bean;

import java.util.Date;
import java.util.List;

public class UserBean {

    private String idUtilisateur;
    private String nom;
    private String prenom;
    private Date dateNaissance;
    private String telephone;
    private String email;
    private String motDePasse;
    private String encodedMotDePasse;
    private boolean emailValide;
    private boolean telephoneValide;
    private List<VehiculeBean> vehicules;
    private String token;


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

    public List<VehiculeBean> getVehicules() {
        return vehicules;
    }

    public void setVehicules(List<VehiculeBean> vehicules) {
        this.vehicules = vehicules;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEncodedMotDePasse() {
        return encodedMotDePasse;
    }

    public void setEncodedMotDePasse(String encodedMotDePasse) {
        this.encodedMotDePasse = encodedMotDePasse;
    }
}
