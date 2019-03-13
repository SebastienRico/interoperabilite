package com.interoperability.interoperability.wikidata.wikidataReader;

import com.interoperability.interoperability.objetsDTO.ContactDTO;
import com.interoperability.interoperability.objetsDTO.RestaurantDTO;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.wikidata.wdtk.datamodel.interfaces.ItemDocument;
import org.wikidata.wdtk.wikibaseapi.ApiConnection;
import org.wikidata.wdtk.wikibaseapi.WikibaseDataFetcher;
import org.wikidata.wdtk.wikibaseapi.apierrors.MediaWikiApiErrorException;

public class WikidataRestaurantReader {

    public static RestaurantDTO readRestaurantPage(String Qid) {
        RestaurantDTO restaurant = new RestaurantDTO();
        ContactDTO contactResto = new ContactDTO();
        String[] arrayRestaurant = null;
        String[] arrayRestaurant2 = null;

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
        
        //System.out.println("Nom resto " + item.getLabels().get("en").getText());
        restaurant.setNameRestaurant(item.getLabels().get("en").getText());
        
        //System.out.println(item.getDescriptions().toString());
        restaurant.setDescriptionRestaurant(item.getDescriptions().get("fr").getText());
        
        String adresse = item.getStatementGroups().get(0).getStatements().get(0).getValue().toString().replaceAll("^\"|\"$", "");
        restaurant.setAddressRestaurant(adresse);
        
        String typeResto = item.getStatementGroups().get(1).getStatements().get(0).getValue().toString().replaceAll("^\"|\"$", "");
        restaurant.setTypeRestaurant(typeResto);
        
        restaurant.setCapacityRestaurant(Integer.parseInt(item.getStatementGroups().get(2).getStatements().get(0).getValue().toString()));
        
        String menu = item.getStatementGroups().get(4).getStatements().get(0).getValue().toString().replaceAll("^\"|\"$", "");
        restaurant.setMenuRestaurant(menu);
        
        String schedule = item.getStatementGroups().get(5).getStatements().get(0).getValue().toString().replaceAll("^\"|\"$", "");
        restaurant.setScheduleRestaurant(schedule);
        
        //Get The contact Qid
        String contactsplit = item.getStatementGroups().get(6).getStatements().get(0).getValue().toString();
        arrayRestaurant = contactsplit.split("php");
        arrayRestaurant2 = arrayRestaurant[1].split(" ");
        
        contactResto = WikidataContactReader.readContactPage(arrayRestaurant2[0]);
        restaurant.setContactRestaurant(contactResto);

        return restaurant;
    }
}
