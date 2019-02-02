
package com.interoperability.interoperability.ObjetsDTO;

import java.sql.Date;
import lombok.Data;

@Data
public class LocauxALouerDTO {
    private String descriptionLocation;
    private AdresseDTO adresseLocation;
    private int capaciteLocation;
    private Date dateDebutLocation; //Le type a definir
    private Date dateFinLocation;
    private float tarifLocation;
    private String disponibiliteLocation; //Type a changer, a voir avec les autres groupes ?
    private OrganisateurDTO organisateurLocation;
    
    public LocauxALouerDTO() {
    }
    
    
}
