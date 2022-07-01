package com.dini.stop.service;

import com.dini.stop.bean.ResponseContext;
import com.dini.stop.bean.TrajetBean;
import com.dini.stop.bean.exception.DiniStopException;
import com.dini.stop.bean.exception.ReturnCode;
import com.dini.stop.dao.trajet.TrajetDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class TrajetService {

    private static final Logger LOG = LoggerFactory.getLogger(TrajetService.class);

    private TrajetDao trajetDao;

    @Autowired
    public TrajetService(TrajetDao trajetDao) {
        this.trajetDao = trajetDao;
    }

    public ResponseContext ajouterTrajet(TrajetBean bean) {

        ResponseContext response = new ResponseContext();
        Map<String, String> messages = new HashMap<>();

        try {
            bean.setIdTrajet(UUID.randomUUID().toString());
            trajetDao.ajouterTrajet(bean);
            response.setCode(ReturnCode.TRAJET_OK.getCode());
            response.setHttpStatus(HttpStatus.OK);
            messages.put("TRAJET_OK", "Trajet ajouté avec succès.");
        } catch (DiniStopException e) {
            LOG.error("ERROR ajouterTrajet : {}", e);
            response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setCode(ReturnCode.ERROR_TRAJET.getCode());
            messages.put("ERROR_TRAJET", "Erreur ajout d'un trajet");
            messages.put("ERROR", e.getCause().getMessage());
        }

        response.setMessages(messages);
        return response;
    }


    public ResponseContext getTrajetsByUser(String idUtilisateur){
        ResponseContext response = new ResponseContext();
        Map<String, String> messages = new HashMap<>();
        try {
            List<TrajetBean> result = trajetDao.getTrajetsByUser(idUtilisateur);
            response.setContext(result);
            response.setCode(ReturnCode.TRAJET_OK.getCode());
            response.setHttpStatus(HttpStatus.OK);
            messages.put("GET_TRAJET_OK", "Trajets récupérés avec succès.");
        } catch (DiniStopException e) {
            LOG.error("ERROR getTrajetsByUser : {}", e);
            response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setCode(ReturnCode.ERROR_TRAJET.getCode());
            messages.put("ERROR_GET_TRAJET", "Erreur get trajets");
            messages.put("ERROR", e.getCause().getMessage());
        }
        response.setMessages(messages);
        return response;
    }
}
