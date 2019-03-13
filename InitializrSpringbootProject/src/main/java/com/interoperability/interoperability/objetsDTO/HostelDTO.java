package com.interoperability.interoperability.objetsDTO;
import com.interoperability.interoperability.ObjectDTO;

import lombok.Data;

@Data
public class HostelDTO extends ObjectDTO{

    private String nameHostel;
    private String addressHostel;
    private Integer capacityHostel;
    private Float priceHostel; //Perhaps String for multiple prices ? Or prices list ?
    private ContactDTO contactHostel;
    private Integer starHostel;
    private String timetableOpenHostel; //See format
    private String openingPeriodHostel; //See format

    public HostelDTO() {
    }
    
}
