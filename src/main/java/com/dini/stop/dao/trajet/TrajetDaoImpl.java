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
    public ResponseContext ajouterTrajet(TrajetBean bean) {

        ResponseContext response = new ResponseContext();
        Map<String, String> messages = new HashMap<>();

        try {
            bean.setIdTrajet(UUID.randomUUID().toString());
            data.ajouterTrajet(bean);
            response.setCode(ReturnCode.TRAJET_OK.getCode());
            messages.put("TRAJET_OK", "Trajet ajouté avec succès.");
        } catch (DiniStopException e) {
            LOG.error("ERROR ajouterTrajet : {}", e);
            response.setCode(ReturnCode.ERROR_TRAJET.getCode());
            messages.put("ERROR_TRAJET", "Erreur ajout d'un trajet");
            messages.put("ERROR", e.getCause().getMessage());
        }

        response.setMessages(messages);
        return response;
    }

    @Override
    public ResponseContext getTrajetsByUser(String idUtilisateur) {

        ResponseContext response = new ResponseContext();
        Map<String, String> messages = new HashMap<>();
        try {
            List<TrajetBean> result = data.getTrajetsByUser(idUtilisateur);
            response.setContext(result);
            response.setCode(ReturnCode.TRAJET_OK.getCode());
            messages.put("GET_TRAJET_OK", "Trajets récupérés avec succès.");
        } catch (DiniStopException e) {
            LOG.error("ERROR getTrajetsByUser : {}", e);
            response.setCode(ReturnCode.ERROR_TRAJET.getCode());
            messages.put("ERROR_GET_TRAJET", "Erreur get trajets");
            messages.put("ERROR", e.getCause().getMessage());
        }

        response.setMessages(messages);
        return response;
    }


}
