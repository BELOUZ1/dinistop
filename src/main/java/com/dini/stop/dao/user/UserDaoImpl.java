package com.dini.stop.dao.user;

import com.dini.stop.bean.ResponseContext;
import com.dini.stop.bean.UserBean;
import com.dini.stop.bean.VehiculeBean;
import com.dini.stop.bean.exception.DiniStopException;
import com.dini.stop.bean.exception.ReturnCode;
import com.dini.stop.data.UserData;
import com.dini.stop.data.VehiculeData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.MessageFormat;

import java.util.List;

import java.util.UUID;

@Service
public class UserDaoImpl implements UserDao{

    private static final Logger LOG = LoggerFactory.getLogger(UserDaoImpl.class);

    private UserData dataUser;

    private VehiculeData dataVehicule;

    private JavaMailSender emailSender;

    private HttpServletRequest httpServletRequest;


    @Autowired
    public UserDaoImpl(UserData data, VehiculeData dataVehicule, HttpServletRequest httpServletRequest,
                       JavaMailSender emailSender) {
        this.dataUser = data;
        this.dataVehicule = dataVehicule;
        this.emailSender = emailSender;
        this.httpServletRequest = httpServletRequest;
    }

    @Override
    public void inscription(UserBean userBean) throws DiniStopException {
         String idUtilisateur = UUID.randomUUID().toString();
         userBean.setIdUtilisateur(idUtilisateur);
         dataUser.inscription(userBean);
         sendMail(userBean);

    }

    @Override
    public UserBean connexion(UserBean userBean) throws DiniStopException {
        UserBean bean = dataUser.getUtilisateur(userBean.getEmail(), userBean.getMotDePasse());
        List<VehiculeBean> vehicules = dataVehicule.getVehiculesByUser(bean.getIdUtilisateur());
        bean.setVehicules(vehicules);
        return bean;
    }

    @Override
    public void validerUtilisateur(String idUtilisateur, String type) throws DiniStopException{
        dataUser.validerUtilisateur(idUtilisateur, type);
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


    private void sendMail(UserBean utilisateur) throws DiniStopException{

        try {

            String contextPath = httpServletRequest.getContextPath();

            StringBuilder sb = new StringBuilder("http://");
            sb.append(httpServletRequest.getServerName());
            sb.append(":");
            sb.append(httpServletRequest.getServerPort());

            if(contextPath != null && !contextPath.isEmpty()){
                sb.append("/");
                sb.append(contextPath);
            }

            sb.append("/api/user/validation/email/");
            sb.append(utilisateur.getIdUtilisateur());

            String urlConfirmation = sb.toString();

            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("dini.stop2022@gmail.com");
            message.setTo(utilisateur.getEmail());
            message.setSubject("Confirmation d'inscription");
            String content = "Bonjour {0} {1}. Merci de clicker sur le lien pour confirmer votre inscription : {2}";
            String texte = MessageFormat.format(content,utilisateur.getPrenom(),utilisateur.getNom(),urlConfirmation);
            message.setText(texte);

            emailSender.send(message);

        } catch (Exception e) {
            LOG.error("ERROR sendMail : {}", e);
            throw new DiniStopException("",e);
        }


    }
}
