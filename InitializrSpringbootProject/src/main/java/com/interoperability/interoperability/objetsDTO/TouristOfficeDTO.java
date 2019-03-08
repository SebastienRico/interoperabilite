package com.interoperability.interoperability.objetsDTO;

import com.interoperability.interoperability.ObjectDTO;
import lombok.Data;

@Data
public class TouristOfficeDTO extends ObjectDTO{
    private AddressDTO adresseOffice;
    private String periodeOuvertureOffice; //Voir format
    private String horaireOuvertureOffice; //Voir format
    
    public TouristOfficeDTO(){
        
    }
}
