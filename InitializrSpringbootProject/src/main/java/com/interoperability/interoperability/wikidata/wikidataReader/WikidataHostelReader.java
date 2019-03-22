package com.interoperability.interoperability.wikidata.wikidataReader;

import com.interoperability.interoperability.objetsDTO.ContactDTO;
import com.interoperability.interoperability.objetsDTO.HostelDTO;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.wikidata.wdtk.datamodel.interfaces.ItemDocument;
import org.wikidata.wdtk.wikibaseapi.ApiConnection;
import org.wikidata.wdtk.wikibaseapi.WikibaseDataFetcher;
import org.wikidata.wdtk.wikibaseapi.apierrors.MediaWikiApiErrorException;

public class WikidataHostelReader {

    public static HostelDTO readHostelPage(String QID) {
        HostelDTO hostel = new HostelDTO();
        ContactDTO contactHostel = new ContactDTO();
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

        hostel.setNameHostel(item.getLabels().get("en").getText());
        
        String adresse = item.getStatementGroups().get(0).getStatements().get(0).getValue().toString().replaceAll("^\"|\"$", "");
        hostel.setAddressHostel(adresse);

        String price = item.getStatementGroups().get(1).getStatements().get(0).getValue().toString().replaceAll("^\"|\"$", "");
        hostel.setPriceHostel(Float.parseFloat(price));

        String stars = item.getStatementGroups().get(2).getStatements().get(0).getValue().toString().replaceAll("^\"|\"$", "");
        hostel.setStarHostel(Integer.parseInt(stars));

        hostel.setCapacityHostel(Integer.parseInt(item.getStatementGroups().get(3).getStatements().get(0).getValue().toString()));

        String timetable = item.getStatementGroups().get(5).getStatements().get(0).getValue().toString().replaceAll("^\"|\"$", "");
        hostel.setTimetableOpenHostel(timetable);

        //Get The contact Qid
        String contactsplit = item.getStatementGroups().get(6).getStatements().get(0).getValue().toString();
        array = contactsplit.split("php");
        array2 = array[1].split(" ");

        contactHostel = WikidataContactReader.readContactPage(array2[0]);
        hostel.setContactHostel(contactHostel);

        String openingPeriod = item.getStatementGroups().get(7).getStatements().get(0).getValue().toString().replaceAll("^\"|\"$", "");
        hostel.setOpeningPeriodHostel(openingPeriod);

        return hostel;
    }
}
