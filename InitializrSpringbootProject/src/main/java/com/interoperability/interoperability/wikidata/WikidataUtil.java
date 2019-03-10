package com.interoperability.interoperability.wikidata;

import com.interoperability.interoperability.objetsDTO.ContactDTO;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.wikidata.wdtk.datamodel.interfaces.ItemDocument;
import org.wikidata.wdtk.wikibaseapi.WbSearchEntitiesResult;
import org.wikidata.wdtk.wikibaseapi.apierrors.MediaWikiApiErrorException;

public class WikidataUtil {
    
    public static String getOwner(ContactDTO contact) {
        String owner = "";
        try {
            List<WbSearchEntitiesResult> entities = WikidataLogger.WikibaseWbdf.searchEntities(contact.getFirstnamePerson() + " " + contact.getNamePerson());
            for (WbSearchEntitiesResult entity : entities){
                ItemDocument ent = (ItemDocument) WikidataLogger.WikibaseWbdf.getEntityDocument(entity.getEntityId());
                if(entity.getEntityId() != null){
                    owner = entity.getEntityId();
                } else {
                    // create a specific page for this person and recall this methode
                    owner = contact.toString();
                }
            }
        } catch (MediaWikiApiErrorException ex) {
            Logger.getLogger(WikidataUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return owner;
    }
}
