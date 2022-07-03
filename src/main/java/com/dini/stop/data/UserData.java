package com.dini.stop.data;


import com.dini.stop.bean.UserBean;
import com.dini.stop.bean.exception.DiniStopException;
import com.dini.stop.bean.exception.ReturnCode;
import com.dini.stop.bean.mapper.UserMapper;
import com.dini.stop.configuration.DiniStopConfig;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

@Repository
public class UserData {

    private static final String HTTP = "http://";

    private static final String HTTPS = "https://";

    private JdbcTemplate template;

    private JavaMailSender emailSender;

    private HttpServletRequest httpServletRequest;

    private Configuration configuration;

    private PlatformTransactionManager transactionManager;

    private DiniStopConfig diniStopConfig;

    @Autowired
    public UserData(DataSource dataSource, Configuration configuration,
                    HttpServletRequest httpServletRequest, JavaMailSender emailSender,
                    PlatformTransactionManager transactionManager, DiniStopConfig diniStopConfig) {
        this.template = new JdbcTemplate(dataSource);
        this.configuration = configuration;
        this.httpServletRequest = httpServletRequest;
        this.emailSender = emailSender;
        this.transactionManager = transactionManager;
        this.diniStopConfig = diniStopConfig;
    }

    public void validerEmail(String idUtilisateur) throws DiniStopException {

        String  request = "UPDATE Utilisateur SET emailvalide = true WHERE idutilisateur = ?";

        Object params[] = new Object[] {idUtilisateur};

        try {
            template.update(request,params);
        }catch (Exception e){
            throw  new DiniStopException(e.getMessage(), e);
        }

    }

    public void validerTelephone(String idUtilisateur) throws DiniStopException {

        String  request = "UPDATE Utilisateur SET telephonevalide = true WHERE idutilisateur = ?";

        Object params[] = new Object[] {idUtilisateur};

        try {
            template.update(request,params);
        }catch (Exception e){
            throw  new DiniStopException(e.getMessage(), e);
        }

    }


    public boolean utilisateurExiste(String email) throws DiniStopException {

        boolean existe = false;

        String req = "SELECT * FROM Utilisateur WHERE email = ?;";
        Object params[] = new Object[] {email};
        UserBean res ;

        try {
            res = template.queryForObject(
                    req, new UserMapper(), params);

            if(res != null){
                existe = true;
            }

        } catch (EmptyResultDataAccessException e) {
            existe = false;
        }catch (Exception e) {
            throw new DiniStopException("Erreur connexion",e,ReturnCode.ERROR_USER);
        }

        return existe;
    }

    public UserBean getUtilisateur(String email) throws DiniStopException {

        String query =  "SELECT * FROM Utilisateur u " +
                        "WHERE u.email = ?;";

        Object params[] = new Object[] {email};
        UserBean res ;

        try {
            res = template.queryForObject(
                    query, new UserMapper(), params);
            return res;
        } catch (EmptyResultDataAccessException e) {
            throw new DiniStopException("Email ou mot de passe incorrecte !!!",e, ReturnCode.ERROR_USER);
        }catch (Exception e) {
            throw new DiniStopException("Erreur connexion",e,ReturnCode.ERROR_USER);
        }

    }

    public UserBean getUtilisateur(String email, String motDePasse) throws DiniStopException {

        String query = "SELECT * FROM Utilisateur u " +
                "WHERE u.email = ? AND u.motDePasse = ?;";

        Object params[] = new Object[] {email,motDePasse};
        UserBean res ;

        try {
            res = template.queryForObject(
                    query, new UserMapper(), params);
            return res;
        } catch (EmptyResultDataAccessException e) {
            throw new DiniStopException("Email ou mot de passe incorrecte !!!",e, ReturnCode.ERROR_USER);
        }catch (Exception e) {
            throw new DiniStopException("Erreur connexion",e,ReturnCode.ERROR_USER);
        }

    }

    public void inscription(UserBean userBean) throws DiniStopException {

        TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
        TransactionStatus transactionStatus = transactionManager.getTransaction(transactionDefinition);

        String query = "INSERT INTO Utilisateur (idutilisateur, nom, prenom, datenaissance, telephone, email, motdepasse, encodedmotdepasse, emailvalide, telephonevalide)"
                + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

        Object params[] = new Object[] {
                userBean.getIdUtilisateur(),
                userBean.getNom(),
                userBean.getPrenom(),
                userBean.getDateNaissance(),
                userBean.getTelephone(),
                userBean.getEmail(),
                userBean.getMotDePasse(),
                userBean.getEncodedMotDePasse(),
                userBean.isEmailValide(),
                userBean.isTelephoneValide()
        };

        try {
            template.update(query,params);
            sendMail(userBean);
            transactionManager.commit(transactionStatus);
        }catch (Exception e){
            transactionManager.rollback(transactionStatus);
            throw  new DiniStopException(e.getMessage(), e.getCause(), ReturnCode.ERROR_USER);
        }
    }

    public void sendSMS(String telephone, String idUtilisateur) throws DiniStopException {

        String contextPath = httpServletRequest.getContextPath();
        String protocol = httpServletRequest.getProtocol();
        StringBuilder sb = new StringBuilder();
        if(protocol.toLowerCase().contains("https")){
            sb.append(HTTPS);
        }else{
            sb.append(HTTP);
        }
        sb.append(httpServletRequest.getServerName());
        sb.append(":");
        sb.append(httpServletRequest.getServerPort());

        if(contextPath != null && !contextPath.isEmpty()){
            sb.append("/");
            sb.append(contextPath);
        }

        sb.append("/api/user/validation/telephone/");
        sb.append(idUtilisateur);

        String urlConfirmation = sb.toString();

        try{
            Twilio.init(diniStopConfig.getSmsusername(), diniStopConfig.getSmspassword());

            Message.creator(new PhoneNumber(telephone),
                    new PhoneNumber(diniStopConfig.getSmsphone()), urlConfirmation).create();
        }catch (Exception e){
            throw new DiniStopException("Erreur envoie sms", e);
        }

    }

    private void sendMail(UserBean utilisateur) throws TemplateException, IOException, MessagingException {

            MimeMessage mimeMessage = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
            String contextPath = httpServletRequest.getContextPath();

            String protocol = httpServletRequest.getProtocol();
            StringBuilder sb = new StringBuilder();
            if(protocol.toLowerCase().contains("https")){
                sb.append(HTTPS);
            }else{
                sb.append(HTTP);
            }
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

            helper.setFrom("no-reply@gmail.com");
            helper.setReplyTo("no-reply@gmail.com");
            helper.setTo(utilisateur.getEmail());
            helper.setSubject("Confirmation d'inscription");
            helper.setText(getEmailContent(utilisateur, urlConfirmation), true);

            emailSender.send(mimeMessage);


    }

    private String getEmailContent(UserBean userBean, String urlValidation) throws IOException, TemplateException {
        StringWriter stringWriter = new StringWriter();
        Map<String, String> model = new HashMap<>();
        model.put("nom", userBean.getNom());
        model.put("prenom", userBean.getPrenom());
        model.put("urlvalidation", urlValidation);
        configuration.getTemplate("email.ftlh").process(model, stringWriter);
        return stringWriter.getBuffer().toString();
    }

}
