package com.interoperability.interoperability.objetsDTO;
import com.interoperability.interoperability.ObjectDTO;

import lombok.Data;

@Data
public class AddressDTO extends ObjectDTO{
    private Integer numeroRue;
    private String nomRue;
    private String ville;
    
    public AddressDTO(){
        
    }
    
    @Override
    public String toString(){
        String result = "";
        if(nomRue == null || nomRue.isEmpty() || ville == null || ville.isEmpty()){
            return result;
        } else {
            result = numeroRue.toString() + " " + nomRue + "\n" + ville;
        }
        return result;
    }
}
