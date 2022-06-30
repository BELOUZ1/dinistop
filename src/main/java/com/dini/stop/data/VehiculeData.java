package com.dini.stop.data;

import com.dini.stop.bean.TrajetBean;
import com.dini.stop.bean.VehiculeBean;
import com.dini.stop.bean.exception.DiniStopException;
import com.dini.stop.bean.mapper.TrajetMapper;
import com.dini.stop.bean.mapper.VehiculeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class VehiculeData {

    private JdbcTemplate template;

    @Autowired
    public VehiculeData(JdbcTemplate template) {
        this.template = template;
    }


    public List<VehiculeBean> getVehiculesByUser(String idUtilisateur) throws DiniStopException {

        String query = "SELECT * FROM Vehicule WHERE idutilisateur = ?;";

        Object params[] = new Object[] {idUtilisateur};

        try {
            List<VehiculeBean> result = template.query(
                    query,
                    params,new VehiculeMapper());
            return result;
        }catch (Exception e){
            throw new DiniStopException(e.getMessage(), e);
        }
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
