package com.interoperability.interoperability.wikidata;

import com.interoperability.interoperability.ObjectDTO;
import com.interoperability.interoperability.objetsDTO.ActivitesDTO;
import com.interoperability.interoperability.objetsDTO.ContactDTO;
import com.interoperability.interoperability.objetsDTO.EventDTO;
import com.interoperability.interoperability.objetsDTO.HostelDTO;
import com.interoperability.interoperability.objetsDTO.PersonDTO;
import com.interoperability.interoperability.objetsDTO.RentDTO;
import com.interoperability.interoperability.objetsDTO.RestaurantDTO;
import com.interoperability.interoperability.wikidata.wikidataReader.WikidataRestaurantReader;
import com.interoperability.interoperability.wikidata.wikidataWriter.WikidataContactWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.wikidata.wdtk.datamodel.helpers.Datamodel;
import org.wikidata.wdtk.datamodel.interfaces.ItemDocument;
import org.wikidata.wdtk.datamodel.interfaces.ItemIdValue;
import org.wikidata.wdtk.wikibaseapi.ApiConnection;
import org.wikidata.wdtk.wikibaseapi.WbSearchEntitiesResult;
import org.wikidata.wdtk.wikibaseapi.WikibaseDataFetcher;
import org.wikidata.wdtk.wikibaseapi.apierrors.MediaWikiApiErrorException;

public class WikidataUtil {

    public static String getOwner(ContactDTO contact) {
        String owner = "";
        try {
            String contactToSearch = contact.getNamePerson();
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

    public static ItemIdValue getObjectItemIdValue(ObjectDTO object) {
        ItemIdValue noid = ItemIdValue.NULL;
        try {
            String objectToSearch = "";
            if (object instanceof ActivitesDTO) {
                ActivitesDTO activity = (ActivitesDTO) object;
                objectToSearch = activity.getNameActivity();
            } else if (object instanceof RestaurantDTO) {
                RestaurantDTO restaurant = (RestaurantDTO) object;
                objectToSearch = restaurant.getNameRestaurant();
            } else if (object instanceof HostelDTO) {
                HostelDTO hostel = (HostelDTO) object;
                objectToSearch = hostel.getNameHostel();
            } else if (object instanceof EventDTO) {
                EventDTO event = (EventDTO) object;
                objectToSearch = event.getNameEvent();
            } else if (object instanceof RentDTO) {
                RentDTO rent = (RentDTO) object;
                objectToSearch = rent.getDescriptionRent();
            } else if (object instanceof ContactDTO) {
                ContactDTO contact = (ContactDTO) object;
                objectToSearch = contact.getNamePerson();
            }
            List<WbSearchEntitiesResult> entities = WikidataLogger.WikibaseWbdf.searchEntities(objectToSearch);
            if (!entities.isEmpty()) {
                for (WbSearchEntitiesResult entity : entities) {
                    WikidataLogger.WikibaseWbdf.getEntityDocument(entity.getEntityId());
                    if (entity.getEntityId() != null) {
                        noid = Datamodel.makeWikidataItemIdValue​(entity.getEntityId());
                        return noid;
                    }
                }
            }
        } catch (MediaWikiApiErrorException ex) {
            Logger.getLogger(WikidataUtil.class.getName()).log(Level.SEVERE, "Cannot find ItemIdValue", ex);
        }
        return noid;
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
            Logger.getLogger(WikidataRestaurantReader.class.getName()).log(Level.SEVERE, "Cannot find itemDocument for QId : " + QId, ex);
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
            Logger.getLogger(WikidataRestaurantReader.class.getName()).log(Level.SEVERE, "Cannot find itemDocument for QId : " + array2[0], ex);
        }
        String instanceName = itemInstance.getLabels().get("en").getText();
        //System.out.println(instanceName);
        return instanceName;
    }
}
