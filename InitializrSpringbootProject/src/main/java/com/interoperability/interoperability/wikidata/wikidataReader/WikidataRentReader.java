package com.interoperability.interoperability.wikidata.wikidataReader;

import com.interoperability.interoperability.objetsDTO.ContactDTO;
import com.interoperability.interoperability.objetsDTO.RentDTO;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.wikidata.wdtk.datamodel.interfaces.ItemDocument;
import org.wikidata.wdtk.wikibaseapi.ApiConnection;
import org.wikidata.wdtk.wikibaseapi.WikibaseDataFetcher;
import org.wikidata.wdtk.wikibaseapi.apierrors.MediaWikiApiErrorException;

public class WikidataRentReader {

    public static RentDTO readRentPage(String Qid) {
        RentDTO rental = new RentDTO();
        ContactDTO contactRental = new ContactDTO();
        String[] array = null;
        String[] array2 = null;

        String siteIri = "http://qanswer-svc1.univ-st-etienne.fr/index.php";
        ApiConnection con = new ApiConnection("http://qanswer-svc1.univ-st-etienne.fr/api.php");

        WikibaseDataFetcher wbdf = new WikibaseDataFetcher(con, siteIri);
        ItemDocument item = null;
        try {
            //Pour l'instant on met le QID en dur mais il faudra le passer en paramètre
            item = (ItemDocument) wbdf.getEntityDocument(Qid);
        } catch (MediaWikiApiErrorException ex) {
            Logger.getLogger(WikidataRestaurantReader.class.getName()).log(Level.SEVERE, null, ex);
        }

        rental.setDescriptionRent(item.getDescriptions().get("fr").getText());

        String adresse = item.getStatementGroups().get(0).getStatements().get(0).getValue().toString().replaceAll("^\"|\"$", "");
        rental.setAddressRent(adresse);
        
        String dispo = item.getStatementGroups().get(1).getStatements().get(0).getValue().toString().replaceAll("^\"|\"$", "");
        rental.setDisponibilityRent(dispo);
        
        rental.setCapacityRent(Integer.parseInt(item.getStatementGroups().get(2).getStatements().get(0).getValue().toString()));

        String startDate = item.getStatementGroups().get(4).getStatements().get(0).getValue().toString().replaceAll("^\"|\"$", "");
        rental.setDateStartRent(startDate);
        
        String endDate = item.getStatementGroups().get(5).getStatements().get(0).getValue().toString().replaceAll("^\"|\"$", "");
        rental.setDateEndRent(endDate);
        //Get The contact Qid
        String contactsplit = item.getStatementGroups().get(6).getStatements().get(0).getValue().toString();
        array = contactsplit.split("php");
        array2 = array[1].split(" ");

        contactRental = WikidataContactReader.readContactPage(array2[0]);
        rental.setContactRent(contactRental);
        return rental;
    }
}
