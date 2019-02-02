package com.interoperability.interoperability.ObjetsDTO;

import lombok.Data;

@Data
public class OfficeDeTourismeDTO {
    private AdresseDTO adresseOffice;
    private String periodeOuvertureOffice; //Voir format
    private String horaireOuvertureOffice; //Voir format
    
    public OfficeDeTourismeDTO(){
        
    }
}
