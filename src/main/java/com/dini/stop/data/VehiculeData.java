package com.dini.stop.data;

import com.dini.stop.bean.VehiculeBean;
import com.dini.stop.bean.exception.DiniStopException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class VehiculeData {

    private JdbcTemplate template;

    @Autowired
    public VehiculeData(JdbcTemplate template) {
        this.template = template;
    }

    public void supprimerVehicule(String idVehicule) throws DiniStopException {
        String query = "DELETE FROM Vehicule WHERE idvehicule = ?;";
        Object params[] = new Object[] {idVehicule};

        try {
            template.update(query,params);
        }catch (Exception e){
            throw  new DiniStopException("Erreur suppression vehicule", e);
        }
    }

    public void ajouterVehicule(VehiculeBean vehiculeBean) throws DiniStopException {

        String query = "INSERT INTO Vehicule (idvehicule, idutilisateur, matricule, marque, modele, couleur, anneeimatriculation)"
                + " VALUES (?, ?, ?, ?, ?, ?, ?);";

        Object params[] = new Object[] {
                vehiculeBean.getIdVehicule(),
                vehiculeBean.getIdUtilisateur(),
                vehiculeBean.getMatricule(),
                vehiculeBean.getMarque(),
                vehiculeBean.getModele(),
                vehiculeBean.getCouleur(),
                vehiculeBean.getAnneeImatriculation()
        };

        try {
            template.update(query,params);
        }catch (Exception e){
            throw  new DiniStopException("Erreur ajout vehicule", e);
        }
    }

}
