package com.interoperability.interoperability.wikidata;

import static com.interoperability.interoperability.wikidata.WikidataLogger.WikibaseConnexion;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.wikidata.wdtk.datamodel.interfaces.ItemDocument;
import org.wikidata.wdtk.wikibaseapi.ApiConnection;
import org.wikidata.wdtk.wikibaseapi.WbSearchEntitiesResult;
import org.wikidata.wdtk.wikibaseapi.WikibaseDataFetcher;
import org.wikidata.wdtk.wikibaseapi.apierrors.MediaWikiApiErrorException;

public class WikidataReader {
    
    public static void readWikidataPage(){
        //Example for getting information about an entity, here the example of The Laboratoire Huber Curien, Q900
        //For more examples give a look at: https://github.com/Wikidata/Wikidata-Toolkit-Examples/blob/master/src/examples/FetchOnlineDataExample.java
        /*
        
        */
        String siteIri = "http://qanswer-svc1.univ-st-etienne.fr/index.php";
        ApiConnection con = new ApiConnection("http://qanswer-svc1.univ-st-etienne.fr/api.php");
        
        WikibaseDataFetcher wbdf = new WikibaseDataFetcher(con, siteIri);
        ItemDocument item = null;
        try {
            item = (ItemDocument) wbdf.getEntityDocument("Q50");
        } catch (MediaWikiApiErrorException ex) {
            Logger.getLogger(WikidataReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Entity ID " + item.getEntityId());
        System.out.println("Labels " + item.getLabels());
        System.out.println("StatementGroup " + item.getStatementGroups().get(0).getStatements().get(0).getValue());
        System.out.println("Description "+ item.getDescriptions());

        //Example to search for an ID given the label
        //Useless ?
        List<WbSearchEntitiesResult> entities = null;
        try {
            entities = wbdf.searchEntities("Pierre Maret");
        } catch (MediaWikiApiErrorException ex) {
            Logger.getLogger(WikidataReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (WbSearchEntitiesResult entity : entities){
            System.out.println(entity.getEntityId());
        }
    }
}
