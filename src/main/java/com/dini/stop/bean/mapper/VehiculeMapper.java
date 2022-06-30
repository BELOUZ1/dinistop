package com.dini.stop.bean.mapper;

import com.dini.stop.bean.VehiculeBean;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class VehiculeMapper implements RowMapper<VehiculeBean> {
    @Override
    public VehiculeBean mapRow(ResultSet rs, int rowNum) throws SQLException {

        VehiculeBean vehiculeBean = new VehiculeBean();

        vehiculeBean.setIdVehicule(rs.getString("idvehicule"));
        vehiculeBean.setIdUtilisateur(rs.getString("idutilisateur"));
        vehiculeBean.setModele(rs.getString("modele"));
        vehiculeBean.setMatricule(rs.getString("matricule"));
        vehiculeBean.setAnneeImatriculation(rs.getString("matricule"));
        vehiculeBean.setMarque(rs.getString("marque"));
        vehiculeBean.setCouleur(rs.getString("couleur"));

        return vehiculeBean;
    }
}
