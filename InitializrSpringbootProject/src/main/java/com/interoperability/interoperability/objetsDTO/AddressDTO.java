package com.interoperability.interoperability.objetsDTO;
import com.interoperability.interoperability.ObjectDTO;

import lombok.Data;

@Data
public class AddressDTO extends ObjectDTO{
    private Integer numberStreet;
    private String nameStreet;
    private String city;
    
    public AddressDTO(){
        
    }
    
    @Override
    public String toString(){
        String result = "";
        if(nameStreet == null || nameStreet.isEmpty() || city == null || city.isEmpty()){
            return result;
        } else {
            StringBuilder sb = new StringBuilder();
            result = numberStreet.toString() + " " + nameStreet + " " + city;
        }
        return result;
    }
}
