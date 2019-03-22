
package com.interoperability.interoperability.objetsDTO;
import com.interoperability.interoperability.ObjectDTO;

import lombok.Data;

@Data
public class RentDTO extends ObjectDTO{
    private String nameRent;
    private String descriptionRent;
    private String addressRent;
    private Integer capacityRent;
    private String dateStartRent; //Type to define yet
    private String dateEndRent;
    private Float priceRent;
    private String disponibilityRent; //Type to change, see with other groups ?
    private ContactDTO contactRent;
    
    public RentDTO() {
    }
    
}
