
package com.interoperability.interoperability.objetsDTO;
import com.interoperability.interoperability.ObjectDTO;

import lombok.Data;

@Data
public class EventDTO extends ObjectDTO{
    private String typeEvent;
    private String addressEvent;
    private Integer capacityEvent;
    private String dateStartEvent; //Type to define yet
    private String dateEndEvent;
    private Float priceEvent; //Perhaps String for multiple prices ? Or prices list ?
    private ContactDTO contactEvent;
    private String nameEvent;
    
    public EventDTO() {
        this.contactEvent = new ContactDTO();
    }
    
    
}
