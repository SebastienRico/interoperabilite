package com.interoperability.interoperability.ObjetsDTO;
import lombok.Data;

@Data
public class ActivitesDTO {
    
    private AdresseDTO adresseActivite;
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
