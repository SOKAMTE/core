/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.core;

import com.app.config.MyRestControllerConfig;
import com.app.dao.AdminDao;
import com.app.entities.Admin;
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
public class AdminCore {
    
    @Autowired
    private AdminDao adminDao;
    
    //afficher tous les admins
    @ApiOperation( value= "Afficher une liste des admins disponibles" , response = List.class )
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "la liste des admins a correctement ete envoyé"),
        @ApiResponse(code = 403, message = "Erreur lors de l'envoie de la liste des demandes.")
    })
    /*@Produces(value = MediaType.APPLICATION_JSON)
    @GetMapping("/admins")
    public List<Admin> getContactses(){
            return admindao.findAll();            
    }*/
    @RequestMapping(value = "/admin", method= RequestMethod.GET, produces = "application/json")
    public List<Admin> list(Model model){ 
        return adminDao.findAll();
    }

    //afficher un admin par id
    @GetMapping("/admins/{id}")
    public Optional<Admin> getAdminOptional(@RequestBody @PathVariable Long id){
            return adminDao.findById(id);
    }

    //ajouter un admin
    @PostMapping("/admins/addadmin")
    public Admin createAdmin(@RequestBody Admin a){
            return adminDao.save(a);
    }

    //supprimer un admin dont l'id est 'id'
    @DeleteMapping("/admins/deleteadmin/{id}")
    public void deleteAdmin(@RequestBody @PathVariable Long id){
            adminDao.deleteById(id);
    }
    
    @RequestMapping(value = "/updateAdmin/{id}", method=RequestMethod.PUT)
    public Admin updateAdmin(@PathVariable Long id, @RequestBody Admin a){
        a.setIdadmin(id);
        return adminDao.save(a);
    }
    
    @GetMapping(value = "/searchAdmin")
    public Page<Admin> searchAdmin(@RequestParam(name="cAdm", defaultValue = "") String cAdm, 
            @RequestParam(name="page", defaultValue = "0") int page, 
            @RequestParam(name="size", defaultValue = "5") int size){
        return adminDao.searchAdmin("%"+cAdm+"%", PageRequest.of(page, size));
    }
}
