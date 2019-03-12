package com.interoperability.interoperability.objetsDTO;
import com.interoperability.interoperability.ObjectDTO;

import lombok.Data;

@Data
public class HousingDTO extends ObjectDTO{

    private String addressHousing;
    private int capacityHousing;
    private float priceHousing; //Perhaps String for multiple prices ? Or prices list ?
    private ContactDTO contactHousing;
    private int starHousing;
    private String timetableOpenHousing; //See format
    private String openingPeriodHousing; //See format
    private ManagerDTO managerHousing;

    public HousingDTO() {
    }
    
}
