
package com.interoperability.interoperability.objetsDTO;
import com.interoperability.interoperability.ObjectDTO;

import lombok.Data;

@Data
public class PersonDTO extends ObjectDTO{
    private String namePerson;
    private String firstnamePerson;

    public PersonDTO() {
    }
    
    
}
