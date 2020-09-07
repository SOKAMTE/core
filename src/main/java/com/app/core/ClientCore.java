/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.core;

import com.app.config.MyRestControllerConfig;
import com.app.dao.ClientDao;
import com.app.entities.Client;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.List;
import java.util.Optional;  
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author kouam
 */
@MyRestControllerConfig
@CrossOrigin("*")
public class ClientCore {
    @Autowired
    private ClientDao clientdao;
    
    //afficher tous les clients
    @ApiOperation( value= "Afficher une liste des Clients disponibles" , response = List.class )
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "la liste des Clients a correctement ete envoyé"),
        @ApiResponse(code = 403, message = "Erreur lors de l'envoie de la liste des demandes.")
    })
    /*@Produces(value = MediaType.APPLICATION_JSON)
    @GetMapping("/clients")
    public List<Client> getContactses(){
            return clientdao.findAll();            
    }*/
    @RequestMapping(value = "/clients", method= RequestMethod.GET, produces = "application/json")
    public List<Client> list(Model model){ 
        return clientdao.findAll();
    }

    //afficher un client par id
    @GetMapping("/clients/{id}")
    public Optional<Client> getClientOptional(@RequestBody @PathVariable Long id){
            return clientdao.findById(id);
    }

    //ajouter un client
    @PostMapping("/clients/addclient")
    public Client createClient(@RequestBody Client c){
            return clientdao.save(c);
    }

    //supprimer un client dont l'id est 'id'
    @DeleteMapping("/clients/deleteclient/{id}")
    public void deleteClient(@RequestBody @PathVariable Long id){
            clientdao.deleteById(id);
    }
}
