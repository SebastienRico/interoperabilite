package com.interoperability.interoperability.wikidata;

import com.interoperability.interoperability.objetsDTO.ContactDTO;
import com.interoperability.interoperability.wikidata.wikidataReader.WikidataRestaurantReader;
import com.interoperability.interoperability.wikidata.wikidataWriter.WikidataContactWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.wikidata.wdtk.datamodel.interfaces.ItemDocument;
import org.wikidata.wdtk.wikibaseapi.ApiConnection;
import org.wikidata.wdtk.wikibaseapi.WbSearchEntitiesResult;
import org.wikidata.wdtk.wikibaseapi.WikibaseDataFetcher;
import org.wikidata.wdtk.wikibaseapi.apierrors.MediaWikiApiErrorException;

public class WikidataUtil {

    public static String getOwner(ContactDTO contact) {
        String owner = "";
        try {
            String contactToSearch = contact.getFirstnamePerson() + " " + contact.getNamePerson();
            List<WbSearchEntitiesResult> entities = WikidataLogger.WikibaseWbdf.searchEntities(contactToSearch);
            if (!entities.isEmpty()) {
                for (WbSearchEntitiesResult entity : entities) {
                    WikidataLogger.WikibaseWbdf.getEntityDocument(entity.getEntityId());
                    if (entity.getEntityId() != null) {
                        owner = entity.getEntityId();
                        return owner;
                    }
                }
            }
            if (owner.isEmpty()) {
                WikidataContactWriter wikidataContactWriter = new WikidataContactWriter();
                wikidataContactWriter.writeContactPage(contact);
                owner = getOwner(contact);
            }
        } catch (MediaWikiApiErrorException ex) {
            Logger.getLogger(WikidataUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return owner;
    }

    public static String checkInstanceOf(String QId) {

        String[] array1 = null;
        String[] array2 = null;

        String siteIri = "http://qanswer-svc1.univ-st-etienne.fr/index.php";
        ApiConnection con = new ApiConnection("http://qanswer-svc1.univ-st-etienne.fr/api.php");

        WikibaseDataFetcher wbdf = new WikibaseDataFetcher(con, siteIri);
        ItemDocument item = null;

        try {
            item = (ItemDocument) wbdf.getEntityDocument(QId);
        } catch (MediaWikiApiErrorException ex) {
            Logger.getLogger(WikidataRestaurantReader.class.getName()).log(Level.SEVERE, null, ex);
        }

        for (int i = 0; i < item.getStatementGroups().size(); i++) {
            //System.out.println(item.getStatementGroups().get(i).getStatements().get(0).toString());
            String test = item.getStatementGroups().get(i).getStatements().get(0).toString();
            if (test.contains("P16")) {
                //System.out.println(item.getStatementGroups().get(i).getStatements().get(0));
                String contactsplit = item.getStatementGroups().get(i).getStatements().get(0).getValue().toString();
                array1 = contactsplit.split("php");
                array2 = array1[1].split(" ");
                i = item.getStatementGroups().size();
            }
        }

        ItemDocument itemInstance = null;
        try {
            //Pour l'instant on met le QID en dur mais il faudra le passer en paramètre
            itemInstance = (ItemDocument) wbdf.getEntityDocument(array2[0]);
        } catch (MediaWikiApiErrorException ex) {
            Logger.getLogger(WikidataRestaurantReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        String instanceName = itemInstance.getLabels().get("en").getText();
        //System.out.println(instanceName);
        return instanceName;
    }
}
