package com.dini.stop.dao.user;

import com.dini.stop.bean.ResponseContext;
import com.dini.stop.bean.UserBean;
import com.dini.stop.bean.VehiculeBean;
import com.dini.stop.bean.exception.DiniStopException;
import com.dini.stop.bean.exception.ReturnCode;
import com.dini.stop.data.UserData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class UserDaoImpl implements UserDao{

    private UserData data;

    private JavaMailSender emailSender;

    private HttpServletRequest httpServletRequest;

    @Autowired
    public UserDaoImpl(UserData data, HttpServletRequest httpServletRequest, JavaMailSender emailSender) {
        this.data = data;
        this.emailSender = emailSender;
        this.httpServletRequest = httpServletRequest;
    }

    @Override
    public ResponseContext inscription(UserBean userBean) {

        ResponseContext response = new ResponseContext();
        Map<String, String> messages = new HashMap<>();

        try {

            boolean userExist = data.utilisateurExiste(userBean.getEmail());

            if(userExist){
                response.setCode(ReturnCode.USER_EXIST.getCode());
                messages.put("INSCRIPTION_ERROR", "Utilisateur " + userBean.getEmail() + " existe déja !");
                response.setMessages(messages);

                return response;
            }

            String idUtilisateur = UUID.randomUUID().toString();
            userBean.setIdUtilisateur(idUtilisateur);
            data.inscription(userBean);
            sendMail(userBean);
            response.setCode(ReturnCode.USER_OK.getCode());
            messages.put("INSCRIPTION_OK", "Inscription a été effectuée avec succès.");
            response.setMessages(messages);

        } catch (DiniStopException e) {
            response.setCode(ReturnCode.ERROR_USER.getCode());
            messages.put("INSCRIPTION_ERROR", "Erreur inscription.");
            response.setMessages(messages);
        }
        return response;
    }

    @Override
    public ResponseContext connexion(UserBean userBean) {
        ResponseContext response = new ResponseContext();
        Map<String, String> messages = new HashMap<>();
        try {
            UserBean bean = data.getUtilisateur(userBean.getEmail(), userBean.getMotDePasse());
            response.setContext(bean);
            response.setCode(ReturnCode.USER_OK.getCode());
            messages.put("CONNEXION_OK", "Connexion a été effectuée avec succès.");
            response.setMessages(messages);
        } catch (DiniStopException e) {
            response.setCode(ReturnCode.ERROR_USER.getCode());
            messages.put("CONNEXION_ERROR", e.getMessage());
            messages.put("ERROR", e.getCause().getMessage());
            response.setMessages(messages);
        }

        return response;
    }

    @Override
    public ResponseContext validerUtilisateur(String idUtilisateur, String type) {
        ResponseContext response = new ResponseContext();
        Map<String, String> messages = new HashMap<>();
        try {
            data.validerUtilisateur(idUtilisateur, type);
            response.setCode(ReturnCode.USER_OK.getCode());
            messages.put("VALIDATION_OK", "Validation " + type + " a été effectuée avec succès.");
        } catch (DiniStopException e) {
            response.setCode(ReturnCode.ERROR_USER.getCode());
            messages.put("VALIDATION_ERROR", "Erreur validation " + type );
            messages.put("ERROR", e.getCause().getMessage());
        }

        response.setMessages(messages);
        return response;
    }


    private void sendMail(UserBean utilisateur) throws DiniStopException{

        try {


            String urlConfirmation = "http://" + httpServletRequest.getServerName()
                    + ":" + httpServletRequest.getServerPort()
                    + "/" + httpServletRequest.getContextPath()
                    +"/api/confirmationuser/" + utilisateur.getIdUtilisateur();

            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("belouz2@outlook.fr");
            message.setTo(utilisateur.getEmail());
            message.setSubject("Confirmation d'inscription");
            String content = "Bonjour {0} {1}. Merci de clicker sur le lien pour confirmer votre inscription : {2}";
            String texte = MessageFormat.format(content,utilisateur.getPrenom(),utilisateur.getNom(),urlConfirmation);
            message.setText(texte);

            emailSender.send(message);

        } catch (Exception e) {
            throw new DiniStopException("",e);
        }


    }
}
