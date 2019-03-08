package com.interoperability.interoperability;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Data;

/**
 *
 * @author qbiss
 */
@Data
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
    
    public void RequestQAnswer(){
        
    }
}
