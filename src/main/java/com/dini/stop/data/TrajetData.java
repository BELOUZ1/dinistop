package com.dini.stop.data;

import com.dini.stop.bean.TrajetBean;
import com.dini.stop.bean.exception.DiniStopException;
import com.dini.stop.bean.mapper.TrajetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class TrajetData {

    private JdbcTemplate template;

    @Autowired
    public TrajetData(JdbcTemplate template) {
        this.template = template;
    }

    public List<TrajetBean> getTrajetsByUser(String idUtilisateur) throws DiniStopException {

        String query = "SELECT * FROM Trajet WHERE idutilisateur = ?;";

        Object params[] = new Object[] {idUtilisateur};

        try {
            List<TrajetBean> result = template.query(
                    query,
                    params,new TrajetMapper());
            return result;
        }catch (Exception e){
            throw new DiniStopException(e.getMessage(), e);
        }
    }


    public void ajouterTrajet(TrajetBean bean) throws DiniStopException {
        String query = "INSERT INTO Trajet " +
                "(idtrajet, adressedepart, adressearrive, heuredepart, heurearrivee, datedepart, " +
                "posxdepart, posydepart, posxarrivee, posyarrivee, idutilisateur) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

        Object params[] = new Object[] {
                bean.getIdTrajet(),
                bean.getAdresseDepart(),
                bean.getAdresseArrive(),
                bean.getHeureDepart(),
                bean.getHeureArrivee(),
                bean.getDateDepart(),
                bean.getPosXDepart(),
                bean.getPosXArrivee(),
                bean.getPosYDepart(),
                bean.getPosYArrivee(),
                bean.getIdUtilisateur()
        };

        try {
            template.update(query,params);
        }catch (Exception e){
            throw  new DiniStopException(e.getMessage(), e);
        }
    }


}
