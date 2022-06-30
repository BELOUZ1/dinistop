package com.dini.stop.dao.user;

import com.dini.stop.bean.ResponseContext;
import com.dini.stop.bean.UserBean;
import com.dini.stop.bean.exception.DiniStopException;


public interface UserDao {

    void inscription(UserBean userBean) throws DiniStopException;

    UserBean connexion(UserBean userBean) throws DiniStopException;

    void validerUtilisateur(String idUtilisateur, String type) throws DiniStopException;

    UserBean getUserByUserName(String userName);

    boolean utilisateurExiste(String email) throws DiniStopException;
}
