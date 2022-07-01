package com.dini.stop.dao.user;

import com.dini.stop.bean.UserBean;
import com.dini.stop.bean.exception.DiniStopException;


public interface UserDao {

    void inscription(UserBean userBean) throws DiniStopException;

    UserBean connexion(UserBean userBean) throws DiniStopException;

    void validerEmail(String idUtilisateur) throws DiniStopException;

    void validerTelephone(String idUtilisateur) throws DiniStopException;

    void sendSMS(String telephone, String idUtilisateur);

    UserBean getUserByUserName(String userName);

    boolean utilisateurExiste(String email) throws DiniStopException;
}
