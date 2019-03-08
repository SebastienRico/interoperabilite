package com.interoperability.interoperability.objetsDTO;
import com.interoperability.interoperability.ObjectDTO;

import lombok.Data;

@Data
public class ActivitesDTO extends ObjectDTO{
    
    private AddressDTO addressActivity;
    private String nameActivity;
    private String scheduleActivity; //See format
    private int capacityActivity;
    private ContactDTO contactActivity;
    private String circuitIDActivity;
    private String rideActivity;
    private String descriptionActivity; //See format
    private float priceActivity; //See format if multiple prices

    public ActivitesDTO() {
    }
}
