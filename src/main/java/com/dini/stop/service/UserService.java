package com.dini.stop.service;

import com.dini.stop.bean.ResponseContext;
import com.dini.stop.bean.UserBean;
import com.dini.stop.bean.exception.DiniStopException;
import com.dini.stop.bean.exception.ReturnCode;
import com.dini.stop.configuration.JwtTokenUtil;
import com.dini.stop.dao.user.UserDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class UserService {

    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);
    private UserDao userDao;
    private AuthenticationManager authenticationManager;
    private JwtTokenUtil jwtTokenUtil;
    private JwtUserDetailsService jwtUserDetailsService;
    private PasswordEncoder bcryptEncoder;

    @Autowired
    public UserService(UserDao userDao, AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil,
                       JwtUserDetailsService jwtUserDetailsService, PasswordEncoder bcryptEncoder) {
        this.userDao = userDao;
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.jwtUserDetailsService = jwtUserDetailsService;
        this.bcryptEncoder = bcryptEncoder;
    }

    public ResponseContext createAuthenticationToken(UserBean userBean)  {


        ResponseContext response = new ResponseContext();
        Map<String, String> messages = new HashMap<>();
        try {
            authenticate(userBean.getEmail(), userBean.getMotDePasse());
            final UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(userBean.getEmail());
            final String token = jwtTokenUtil.generateToken(userDetails);
            UserBean bean = userDao.connexion(userBean);
            bean.setToken(token);
            response.setContext(bean);
            response.setCode(ReturnCode.USER_OK.getCode());
            response.setHttpStatus(HttpStatus.OK);
            messages.put("CONNEXION_OK", "Connexion a été effectuée avec succès.");
            response.setMessages(messages);
        } catch (DiniStopException e) {
            LOG.error("ERROR connexion : {}", e);
            response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setCode(ReturnCode.ERROR_USER.getCode());
            messages.put("CONNEXION_ERROR", e.getMessage());
            messages.put("ERROR", e.getCause().getMessage());
            response.setMessages(messages);
        }

        return response;

    }

    public ResponseContext validerTelephone(String idUtilisateur) {
        ResponseContext response = new ResponseContext();
        Map<String, String> messages = new HashMap<>();
        try {
            userDao.validerTelephone(idUtilisateur);
            response.setCode(ReturnCode.USER_OK.getCode());
            response.setHttpStatus(HttpStatus.OK);
            messages.put("VALIDATION_OK", "Validation telephone a été effectuée avec succès.");
        } catch (DiniStopException e) {
            LOG.error("ERROR validerTelephone : {}", e);
            response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setCode(ReturnCode.ERROR_USER.getCode());
            messages.put("VALIDATION_ERROR", "Erreur validation telephone");
            messages.put("ERROR", e.getCause().getMessage());
        }
        response.setMessages(messages);
        return response;
    }

    public ResponseContext validerEmail(String idUtilisateur) {
        ResponseContext response = new ResponseContext();
        Map<String, String> messages = new HashMap<>();
        try {
            userDao.validerEmail(idUtilisateur);
            response.setCode(ReturnCode.USER_OK.getCode());
            response.setHttpStatus(HttpStatus.OK);
            messages.put("VALIDATION_OK", "Validation email a été effectuée avec succès.");
        } catch (DiniStopException e) {
            LOG.error("ERROR validerUtilisateur : {}", e);
            response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setCode(ReturnCode.ERROR_USER.getCode());
            messages.put("VALIDATION_ERROR", "Erreur validation email");
            messages.put("ERROR", e.getCause().getMessage());
        }
        response.setMessages(messages);
        return response;

    }

    public ResponseContext sendSMS(String telephone, String idUtilisateur) {

        ResponseContext response = new ResponseContext();
        Map<String, String> messages = new HashMap<>();
        try {
            userDao.sendSMS(telephone, idUtilisateur);
            response.setCode(ReturnCode.USER_OK.getCode());
            response.setHttpStatus(HttpStatus.OK);
            messages.put("SEND_SMS_OK", "Envoie SMS a été effectuée avec succès.");
        } catch (DiniStopException e) {
            LOG.error("ERROR SendSMS : {}", e);
            response.setCode(ReturnCode.ERROR_USER.getCode());
            response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            messages.put("SEND_SMS_ERROR", "Erreur envoie SMS.");
            messages.put("ERROR", e.getCause().getMessage());
        }
        response.setMessages(messages);
        return response;
    }

    public ResponseContext inscription(UserBean userBean) {
        String encryptedPassword = bcryptEncoder.encode(userBean.getMotDePasse());
        userBean.setEncodedMotDePasse(encryptedPassword);
        ResponseContext response = new ResponseContext();
        Map<String, String> messages = new HashMap<>();
        try {
            boolean userExist = userDao.utilisateurExiste(userBean.getEmail());
            if(userExist){
                LOG.warn("L'utilisateur " + userBean.getEmail() + " existe");
                response.setCode(ReturnCode.USER_EXIST.getCode());
                response.setHttpStatus(HttpStatus.OK);
                messages.put("INSCRIPTION_ERROR", "Utilisateur " + userBean.getEmail() + " existe déja !");
                response.setMessages(messages);
                return response;
            }
            String idUtilisateur = UUID.randomUUID().toString();
            userBean.setIdUtilisateur(idUtilisateur);
            userDao.inscription(userBean);
            response.setCode(ReturnCode.USER_OK.getCode());
            response.setHttpStatus(HttpStatus.OK);
            messages.put("INSCRIPTION_OK", "Inscription a été effectuée avec succès.");
            response.setMessages(messages);

        } catch (DiniStopException e) {
            LOG.error("ERROR inscription : {}", e);
            response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setCode(ReturnCode.ERROR_USER.getCode());
            messages.put("INSCRIPTION_ERROR", "Erreur inscription");
            messages.put("ERROR", e.getCause().getMessage());
            response.setMessages(messages);
        }
        return response;
    }

    private void authenticate(String username, String password) throws DiniStopException {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new DiniStopException("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new DiniStopException("INVALID_CREDENTIALS", e);
        }
    }

}
