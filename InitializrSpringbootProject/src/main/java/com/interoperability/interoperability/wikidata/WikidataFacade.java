package com.interoperability.interoperability.wikidata;

import com.interoperability.interoperability.ObjectDTO;
import com.interoperability.interoperability.objetsDTO.RestaurantDTO;
import com.interoperability.interoperability.wikidata.wikidataReader.WikidataRestaurantReader;
import com.interoperability.interoperability.wikidata.wikidataWriter.WikidataRestaurantWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WikidataFacade {
    
    public static void writePage(ObjectDTO objectDTO){
        if(objectDTO instanceof RestaurantDTO){
                WikidataRestaurantWriter.writeRestaurantPage((RestaurantDTO) objectDTO);
        } else {
            Logger.getLogger(WikidataFacade.class.getName()).log(Level.SEVERE, "The objectDTO has no instanceof");
        }
    }
    
    public static void readPage(ObjectDTO objectDTO){
        if(objectDTO instanceof RestaurantDTO){
                //WikidataRestaurantReader.readRestaurantPage((RestaurantDTO) objectDTO);
        } else {
            Logger.getLogger(WikidataFacade.class.getName()).log(Level.SEVERE, "The objectDTO has no instanceof");
        }
    }
}
