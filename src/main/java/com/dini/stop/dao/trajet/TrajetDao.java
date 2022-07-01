package com.dini.stop.dao.trajet;

import com.dini.stop.bean.ResponseContext;
import com.dini.stop.bean.TrajetBean;
import com.dini.stop.bean.exception.DiniStopException;

import java.util.List;

public interface TrajetDao {

    void ajouterTrajet(TrajetBean bean) throws DiniStopException;

    List<TrajetBean> getTrajetsByUser(String idUtilisateur) throws DiniStopException;

}
