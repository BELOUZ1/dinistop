package com.dini.stop.bean.mapper;

import com.dini.stop.bean.UserBean;
import com.dini.stop.bean.VehiculeBean;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements RowMapper<UserBean> {
    @Override
    public UserBean mapRow(ResultSet rs, int rowNum) throws SQLException {

        UserBean user = new UserBean();
        user.setIdUtilisateur(rs.getString("idutilisateur"));
        user.setNom(rs.getString("nom"));
        user.setPrenom(rs.getString("prenom"));
        user.setEmail(rs.getString("email"));
        user.setDateNaissance(rs.getDate("datenaissance"));
        user.setMotDePasse(rs.getString("motdepasse"));
        user.setTelephone(rs.getString("telephone"));
        user.setEmailValide(rs.getBoolean("emailvalide"));
        user.setTelephoneValide(rs.getBoolean("telephonevalide"));

        VehiculeBean vehiculeBean = new VehiculeBean();
        vehiculeBean.setIdVehicule(rs.getString("idvehicule"));
        vehiculeBean.setIdUtilisateur(rs.getString("idutilisateur"));
        vehiculeBean.setCouleur(rs.getString("couleur"));
        vehiculeBean.setMarque(rs.getString("marque"));
        vehiculeBean.setMatricule(rs.getString("matricule"));
        vehiculeBean.setAnneeImatriculation(rs.getString("anneeimatriculation"));
        vehiculeBean.setModele(rs.getString("modele"));

        user.setVehicule(vehiculeBean);

        return user;
    }
}
