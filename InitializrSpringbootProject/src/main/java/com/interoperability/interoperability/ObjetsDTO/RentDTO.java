
package com.interoperability.interoperability.objetsDTO;

import java.sql.Date;
import lombok.Data;

@Data
public class RentDTO {
    private String descriptionLocation;
    private AddressDTO adresseLocation;
    private int capaciteLocation;
    private String dateDebutLocation; //Le type a definir
    private String dateFinLocation;
    private float tarifLocation;
    private String disponibiliteLocation; //Type a changer, a voir avec les autres groupes ?
    private OrganizerDTO organisateurLocation;
    
    public RentDTO() {
    }
    
    
}
