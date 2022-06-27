package com.dini.stop.dao.trajet;

import com.dini.stop.bean.ResponseContext;
import com.dini.stop.bean.TrajetBean;

public interface TrajetDao {

    ResponseContext ajouterTrajet(TrajetBean bean);

    ResponseContext getTrajetsByUser(String idUtilisateur);

}
