package com.interoperability.interoperability.objetsDTO;

import lombok.Data;

@Data
public class ActivitesDTO {
    
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
