package com.interoperability.interoperability.objetsDTO;
import com.interoperability.interoperability.ObjectDTO;

import lombok.Data;

@Data
public class ActivitesDTO extends ObjectDTO{
    
    private AddressDTO adresseActivite;
    private String nomActivite;
    private String horaireActivite; //Voir format
    private int capaciteActivite;
    private ContactDTO contactActivite;
    private String circuitActivite;
    private String trajetActivite;
    private String descriptionActivite; //Voir le format
    private float tarifActivite; //Voir format si plusieurs tarifs

    public ActivitesDTO() {
    }
}
