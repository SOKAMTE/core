/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.core;

import com.app.config.MyRestControllerConfig;
import com.app.dao.ArticleTypeDao;
import com.app.entities.ArticleType;
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
public class ArticleTypeCore {
    
    @Autowired
    private ArticleTypeDao articleTypeDao;
    
    //afficher tous les types d'articles
    @ApiOperation( value= "Afficher une liste des types d'articles disponibles" , response = List.class )
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "la liste des types d'articles a correctement ete envoyé"),
        @ApiResponse(code = 403, message = "Erreur lors de l'envoie de la liste des demandes.")
    })
    /*@Produces(value = MediaType.APPLICATION_JSON)
    @GetMapping("/articleType")
    public List<ArticleType> getContactses(){
            return articleTypeDao.findAll();            
    }*/
    @RequestMapping(value = "/articleType", method= RequestMethod.GET, produces = "application/json")
    public List<ArticleType> list(Model model){ 
        return articleTypeDao.findAll();
    }

    //afficher un type d'article par id
    @GetMapping("/articleType/{id}")
    public Optional<ArticleType> getArticleTypeOptional(@RequestBody @PathVariable Long id){
            return articleTypeDao.findById(id);
    }

    //ajouter un type d'article
    @PostMapping("/articleType/addarticleType")
    public ArticleType createArticleType(@RequestBody ArticleType at){
            return articleTypeDao.save(at);
    }

    //supprimer un type d'article dont l'id est 'id'
    @DeleteMapping("/articleType/deletearticleType/{id}")
    public void deleteAdmin(@RequestBody @PathVariable Long id){
            articleTypeDao.deleteById(id);
    }
    
    @RequestMapping(value = "/updateArticleType/{id}", method=RequestMethod.PUT)
    public ArticleType updateArticleType(@PathVariable Long id, @RequestBody ArticleType at){
        at.setIdarticletype(id);
        return articleTypeDao.save(at);
    }
    
    @GetMapping(value = "/searchArticleType")
    public Page<ArticleType> searchArticleType(@RequestParam(name="cAtcTp", defaultValue = "") String cAtcTp, 
            @RequestParam(name="page", defaultValue = "0") int page, 
            @RequestParam(name="size", defaultValue = "5") int size){
        return articleTypeDao.searchArticleType("%"+cAtcTp+"%", PageRequest.of(page, size));
    }
    
}
