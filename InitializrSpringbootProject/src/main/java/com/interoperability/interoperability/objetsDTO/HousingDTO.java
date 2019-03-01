package com.interoperability.interoperability.objetsDTO;

import lombok.Data;

@Data
public class HousingDTO {

    private AddressDTO adresseHebergement;
    private int capaciteHebergement;
    private float tarifHebergement; //Peut-etre String pour plusieurs tarifs ? Ou liste de tarifs ?
    private ContactDTO contactHebergement;
    private int etoileHebergement;
    private String horaireOuvertureHebergement; //Voir le format
    private String periodeOuvertureHebergement; //Voir le format
    private ManagerDTO gerantHebergement;

    public HousingDTO() {
    }
    
}
