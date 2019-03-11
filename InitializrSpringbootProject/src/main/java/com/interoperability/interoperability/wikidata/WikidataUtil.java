package com.interoperability.interoperability.wikidata;

import com.interoperability.interoperability.objetsDTO.ContactDTO;
import com.interoperability.interoperability.wikidata.wikidataWriter.WikidataContactWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.wikidata.wdtk.wikibaseapi.WbSearchEntitiesResult;
import org.wikidata.wdtk.wikibaseapi.apierrors.MediaWikiApiErrorException;

public class WikidataUtil {
    
    public static String getOwner(ContactDTO contact) {
        String owner = "";
        try {
            String contactToSearch = contact.getFirstnamePerson() + " " + contact.getNamePerson();
            List<WbSearchEntitiesResult> entities = WikidataLogger.WikibaseWbdf.searchEntities(contactToSearch);
            if(!entities.isEmpty()){
                for (WbSearchEntitiesResult entity : entities){
                    WikidataLogger.WikibaseWbdf.getEntityDocument(entity.getEntityId());
                    if(entity.getEntityId() != null){
                        owner = entity.getEntityId();
                        return owner;
                    }
                }
            }
            if(owner.isEmpty()){
                WikidataContactWriter wikidataContactWriter = new WikidataContactWriter();
                wikidataContactWriter.writeContactPage(contact);
                owner = getOwner(contact);
            }
        } catch (MediaWikiApiErrorException ex) {
            Logger.getLogger(WikidataUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return owner;
    }
}
