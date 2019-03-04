package com.interoperability.interoperability;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Data;

/**
 *
 * @author qbiss
 */
@Data
public class Research{
    private String champs;
    
    @Id 
    @GeneratedValue
    private long id;
    
    public Research(){
    }
    
    public Research(String champs){
        this.champs = champs;
    }
    
    public String requestQAnswer(){
        String qId = null;
        // requêter avec le service QAnswer
        return qId;
    }
}
