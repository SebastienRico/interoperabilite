/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.interoperability.interoperability.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Data;

/**
 *
 * @author qbiss
 */
@Data
@Entity
public class Recherche{
    private String champs;
    
    @Id 
    @GeneratedValue
    private long id;
    
    public Recherche(){
    }
    
    public Recherche(String champs){
        this.champs = champs;
    }
}
