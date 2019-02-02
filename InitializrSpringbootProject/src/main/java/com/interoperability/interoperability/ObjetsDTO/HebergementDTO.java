package com.interoperability.interoperability.ObjetsDTO;

import lombok.Data;

@Data
public class HebergementDTO {

    private AdresseDTO adresseHebergement;
    private int capaciteHebergement;
    private float tarifHebergement; //Peut-etre String pour plusieurs tarifs ? Ou liste de tarifs ?
    private ContactDTO contactHebergement;
    private int etoileHebergement;
    private String horaireOuvertureHebergement; //Voir le format
    private String periodeOuvertureHebergement; //Voir le format
    private GerantDTO gerantHebergement;

    public HebergementDTO() {
    }
    
}
