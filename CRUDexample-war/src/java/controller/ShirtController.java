/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entities.ShirtEntity;
import entities.TagEntity;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.inject.Named;
import model.ShirtEntityFacade;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import model.TagEntityFacade;

/**
 *
 * @author jaspe
 */
@Named(value = "shirtController")
@RequestScoped
public class ShirtController implements Serializable {

    @EJB
    private TagEntityFacade tagEntityFacade;

    @EJB
    private ShirtEntityFacade shirtEntityFacade;
    private ShirtEntity shirt = new ShirtEntity();
    
    public ShirtController() {
    }

    public ShirtEntity getShirt() {
        return shirt;
    }

    public void setShoe(ShirtEntity shirt) {
        this.shirt = shirt;
    }
    
    public List<ShirtEntity> findAll(){
        return this.shirtEntityFacade.findAll();
    }
    
    public String add(){
        this.shirtEntityFacade.create(this.shirt);
        return "index";
    }
    
    public void remove(ShirtEntity shirt){
        
        //When a product is deleted, make sure to remove all tags that this product used
        for(TagEntity p : shirt.getRelatedTags()){
            p.removeRelatedProduct(shirt);
            this.tagEntityFacade.edit(p);
        }
        shirt.clearRelatedTags();
        this.shirtEntityFacade.remove(shirt);
    }
    
    public String edit(ShirtEntity shirt){
        this.shirt = shirt;
        return "editShirt";
    }
    
    public String edit(){
        this.shirtEntityFacade.edit(this.shirt);
        return "index";
    }
    
    
}