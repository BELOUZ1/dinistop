package com.dini.stop.controller.trajet;

import com.dini.stop.bean.ResponseContext;
import com.dini.stop.bean.TrajetBean;
import com.dini.stop.controller.AbstractController;
import com.dini.stop.controller.user.UserController;
import com.dini.stop.dao.trajet.TrajetDao;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(UserController.PATH)
@Api(value = UserController.PATH, tags = "Trajet controller")
public class TrajetController extends AbstractController {

    public static final String PATH = BUSINESS_API_PREFIX + "trajet";

    private TrajetDao dao;

    @Autowired
    public TrajetController(TrajetDao dao) {
        this.dao = dao;
    }

    @ApiOperation(value = "Ajouter un trajet")
    @PostMapping("/ajoutertrajet")
    public ResponseEntity<ResponseContext> ajouterTrajet(@RequestBody TrajetBean bean){
        ResponseContext context = dao.ajouterTrajet(bean);
        return ResponseEntity.ok(context);
    }

    @ApiOperation(value = "Récupérer la liste des trajets")
    @GetMapping("/gettrajetbyuser/{idutilisateur}")
    public ResponseEntity<ResponseContext> getTrajetByUser(@PathVariable("idutilisateur") String idUtilisateur){
        ResponseContext context = dao.getTrajetsByUser(idUtilisateur);
        return ResponseEntity.ok(context);
    }

}
