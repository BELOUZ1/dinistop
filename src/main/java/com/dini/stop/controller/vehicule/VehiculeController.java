package com.dini.stop.controller.vehicule;

import com.dini.stop.bean.ResponseContext;
import com.dini.stop.bean.VehiculeBean;
import com.dini.stop.controller.AbstractController;
import com.dini.stop.controller.user.UserController;
import com.dini.stop.dao.vehicule.VehiculeDao;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(UserController.PATH)
@Api(value = UserController.PATH, tags = "Vehicule controller")
public class VehiculeController extends AbstractController {

    public static final String PATH = BUSINESS_API_PREFIX + "vehicule";

    private VehiculeDao dao;

    @Autowired
    public VehiculeController(VehiculeDao dao) {
        this.dao = dao;
    }

    @ApiOperation(value = "Ajouter un véhicule pour un utilisateur")
    @PostMapping("/ajoutervehicule")
    public ResponseEntity<ResponseContext> ajouterVehicule(@RequestBody VehiculeBean vehiculeBean){
        ResponseContext context = dao.ajouterVeicule(vehiculeBean);
        return ResponseEntity.ok(context);
    }


    @ApiOperation(value = "Supprimer un véhicule pour un utilisateur")
    @GetMapping("/supprimervehicule/{idvehicule}")
    public ResponseEntity<ResponseContext> supprimerVehicule(@PathVariable String idvehicule){
        ResponseContext context = dao.supprimerVeicule(idvehicule);
        return ResponseEntity.ok(context);
    }
}
