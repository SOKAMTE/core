/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.core;

import com.app.config.MyRestControllerConfig;
import com.app.dao.CommentaireDao;
import com.app.entities.Commentaire;
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
public class CommentaireCore {
    
    @Autowired
    private CommentaireDao commentaireDao;
    
    //afficher tous les commentaires
    @ApiOperation( value= "Afficher une liste des commentaires disponibles" , response = List.class )
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "la liste des commentaires a correctement ete envoyé"),
        @ApiResponse(code = 403, message = "Erreur lors de l'envoie de la liste des demandes.")
    })
    /*@Produces(value = MediaType.APPLICATION_JSON)
    @GetMapping("/commentaires")
    public List<Commeentaire> getContactses(){
            return commentaireDao.findAll();            
    }*/
    @RequestMapping(value = "/commentaire", method= RequestMethod.GET, produces = "application/json")
    public List<Commentaire> list(Model model){ 
        return commentaireDao.findAll();
    }

    //afficher un commentaire par id
    @GetMapping("/commentaires/{id}")
    public Optional<Commentaire> getCommentaireOptional(@RequestBody @PathVariable Long id){
            return commentaireDao.findById(id);
    }

    //ajouter un commentaire
    @PostMapping("/commentaires/addcommentaire")
    public Commentaire createCommentaire(@RequestBody Commentaire c){
            return commentaireDao.save(c);
    }

    //supprimer un commentaire dont l'id est 'id'
    @DeleteMapping("/commentaire/deletecommentaire/{id}")
    public void deleteCommentaire(@RequestBody @PathVariable Long id){
            commentaireDao.deleteById(id);
    }
    
    @RequestMapping(value = "/updateCommentaire/{id}", method=RequestMethod.PUT)
    public Commentaire updateCommentaire(@PathVariable Long id, @RequestBody Commentaire cmt){
        cmt.setIdcommentaire(id);
        return commentaireDao.save(cmt);
    }
    
    @GetMapping(value = "/searchCommentaire")
    public Page<Commentaire> searchCommentaire(@RequestParam(name="cCmt", defaultValue = "") String cCmt, 
            @RequestParam(name="page", defaultValue = "0") int page, 
            @RequestParam(name="size", defaultValue = "5") int size){
        return commentaireDao.searchCommentaire("%"+cCmt+"%", PageRequest.of(page, size));
    }
    
}
