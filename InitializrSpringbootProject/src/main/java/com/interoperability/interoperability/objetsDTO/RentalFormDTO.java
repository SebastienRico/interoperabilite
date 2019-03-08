package com.interoperability.interoperability.objetsDTO;
import com.interoperability.interoperability.ObjectDTO;

import lombok.Data;

/**
 * Cette classe permet de récupérer les données du formulaire de Location.
 */
@Data
public class RentalFormDTO extends ObjectDTO{

    private String descriptionLocation;
    private int capaciteLocation;
    private String dateDebutLocation;
    private String dateFinLocation;
    private float tarifLocation;
    private String disponibiliteLocation;
    
    private int numeroRue;
    private String nomRue;
    private String ville;

    private String nomPersonne;
    private String prenomPersonne;
    private int telephoneContact;
    private String emailContact;
    private String siteWebContact;

    public RentalFormDTO() {
    }
}
