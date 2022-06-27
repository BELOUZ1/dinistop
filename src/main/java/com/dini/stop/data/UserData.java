package com.dini.stop.data;


import com.dini.stop.bean.UserBean;
import com.dini.stop.bean.VehiculeBean;
import com.dini.stop.bean.mapper.UserMapper;
import com.dini.stop.bean.exception.DiniStopException;
import com.dini.stop.bean.exception.ReturnCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserData {


    private JdbcTemplate template;

    @Autowired
    public UserData(JdbcTemplate template) {
        this.template = template;
    }

    public void validerUtilisateur(String idUtilisateur, String type) throws DiniStopException {

        String request;

        Object params[] = new Object[] {idUtilisateur};

        if(type.equals("email")){
            request = "UPDATE Utilisateur SET emailvalide = true WHERE idutilisateur = ?";
        }else{
            request = "UPDATE Utilisateur SET telephonevalide = true WHERE idutilisateur = ?";
        }

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

    public UserBean getUtilisateur(String email, String motDePasse) throws DiniStopException {

        String query = "SELECT " +
                "u.id, u.idutilisateur, u.nom, u.prenom, u.datenaissance, u.telephone, u.email, u.motdepasse, u.emailvalide, u.telephonevalide, " +
                "v.id, v.idvehicule, v.idutilisateur, v.matricule, v.marque, v.modele, v.couleur, v.anneeimatriculation " +
                "FROM Utilisateur u " +
                "LEFT JOIN Vehicule v ON u.idutilisateur = v.idutilisateur " +
                "WHERE u.email = ? AND motDePasse = ?;";

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
        String query = "INSERT INTO Utilisateur (idutilisateur, nom, prenom, datenaissance, telephone, email, motdepasse)"
                + " VALUES (?, ?, ?, ?, ?, ?, ?);";

        Object params[] = new Object[] {
                userBean.getIdUtilisateur(),
                userBean.getNom(),
                userBean.getPrenom(),
                userBean.getDateNaissance(),
                userBean.getTelephone(),
                userBean.getEmail(),
                userBean.getMotDePasse()
        };

        try {
            template.update(query,params);
        }catch (Exception e){
            throw  new DiniStopException(e.getMessage(), e);
        }
    }

}
