package com.interoperability.interoperability.wikidata.wikidataReader;

import com.interoperability.interoperability.objetsDTO.RestaurantDTO;
import com.interoperability.interoperability.wikidata.WikidataLogger;
import static com.interoperability.interoperability.wikidata.WikidataLogger.WikibaseConnexion;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.wikidata.wdtk.datamodel.interfaces.EntityDocument;
import org.wikidata.wdtk.datamodel.interfaces.ItemDocument;
import org.wikidata.wdtk.wikibaseapi.ApiConnection;
import org.wikidata.wdtk.wikibaseapi.WbSearchEntitiesResult;
import org.wikidata.wdtk.wikibaseapi.WikibaseDataFetcher;
import org.wikidata.wdtk.wikibaseapi.apierrors.MediaWikiApiErrorException;

public class WikidataRestaurantReader {

    public static void readRestaurantPage() {
        //Example for getting information about an entity, here the example of The Laboratoire Huber Curien, Q900
        //For more examples give a look at: https://github.com/Wikidata/Wikidata-Toolkit-Examples/blob/master/src/examples/FetchOnlineDataExample.java
        /*
        
         */
        String siteIri = "http://qanswer-svc1.univ-st-etienne.fr/index.php";
        ApiConnection con = new ApiConnection("http://qanswer-svc1.univ-st-etienne.fr/api.php");

        WikibaseDataFetcher wbdf = new WikibaseDataFetcher(con, siteIri);
        ItemDocument item = null;
        try {
            item = (ItemDocument) wbdf.getEntityDocument("Q1570");
        } catch (MediaWikiApiErrorException ex) {
            Logger.getLogger(WikidataRestaurantReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        //System.out.println("Entity ID " + item.getEntityId());
        //System.out.println("Labels " + item.getLabels());
        System.out.println("StatementGroup " + item.getEntityId());
        String[] arrayRestaurant = null;
        String[] arrayRestaurant2 = null;

        System.out.println(item.getStatementGroups().get(0).getStatements());
        //String linkRestaurant = item.getStatementGroups().get(0).getStatements().get(6).getValue().toString();
       // arrayRestaurant = linkRestaurant.split("php");
        //arrayRestaurant2 = arrayRestaurant[1].split(" ");
        //System.out.println("Le premier statement de Q1550 est " + arrayRestaurant2[0]);

        //Permet de regarder l'instance
        /*EntityDocument item2 = null;
        try {
            item2 = wbdf.getEntityDocument(arrayRestaurant2[0]);
        } catch (MediaWikiApiErrorException ex) {
            Logger.getLogger(WikidataRestaurantReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (item instanceof ItemDocument) {
            String restaurantName = ((ItemDocument) item2).getLabels().get("en").getText();
            System.out.println("The owner is " + restaurantName);
        }

        //System.out.println("Description "+ item.getDescriptions());
        //Example to search for an ID given the label
        //Useless ?
        /*List<WbSearchEntitiesResult> entities = null;
        try {
            entities = wbdf.searchEntities("Pierre Maret");
        } catch (MediaWikiApiErrorException ex) {
            Logger.getLogger(WikidataRestaurantReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (WbSearchEntitiesResult entity : entities){
            System.out.println(entity.getEntityId());
        }*/
    }
}
