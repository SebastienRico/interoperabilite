
package com.interoperability.interoperability.objetsDTO;

import lombok.Data;

@Data
public class EventDTO {
    private String typeEvenement;
    private AddressDTO adresseEvenement;
    private int capaciteEvenement;
    private String dateDebutEvenement; //Le type a definir
    private String dateFinEvenement;
    private float tarifEvenement; //Peut-etre String pour plusieurs tarifs ? Ou liste de tarifs ?
    private ContactDTO contactEvenement;
    private OrganizerDTO organisateurEvenement;
    private String nomEvenement;
    
    public EventDTO() {
        this.adresseEvenement = new AddressDTO();
        this.contactEvenement = new ContactDTO();
        this.organisateurEvenement = new OrganizerDTO();
    }
    
    
}
