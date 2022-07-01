package com.dini.stop.controller.user;

import com.dini.stop.bean.ResponseContext;
import com.dini.stop.bean.UserBean;
import com.dini.stop.controller.AbstractController;
import com.dini.stop.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(UserController.PATH)
@Api(value = UserController.PATH, tags = "Utilisateur controller")
public class UserController extends AbstractController {

    public static final String PATH = BUSINESS_API_PREFIX + "user";

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ApiOperation(value = "Hello")
    @GetMapping("/hello")
    public ResponseEntity hello() {
        return ResponseEntity.ok("Hello");
    }

    @ApiOperation(value = "Ajouter un utilisateur")
    @PostMapping("/inscription")
    public ResponseEntity<ResponseContext> inscription(@RequestBody UserBean userBean){
        ResponseContext context = userService.inscription(userBean);
        return ResponseEntity.status(context.getHttpStatus()).body(context);
    }


    @ApiOperation(value = "Connexion")
    @PostMapping("/connexion")
    public ResponseEntity<ResponseContext> connexion(@RequestBody UserBean userBean){
        ResponseContext context = userService.createAuthenticationToken(userBean);
        return ResponseEntity.status(context.getHttpStatus()).body(context);
    }

    @ApiOperation(value = "Validation Email")
    @GetMapping("/validation/email/{idutilisateur}")
    public ResponseEntity<ResponseContext> validationEmail(@PathVariable("idutilisateur") String idUtilisateur){
        ResponseContext context = userService.validerEmail(idUtilisateur);
        return ResponseEntity.status(context.getHttpStatus()).body(context);
    }

    @ApiOperation(value = "Validation Telephone")
    @GetMapping("/validation/telephone/{idutilisateur}")
    public ResponseEntity<ResponseContext> validationTelephone(@PathVariable("idutilisateur") String idUtilisateur){
        ResponseContext context = userService.validerTelephone(idUtilisateur);
        return ResponseEntity.status(context.getHttpStatus()).body(context);
    }

    @ApiOperation(value = "Send SMS")
    @GetMapping("/sendsms/{telephone}/{idutilisateur}")
    public ResponseEntity<ResponseContext> sendSMS(@PathVariable("telephone") String telephone, @PathVariable("idutilisateur") String idUtilisateur){
        ResponseContext context = userService.sendSMS(telephone, idUtilisateur);
        return ResponseEntity.status(context.getHttpStatus()).body(context);
    }
}
