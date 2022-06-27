package com.dini.stop.bean.mapper;


import com.dini.stop.bean.TrajetBean;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;


public class TrajetMapper implements RowMapper<TrajetBean> {
    @Override
    public TrajetBean mapRow(ResultSet rs, int rowNum) throws SQLException {
        TrajetBean result = new TrajetBean();
        result.setIdTrajet(rs.getString("idtrajet"));
        result.setAdresseDepart(rs.getString("adressedepart"));
        result.setAdresseArrive(rs.getString("adressearrive"));
        result.setHeureDepart(rs.getTime("heuredepart").toLocaleString());
        Time heureDepart = rs.getTime("heuredepart");
        if(heureDepart != null){
            result.setHeureDepart(heureDepart.toString().substring(0, 5));
        }

        Time heureArrivee = rs.getTime("heurearrivee");
        if(heureDepart != null){
            result.setHeureArrivee(heureArrivee.toString().substring(0, 5));
        }

        result.setDateDepart(rs.getDate("datedepart"));
        result.setPosXDepart(rs.getFloat("posxdepart"));
        result.setPosYDepart(rs.getFloat("posydepart"));
        result.setPosXArrivee(rs.getFloat("posxarrivee"));
        result.setPosYArrivee(rs.getFloat("posyarrivee"));
        result.setIdUtilisateur(rs.getString("idutilisateur"));

        return result;
    }
}
