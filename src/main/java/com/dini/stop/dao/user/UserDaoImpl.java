package com.dini.stop.dao.user;

import com.dini.stop.bean.UserBean;
import com.dini.stop.bean.VehiculeBean;
import com.dini.stop.bean.exception.DiniStopException;
import com.dini.stop.data.UserData;
import com.dini.stop.data.VehiculeData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class UserDaoImpl implements UserDao{

    private static final Logger LOG = LoggerFactory.getLogger(UserDaoImpl.class);


    private UserData dataUser;

    private VehiculeData dataVehicule;

    @Autowired
    public UserDaoImpl(UserData data, VehiculeData dataVehicule) {
        this.dataUser = data;
        this.dataVehicule = dataVehicule;
    }

    @Override
    public void inscription(UserBean userBean) throws DiniStopException {
         String idUtilisateur = UUID.randomUUID().toString();
         userBean.setIdUtilisateur(idUtilisateur);
         dataUser.inscription(userBean);
    }

    @Override
    public UserBean connexion(UserBean userBean) throws DiniStopException {
        UserBean bean = dataUser.getUtilisateur(userBean.getEmail(), userBean.getMotDePasse());
        List<VehiculeBean> vehicules = dataVehicule.getVehiculesByUser(bean.getIdUtilisateur());
        bean.setVehicules(vehicules);
        return bean;
    }

    @Override
    public void validerEmail(String idUtilisateur) throws DiniStopException{
        dataUser.validerEmail(idUtilisateur);
    }

    @Override
    public void validerTelephone(String idUtilisateur) throws DiniStopException {
        dataUser.validerTelephone(idUtilisateur);
    }

    @Override
    public void sendSMS(String telephone, String idUtilisateur) throws DiniStopException {
        dataUser.sendSMS(telephone, idUtilisateur);
    }

    @Override
    public UserBean getUserByUserName(String userName) {

        UserBean res = null;

        try {
            res = dataUser.getUtilisateur(userName);
        } catch (DiniStopException e) {
            LOG.error("ERROR getUserByUserName : {}", e);
        }

        return res;
    }

    @Override
    public boolean utilisateurExiste(String email) throws DiniStopException {
        boolean userExist = dataUser.utilisateurExiste(email);
        return userExist;
    }

}
