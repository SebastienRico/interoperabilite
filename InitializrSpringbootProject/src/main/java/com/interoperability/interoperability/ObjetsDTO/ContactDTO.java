
package com.interoperability.interoperability.ObjetsDTO;

import lombok.Data;

@Data
public class ContactDTO extends PersonneDTO{
    private String nomContact;
    private String faxContact;
    private int telephoneContact;
    private String emailContact;
    private String siteWebContact;

    public ContactDTO() {
    }
    
    
}
