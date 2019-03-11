package com.interoperability.interoperability.objetsDTO;

import lombok.Data;

@Data
public class ContactDTO extends PersonDTO{
    private String nameContact;
    private String faxContact;
    private String phoneContact;
    private String mailContact;
    private String websiteContact;

    public ContactDTO() {
    }
    
    
}
