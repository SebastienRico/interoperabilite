package com.interoperability.interoperability.wikidata.wikidataReader;

import com.interoperability.interoperability.objetsDTO.ContactDTO;
import com.interoperability.interoperability.objetsDTO.EventDTO;
import org.wikidata.wdtk.datamodel.interfaces.ItemDocument;
import org.wikidata.wdtk.wikibaseapi.ApiConnection;
import org.wikidata.wdtk.wikibaseapi.WikibaseDataFetcher;
import org.wikidata.wdtk.wikibaseapi.apierrors.MediaWikiApiErrorException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WikidataEventReader {

    public static EventDTO readEventPage(String QID) {
        EventDTO event = new EventDTO();
        ContactDTO contactEvent = new ContactDTO();
        String[] array = null;
        String[] array2 = null;

        String siteIri = "http://qanswer-svc1.univ-st-etienne.fr/index.php";
        ApiConnection con = new ApiConnection("http://qanswer-svc1.univ-st-etienne.fr/api.php");

        WikibaseDataFetcher wbdf = new WikibaseDataFetcher(con, siteIri);
        ItemDocument item = null;

        try {
            item = (ItemDocument) wbdf.getEntityDocument(QID);
        } catch (MediaWikiApiErrorException ex) {
            Logger.getLogger(WikidataRestaurantReader.class.getName()).log(Level.SEVERE, null, ex);
        }

        //System.out.println("Nom event " + item.getLabels().get("fr").getText());
        event.setNameEvent(item.getLabels().get("fr").getText());

        String adresse = item.getStatementGroups().get(0).getStatements().get(0).getValue().toString().replaceAll("^\"|\"$", "");
        event.setAddressEvent(adresse);

        String speciality = item.getStatementGroups().get(1).getStatements().get(0).getValue().toString().replaceAll("^\"|\"$", "");
        event.setTypeEvent(speciality);

        String dateDebut = item.getStatementGroups().get(3).getStatements().get(0).getValue().toString().replaceAll("^\"|\"$", "");
        event.setDateStartEvent(dateDebut);

        String dateFin = item.getStatementGroups().get(4).getStatements().get(0).getValue().toString().replaceAll("^\"|\"$", "");
        event.setDateEndEvent(dateFin);

        String contactsplit = item.getStatementGroups().get(5).getStatements().get(0).getValue().toString();
        array = contactsplit.split("php");
        array2 = array[1].split(" ");

        contactEvent = WikidataContactReader.readContactPage(array2[0]);
        event.setContactEvent(contactEvent);
        return event;
    }
}
