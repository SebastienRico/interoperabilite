
package com.interoperability.interoperability.ObjetsDTO;

import java.sql.Date;
import lombok.Data;

@Data
public class LocationsDTO {
    private String descriptionLocation;
    private AdresseDTO adresseLocation;
    private int capaciteLocation;
    private String dateDebutLocation; //Le type a definir
    private String dateFinLocation;
    private float tarifLocation;
    private String disponibiliteLocation; //Type a changer, a voir avec les autres groupes ?
    private OrganisateurDTO organisateurLocation;
    
    public LocationsDTO() {
    }
    
    
}
