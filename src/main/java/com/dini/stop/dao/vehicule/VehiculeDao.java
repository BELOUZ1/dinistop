package com.dini.stop.dao.vehicule;

import com.dini.stop.bean.ResponseContext;
import com.dini.stop.bean.VehiculeBean;

public interface VehiculeDao {

    ResponseContext ajouterVeicule(VehiculeBean vehiculeBean);

    ResponseContext supprimerVeicule(String idVehicule);
}
