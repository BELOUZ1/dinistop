package com.dini.stop.controller.user;

import com.dini.stop.bean.ResponseContext;
import com.dini.stop.bean.UserBean;
import com.dini.stop.controller.AbstractController;
import com.dini.stop.dao.user.UserDao;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(UserController.PATH)
@Api(value = UserController.PATH, tags = "Utilisateur controller")
public class UserController extends AbstractController {

    public static final String PATH = BUSINESS_API_PREFIX + "user";

    private UserDao dao;

    public UserController(UserDao dao) {
        this.dao = dao;
    }

    @ApiOperation(value = "Hello")
    @GetMapping("/hello")
    public ResponseEntity hello() {
        return ResponseEntity.ok("Hello");
    }

    @ApiOperation(value = "Ajouter un utilisateur")
    @PostMapping("/inscription")
    public ResponseEntity<ResponseContext> inscription(@RequestBody UserBean userBean){
        ResponseContext context = dao.inscription(userBean);
        return ResponseEntity.ok(context);
    }


    @ApiOperation(value = "Connexion")
    @PostMapping("/connexion")
    public ResponseEntity<ResponseContext> connexion(@RequestBody UserBean userBean){
        ResponseContext context = dao.connexion(userBean);
        return ResponseEntity.ok(context);
    }

    @ApiOperation(value = "Validation utilisateur")
    @GetMapping("/validation/{type}/{idutilisateur}")
    public ResponseEntity<ResponseContext> validationUtilisateur(@PathVariable String type, @PathVariable("idutilisateur") String idUtilisateur){
        ResponseContext context = dao.validerUtilisateur(idUtilisateur, type);
        return ResponseEntity.ok(context);
    }
}
