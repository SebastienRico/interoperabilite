package com.interoperability.interoperability.objetsDTO;

import com.interoperability.interoperability.ObjectDTO;
import lombok.Data;

@Data
public class TouristOfficeDTO extends ObjectDTO{
    private AddressDTO addressOffice;
    private String openingPeriodOffice; //Voir format
    private String openingHoursOffice; //Voir format
    
    public TouristOfficeDTO(){
        
    }
}
