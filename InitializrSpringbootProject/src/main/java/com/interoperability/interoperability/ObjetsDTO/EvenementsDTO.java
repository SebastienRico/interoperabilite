
package com.interoperability.interoperability.ObjetsDTO;

import lombok.Data;

@Data
public class EvenementsDTO {
    private String typeEvenement;
    private AdresseDTO adresseEvenement;
    private int capaciteEvenement;
    private String dateDebutEvenement; //Le type a definir
    private String dateFinEvenement;
    private float tarifEvenement; //Peut-etre String pour plusieurs tarifs ? Ou liste de tarifs ?
    private ContactDTO contactEvenement;
    private OrganisateurDTO organisateurEvenement;
    private String nomEvenement;
    
    public EvenementsDTO() {
        this.adresseEvenement = new AdresseDTO();
        this.contactEvenement = new ContactDTO();
        this.organisateurEvenement = new OrganisateurDTO();
    }
    
    
}
