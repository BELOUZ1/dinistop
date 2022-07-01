package com.dini.stop.dao.trajet;

import com.dini.stop.bean.ResponseContext;
import com.dini.stop.bean.TrajetBean;
import com.dini.stop.bean.exception.DiniStopException;
import com.dini.stop.bean.exception.ReturnCode;
import com.dini.stop.data.TrajetData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
public class TrajetDaoImpl implements TrajetDao{

    private static final Logger LOG = LoggerFactory.getLogger(TrajetDaoImpl.class);

    private TrajetData data;

    @Autowired
    public TrajetDaoImpl(TrajetData data) {
        this.data = data;
    }

    @Override
    public void ajouterTrajet(TrajetBean bean) throws DiniStopException {
        data.ajouterTrajet(bean);
    }

    @Override
    public List<TrajetBean> getTrajetsByUser(String idUtilisateur) throws DiniStopException {
        return data.getTrajetsByUser(idUtilisateur);
    }

}
