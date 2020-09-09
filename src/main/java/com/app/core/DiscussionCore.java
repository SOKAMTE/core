/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.core;

import com.app.config.MyRestControllerConfig;
import com.app.dao.DiscussionDao;
import com.app.entities.Discussion;
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
public class DiscussionCore {
    
    @Autowired
    private DiscussionDao discussionDao;
    
    //afficher tous les discussions
    @ApiOperation( value= "Afficher une liste des discussions disponibles" , response = List.class )
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "la liste des discussions a correctement ete envoyé"),
        @ApiResponse(code = 403, message = "Erreur lors de l'envoie de la liste des demandes.")
    })
    /*@Produces(value = MediaType.APPLICATION_JSON)
    @GetMapping("/discussions")
    public List<Discussion> getDiscussionses(){
            return discussionDao.findAll();            
    }*/
    @RequestMapping(value = "/discussion", method= RequestMethod.GET, produces = "application/json")
    public List<Discussion> list(Model model){ 
        return discussionDao.findAll();
    }

    //afficher un discussion par id
    @GetMapping("/discussions/{id}")
    public Optional<Discussion> getDiscussionOptional(@RequestBody @PathVariable Long id){
            return discussionDao.findById(id);
    }

    //ajouter un discussion
    @PostMapping("/discussions/adddiscussion")
    public Discussion createDiscussion(@RequestBody Discussion d){
            return discussionDao.save(d);
    }

    //supprimer un discussion dont l'id est 'id'
    @DeleteMapping("/discussions/deletediscussion/{id}")
    public void deleteDiscussion(@RequestBody @PathVariable Long id){
            discussionDao.deleteById(id);
    }
    
    @RequestMapping(value = "/updateDiscussion/{id}", method=RequestMethod.PUT)
    public Discussion updateDiscussion(@PathVariable Long id, @RequestBody Discussion disc){
        disc.setIddiscussion(id);
        return discussionDao.save(disc);
    }
    
    @GetMapping(value = "/searchDiscussion")
    public Page<Discussion> searchDiscussion(@RequestParam(name="cDisc", defaultValue = "") String cDisc, 
            @RequestParam(name="page", defaultValue = "0") int page, 
            @RequestParam(name="size", defaultValue = "5") int size){
        return discussionDao.searchDiscussion("%"+cDisc+"%", PageRequest.of(page, size));
    }
    
}
