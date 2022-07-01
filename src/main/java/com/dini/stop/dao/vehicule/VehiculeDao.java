package com.dini.stop.dao.vehicule;


import com.dini.stop.bean.VehiculeBean;
import com.dini.stop.bean.exception.DiniStopException;

public interface VehiculeDao {

    void ajouterVehicule(VehiculeBean vehiculeBean) throws DiniStopException;

    void supprimerVehicule(String idVehicule) throws DiniStopException;

}
