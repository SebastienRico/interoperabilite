
package com.interoperability.interoperability.objetsDTO;
import com.interoperability.interoperability.ObjectDTO;

import lombok.Data;

@Data
public class EventDTO extends ObjectDTO{
    private String typeEvent;
    private String addressEvent;
    private int capacityEvent;
    private String dateStartEvent; //Type to define yet
    private String dateEndEvent;
    private float priceEvent; //Perhaps String for multiple prices ? Or prices list ?
    private ContactDTO contactEvent;
    private OrganizerDTO organizerEvent;
    private String nameEvent;
    
    public EventDTO() {
        this.contactEvent = new ContactDTO();
        this.organizerEvent = new OrganizerDTO();
    }
    
    
}
