package com.interoperability.interoperability.wikidata.wikidataReader;

import com.interoperability.interoperability.objetsDTO.RestaurantDTO;
import com.interoperability.interoperability.wikidata.WikidataLogger;
import static com.interoperability.interoperability.wikidata.WikidataLogger.WikibaseConnexion;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.wikidata.wdtk.datamodel.interfaces.ItemDocument;
import org.wikidata.wdtk.wikibaseapi.WbSearchEntitiesResult;
import org.wikidata.wdtk.wikibaseapi.WikibaseDataFetcher;
import org.wikidata.wdtk.wikibaseapi.apierrors.MediaWikiApiErrorException;

public class WikidataRestaurantReader {
    
    public void readRestaurantPage(RestaurantDTO restaurant){
        //Example for getting information about an entity, here the example of The Laboratoire Huber Curien, Q900
        //For more examples give a look at: https://github.com/Wikidata/Wikidata-Toolkit-Examples/blob/master/src/examples/FetchOnlineDataExample.java
        
        WikibaseDataFetcher wbdf = new WikibaseDataFetcher(WikidataLogger.WikibaseConnexion, WikidataLogger.WIKIBASE_SITE_IRI);
        ItemDocument item = null;
        try {
            item = (ItemDocument) wbdf.getEntityDocument("Q1342");
        } catch (MediaWikiApiErrorException ex) {
            Logger.getLogger(WikidataRestaurantReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(item.getEntityId());
        System.out.println(item.getLabels());
        System.out.println(item.getStatementGroups());
        System.out.println(item.getDescriptions());

        //Example to search for an ID given the label
        List<WbSearchEntitiesResult> entities = null;
        try {
            entities = wbdf.searchEntities("Pierre Maret");
        } catch (MediaWikiApiErrorException ex) {
            Logger.getLogger(WikidataRestaurantReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (WbSearchEntitiesResult entity : entities){
            System.out.println(entity.getEntityId());
        }
    }
}
