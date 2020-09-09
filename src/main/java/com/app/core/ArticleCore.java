/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.core;

import com.app.config.MyRestControllerConfig;
import com.app.dao.ArticlesDao;
import com.app.entities.Articles;
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
public class ArticleCore {
    
    @Autowired
    private ArticlesDao articlesDao;
    
    //afficher tous les articles
    @ApiOperation( value= "Afficher une liste des articles disponibles" , response = List.class )
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "la liste des articles a correctement ete envoyé"),
        @ApiResponse(code = 403, message = "Erreur lors de l'envoie de la liste des demandes.")
    })
    /*@Produces(value = MediaType.APPLICATION_JSON)
    @GetMapping("/articles")
    public List<Articles> getContactses(){
            return articlesDao.findAll();            
    }*/
    @RequestMapping(value = "/articles", method= RequestMethod.GET, produces = "application/json")
    public List<Articles> list(Model model){ 
        return articlesDao.findAll();
    }

    //afficher un article par id
    @GetMapping("/articles/{id}")
    public Optional<Articles> getArticleOptional(@RequestBody @PathVariable Long id){
            return articlesDao.findById(id);
    }

    //ajouter un article
    @PostMapping("/articles/addarticle")
    public Articles createArticle(@RequestBody Articles a){
            return articlesDao.save(a);
    }

    //supprimer un article dont l'id est 'id'
    @DeleteMapping("/articles/deleteadmin/{id}")
    public void deleteArticle(@RequestBody @PathVariable Long id){
            articlesDao.deleteById(id);
    }
    
    @RequestMapping(value = "/updateArticle/{id}", method=RequestMethod.PUT)
    public Articles updateArticles(@PathVariable Long id, @RequestBody Articles a){
        a.setIdarticle(id);
        return articlesDao.save(a);
    }
    
    @GetMapping(value = "/searchArticle")
    public Page<Articles> searchArticle(@RequestParam(name="cAtc", defaultValue = "") String cAtc, 
            @RequestParam(name="page", defaultValue = "0") int page, 
            @RequestParam(name="size", defaultValue = "5") int size){
        return articlesDao.searchArticle("%"+cAtc+"%", PageRequest.of(page, size));
    }
    
}
