package com.interoperability.interoperability.objetsDTO;

import lombok.Data;

@Data
public class ContactDTO extends PersonDTO{
    private String faxContact;
    // max value 2.147.483.647 for an int (in theorie it's ok)
    private Integer phoneContact;
    private String mailContact;
    private String websiteContact;

    public ContactDTO() {
    }
    
    
}
