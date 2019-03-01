
package com.interoperability.interoperability.objetsDTO;

import lombok.Data;

@Data
public class ContactDTO extends PersonDTO{
    private String nomContact;
    private String faxContact;
    // max value 2.147.483.647 for an int (in theorie it's ok)
    private Integer telephoneContact;
    private String emailContact;
    private String siteWebContact;

    public ContactDTO() {
    }
    
    @Override
    public String toString(){
        String result = "";
        if(nomContact != null && !nomContact.isEmpty()){
            result = nomContact;
            if(telephoneContact != null && !telephoneContact.toString().isEmpty()){
                result = result + "\n" + telephoneContact.toString();
            }
            if(faxContact != null && !faxContact.isEmpty()){
                result = result + "\n" + faxContact;
            }
            if(emailContact != null && !emailContact.isEmpty()){
                result = result + "\n" + emailContact;
            }
            if(siteWebContact != null && !siteWebContact.isEmpty()){
                result = result + "\n" + siteWebContact;
            }
        }
        return result;
    }
}
