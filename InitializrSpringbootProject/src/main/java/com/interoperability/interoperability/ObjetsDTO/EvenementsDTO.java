
package com.interoperability.interoperability.ObjetsDTO;

import java.sql.Date;
import lombok.Data;

@Data
public class EvenementsDTO {
    private String typeEvenement;
    private AdresseDTO adresseEvenement;
    private int capaciteEvenement;
    private Date dateDebutEvenement; //Le type a definir
    private Date dateFinEvenement;
    private float tarifEvenement; //Peut-etre String pour plusieurs tarifs ? Ou liste de tarifs ?
    private ContactDTO contactEvenement;
    private OrganisateurDTO organisateurEvenement;
    
    public EvenementsDTO() {
    }
    
    
}
