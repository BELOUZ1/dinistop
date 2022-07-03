package com.dini.stop.dao.vehicule;

import com.dini.stop.bean.VehiculeBean;
import com.dini.stop.bean.exception.DiniStopException;
import com.dini.stop.data.VehiculeData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class VehiculeDaoImpl implements VehiculeDao{

    private VehiculeData data;

    @Autowired
    public VehiculeDaoImpl(VehiculeData data) {
        this.data = data;
    }

    @Override
    public void ajouterVehicule(VehiculeBean vehiculeBean) throws DiniStopException {
        data.ajouterVehicule(vehiculeBean);
    }

    @Override
    public void supprimerVehicule(String idVehicule) throws DiniStopException {
        data.supprimerVehicule(idVehicule);
    }
}
