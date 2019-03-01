package com.interoperability.interoperability.objetsDTO;

import lombok.Data;

@Data
public class TouristOfficeDTO {
    private AddressDTO adresseOffice;
    private String periodeOuvertureOffice; //Voir format
    private String horaireOuvertureOffice; //Voir format
    
    public TouristOfficeDTO(){
        
    }
}
