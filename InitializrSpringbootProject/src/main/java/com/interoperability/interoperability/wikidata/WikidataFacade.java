package com.interoperability.interoperability.wikidata;

import com.interoperability.interoperability.ObjectDTO;
import com.interoperability.interoperability.objetsDTO.ContactDTO;
import com.interoperability.interoperability.objetsDTO.RestaurantDTO;
import com.interoperability.interoperability.wikidata.wikidataReader.WikidataRestaurantReader;
import com.interoperability.interoperability.wikidata.wikidataWriter.WikidataRestaurantWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.wikidata.wdtk.datamodel.interfaces.ItemDocument;
import org.wikidata.wdtk.wikibaseapi.WbSearchEntitiesResult;
import org.wikidata.wdtk.wikibaseapi.apierrors.MediaWikiApiErrorException;

public class WikidataFacade {
    
    public static void writePage(ObjectDTO objectDTO){
        if(objectDTO instanceof RestaurantDTO){
                WikidataRestaurantWriter wikidataRestaurantWriter = new WikidataRestaurantWriter();
                wikidataRestaurantWriter.writeRestaurantPage((RestaurantDTO) objectDTO);
        } else {
            Logger.getLogger(WikidataFacade.class.getName()).log(Level.SEVERE, "The objectDTO has no instanceof");
        }
    }
    
    public static void readPage(ObjectDTO objectDTO){
        if(objectDTO instanceof RestaurantDTO){
                WikidataRestaurantReader.readRestaurantPage();
        } else {
            Logger.getLogger(WikidataFacade.class.getName()).log(Level.SEVERE, "The objectDTO has no instanceof");
        }
    }
}
