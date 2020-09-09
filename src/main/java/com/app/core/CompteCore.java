/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.core;

import com.app.config.MyRestControllerConfig;
import com.app.dao.CompteDao;
import com.app.entities.Compte;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author youssouf
 */
@MyRestControllerConfig
@CrossOrigin("*")
public class CompteCore {
    
    @Autowired
    private CompteDao compteDao;
    
    //afficher tous les comptes
    @ApiOperation( value= "Afficher une liste des comptes disponibles" , response = List.class )
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "la liste des comptes a correctement ete envoyé"),
        @ApiResponse(code = 403, message = "Erreur lors de l'envoie de la liste des demandes.")
    })
    /*@Produces(value = MediaType.APPLICATION_JSON)
    @GetMapping("/comptes")
    public List<Compte> getCompteses(){
            return compteDao.findAll();            
    }*/
    @RequestMapping(value = "/compte", method= RequestMethod.GET, produces = "application/json")
    public List<Compte> list(Model model){ 
        return compteDao.findAll();
    }

    //afficher un compte par id
    @GetMapping("/comptes/{id}")
    public Optional<Compte> getAdminOptional(@RequestBody @PathVariable Long id){
            return compteDao.findById(id);
    }

    //ajouter un compte
    @PostMapping("/comptes/addcompte")
    public Compte createCompte(@RequestBody Compte cpt){
            return compteDao.save(cpt);
    }

    //supprimer un compte dont l'id est 'id'
    @DeleteMapping("/comptes/deletecompte/{id}")
    public void deleteCompte(@RequestBody @PathVariable Long id){
            compteDao.deleteById(id);
    }
    
    @RequestMapping(value = "/updateCompte/{id}", method=RequestMethod.PUT)
    public Compte updateCompte(@PathVariable Long id, @RequestBody Compte cpt){
        cpt.setIdcompte(id);
        return compteDao.save(cpt);
    }
    
    @GetMapping(value = "/searchCompte")
    public Page<Compte> searchCompte(@RequestParam(name="cCpt", defaultValue = "") String cCpt, 
            @RequestParam(name="page", defaultValue = "0") int page, 
            @RequestParam(name="size", defaultValue = "5") int size){
        return compteDao.searchCompte("%"+cCpt+"%", PageRequest.of(page, size));
    }
    
}
