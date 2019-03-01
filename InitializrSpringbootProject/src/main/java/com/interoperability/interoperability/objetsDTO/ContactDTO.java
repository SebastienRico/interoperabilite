package com.interoperability.interoperability.objetsDTO;

import lombok.Data;

@Data
public class ContactDTO extends PersonDTO{
    private String nomContact;
    private String faxContact;
    // max value 2.147.483.647 for an int (in theorie it's ok)
    private int telephoneContact;
    private String emailContact;
    private String siteWebContact;

    public ContactDTO() {
    }
    
    
}
