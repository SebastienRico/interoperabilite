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
            item = (ItemDocument) wbdf.getEntityDocument(Qid);
        } catch (MediaWikiApiErrorException ex) {
            Logger.getLogger(WikidataRestaurantReader.class.getName()).log(Level.SEVERE, null, ex);
        }

        restaurant.setNameRestaurant(item.getLabels().get("en").getText());

        restaurant.setDescriptionRestaurant(item.getDescriptions().get("fr").getText());

        for (int i = 0; i < item.getStatementGroups().size(); i++) {
            String statement = item.getStatementGroups().get(i).getStatements().get(0).toString();
            if (statement.contains("P1076")) {
                String adresse = item.getStatementGroups().get(i).getStatements().get(0).getValue().toString().replaceAll("^\"|\"$", "");
                restaurant.setAddressRestaurant(adresse);
            }
            if (statement.contains("P1077")) {
                String typeResto = item.getStatementGroups().get(i).getStatements().get(0).getValue().toString().replaceAll("^\"|\"$", "");
                restaurant.setTypeRestaurant(typeResto);
            }
            if (statement.contains("P1064")) {
                restaurant.setCapacityRestaurant(Integer.parseInt(item.getStatementGroups().get(i).getStatements().get(0).getValue().toString()));
            }
            if (statement.contains("P1072")) {
                String menu = item.getStatementGroups().get(i).getStatements().get(0).getValue().toString().replaceAll("^\"|\"$", "");
                restaurant.setMenuRestaurant(menu);
            }
            if (statement.contains("P1073")) {
                String schedule = item.getStatementGroups().get(i).getStatements().get(0).getValue().toString().replaceAll("^\"|\"$", "");
                restaurant.setScheduleRestaurant(schedule);
            }
            if (statement.contains("P61")) {
                //Get The contact Qid
                String contactsplit = item.getStatementGroups().get(i).getStatements().get(0).getValue().toString();
                arrayRestaurant = contactsplit.split("php");
                arrayRestaurant2 = arrayRestaurant[1].split(" ");

                contactResto = WikidataContactReader.readContactPage(arrayRestaurant2[0]);
                restaurant.setContactRestaurant(contactResto);
            }
        }

        return restaurant;
    }
}
