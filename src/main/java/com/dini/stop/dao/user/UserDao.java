package com.dini.stop.dao.user;

import com.dini.stop.bean.ResponseContext;
import com.dini.stop.bean.UserBean;


public interface UserDao {

    ResponseContext inscription(UserBean userBean);

    ResponseContext connexion(UserBean userBean);

    ResponseContext validerUtilisateur(String idUtilisateur, String type);

}
