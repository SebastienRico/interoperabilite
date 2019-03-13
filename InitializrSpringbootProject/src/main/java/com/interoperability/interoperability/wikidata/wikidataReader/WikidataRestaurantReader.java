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
        
        System.out.println(item.getDescriptions().toString());
        restaurant.setDescriptionRestaurant(item.getDescriptions().get("fr").toString());
        
        //System.out.println("1er statement " + item.getStatementGroups().get(0).getStatements().get(0).getValue().toString());        
        restaurant.setAddressRestaurant(item.getStatementGroups().get(0).getStatements().get(0).getValue().toString());
        
        //System.out.println("2eme statement " + item.getStatementGroups().get(1).getStatements().get(0).getValue().toString());
        restaurant.setTypeRestaurant(item.getStatementGroups().get(1).getStatements().get(0).getValue().toString());
        
        //System.out.println("3eme statement " + item.getStatementGroups().get(2).getStatements().get(0).getValue().toString());
        restaurant.setCapacityRestaurant(Integer.parseInt(item.getStatementGroups().get(2).getStatements().get(0).getValue().toString()));
        
        //System.out.println("4eme statement is the instance of for now");
        
        //System.out.println("5eme statement " + item.getStatementGroups().get(4).getStatements().get(0).getValue().toString());
        restaurant.setMenuRestaurant(item.getStatementGroups().get(4).getStatements().get(0).getValue().toString());
        
        //System.out.println("6eme statement " + item.getStatementGroups().get(5).getStatements().get(0).getValue().toString());
        restaurant.setScheduleRestaurant(item.getStatementGroups().get(5).getStatements().get(0).getValue().toString());
        
        //Get The contact Qid
        String contactsplit = item.getStatementGroups().get(6).getStatements().get(0).getValue().toString();
        arrayRestaurant = contactsplit.split("php");
        arrayRestaurant2 = arrayRestaurant[1].split(" ");
        
        contactResto = WikidataContactReader.readContactPage(arrayRestaurant2[0]);
        //System.out.println(contactResto);
        restaurant.setContactRestaurant(contactResto);
        //System.out.println(restaurant);

        return restaurant;
    }
}
