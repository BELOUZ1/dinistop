package com.dini.stop.dao.vehicule;

import com.dini.stop.bean.ResponseContext;
import com.dini.stop.bean.VehiculeBean;
import com.dini.stop.bean.exception.DiniStopException;
import com.dini.stop.bean.exception.ReturnCode;
import com.dini.stop.data.VehiculeData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class VehiculeDaoImpl implements VehiculeDao{

    private static final Logger LOG = LoggerFactory.getLogger(VehiculeDaoImpl.class);

    private VehiculeData data;

    @Autowired
    public VehiculeDaoImpl(VehiculeData data) {
        this.data = data;
    }

    @Override
    public ResponseContext ajouterVeicule(VehiculeBean vehiculeBean) {

        ResponseContext response = new ResponseContext();
        Map<String, String> messages = new HashMap<>();

        try {
            vehiculeBean.setIdVehicule(UUID.randomUUID().toString());
            data.ajouterVehicule(vehiculeBean);
            response.setCode(ReturnCode.VEHICULE_OK.getCode());
            messages.put("VEHICULE_OK", "Vehicule ajouté avec succès.");
            response.setMessages(messages);
        } catch (DiniStopException e) {
            LOG.error("ERROR ajouterVeicule : {}", e);
            response.setCode(ReturnCode.ERROR_VEHICULE.getCode());
            messages.put("VEHICULE_ERROR", e.getMessage());
            messages.put("ERROR", e.getCause().getMessage());
            response.setMessages(messages);
        }

        return response;
    }

    @Override
    public ResponseContext supprimerVeicule(String idVehicule) {

        ResponseContext response = new ResponseContext();
        Map<String, String> messages = new HashMap<>();

        try {
            data.supprimerVehicule(idVehicule);
            response.setCode(ReturnCode.VEHICULE_OK.getCode());
            messages.put("VEHICULE_OK", "Vehicule supprimé avec succès.");
        } catch (DiniStopException e) {
            LOG.error("ERROR supprimerVeicule : {}", e);
            response.setCode(ReturnCode.ERROR_VEHICULE.getCode());
            messages.put("VEHICULE_ERROR", e.getMessage());
            messages.put("ERROR", e.getCause().getMessage());

        }
        response.setMessages(messages);
        return response;
    }
}
