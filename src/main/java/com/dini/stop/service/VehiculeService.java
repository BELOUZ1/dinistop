package com.dini.stop.service;

import com.dini.stop.bean.ResponseContext;
import com.dini.stop.bean.VehiculeBean;
import com.dini.stop.bean.exception.DiniStopException;
import com.dini.stop.bean.exception.ReturnCode;
import com.dini.stop.dao.vehicule.VehiculeDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class VehiculeService {

    private static final Logger LOG = LoggerFactory.getLogger(VehiculeService.class);

    private VehiculeDao vehiculeDao;

    public VehiculeService(VehiculeDao vehiculeDao) {
        this.vehiculeDao = vehiculeDao;
    }

    public ResponseContext ajouterVeicule(VehiculeBean vehiculeBean) {

        ResponseContext response = new ResponseContext();
        Map<String, String> messages = new HashMap<>();

        try {
            vehiculeBean.setIdVehicule(UUID.randomUUID().toString());
            vehiculeDao.ajouterVehicule(vehiculeBean);
            response.setCode(ReturnCode.VEHICULE_OK.getCode());
            response.setHttpStatus(HttpStatus.OK);
            messages.put("VEHICULE_OK", "Vehicule ajouté avec succès.");
            response.setMessages(messages);
        } catch (DiniStopException e) {
            LOG.error("ERROR ajouterVeicule : {}", e);
            response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setCode(ReturnCode.ERROR_VEHICULE.getCode());
            messages.put("VEHICULE_ERROR", e.getMessage());
            messages.put("ERROR", e.getCause().getMessage());
            response.setMessages(messages);
        }

        return response;
    }

    public ResponseContext supprimerVeicule(String idVehicule) {
        ResponseContext response = new ResponseContext();
        Map<String, String> messages = new HashMap<>();

        try {
            vehiculeDao.supprimerVehicule(idVehicule);
            response.setCode(ReturnCode.VEHICULE_OK.getCode());
            response.setHttpStatus(HttpStatus.OK);
            messages.put("VEHICULE_OK", "Vehicule supprimé avec succès.");
        } catch (DiniStopException e) {
            LOG.error("ERROR supprimerVeicule : {}", e);
            response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setCode(ReturnCode.ERROR_VEHICULE.getCode());
            messages.put("VEHICULE_ERROR", e.getMessage());
            messages.put("ERROR", e.getCause().getMessage());

        }
        response.setMessages(messages);
        return response;
    }
}
