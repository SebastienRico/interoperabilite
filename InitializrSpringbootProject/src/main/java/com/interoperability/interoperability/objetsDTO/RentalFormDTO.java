package com.interoperability.interoperability.objetsDTO;
import com.interoperability.interoperability.ObjectDTO;

import lombok.Data;

/**
 * Cette classe permet de récupérer les données du formulaire de Location.
 */
@Data
public class RentalFormDTO extends ObjectDTO{

    private String descriptionRent;
    private int capacityRent;
    private String dateStartRent;
    private String dateEndRent;
    private float priceRent;
    private String disponibilityRent;
    
    private int numberStreet;
    private String nameStreet;
    private String city;

    private String namePerson;
    private String firstnamePerson;
    private String phoneContact;
    private String mailContact;
    private String websiteContact;

    public RentalFormDTO() {
    }
}
