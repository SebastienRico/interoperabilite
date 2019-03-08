package com.interoperability.interoperability.objetsDTO;

import lombok.Data;

@Data
public class ContactDTO extends PersonDTO{
    private String nameContact;
    private String faxContact;
    // max value 2.147.483.647 for an int (in theorie it's ok)
    private int phoneContact;
    private String mailContact;
    private String websiteContact;

    public ContactDTO() {
    }
    
    
}
