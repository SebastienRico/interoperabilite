package com.interoperability.interoperability.wikidata;

import com.interoperability.interoperability.ObjectDTO;
import com.interoperability.interoperability.objetsDTO.ActivitesDTO;
import com.interoperability.interoperability.objetsDTO.ContactDTO;
import com.interoperability.interoperability.objetsDTO.EventDTO;
import com.interoperability.interoperability.objetsDTO.RestaurantDTO;
import com.interoperability.interoperability.wikidata.wikidataReader.WikidataContactReader;
import com.interoperability.interoperability.wikidata.wikidataReader.WikidataRestaurantReader;
import com.interoperability.interoperability.wikidata.wikidataWriter.WikidataActivityWriter;
import com.interoperability.interoperability.wikidata.wikidataWriter.WikidataContactWriter;
import com.interoperability.interoperability.wikidata.wikidataWriter.WikidataEventWriter;
import com.interoperability.interoperability.wikidata.wikidataWriter.WikidataRestaurantWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WikidataFacade {

    public static void writePage(ObjectDTO objectDTO) {
        WikidataLogger.connectToWikibase();
        if (objectDTO instanceof RestaurantDTO) {
            WikidataRestaurantWriter wikidataRestaurantWriter = new WikidataRestaurantWriter();
            wikidataRestaurantWriter.writeRestaurantPage((RestaurantDTO) objectDTO);
        } else if (objectDTO instanceof ContactDTO) {
            WikidataContactWriter wikidataContactWriter = new WikidataContactWriter();
            wikidataContactWriter.writeContactPage((ContactDTO) objectDTO);
        } else if (objectDTO instanceof EventDTO) {
            WikidataEventWriter wikidataEventWriter = new WikidataEventWriter();
            wikidataEventWriter.writeEventPage((EventDTO) objectDTO);
        } else if (objectDTO instanceof ActivitesDTO) {
            WikidataActivityWriter wikidataActivityWriter = new WikidataActivityWriter();
            wikidataActivityWriter.writeActivityPage((ActivitesDTO) objectDTO);
        } else {
            Logger.getLogger(WikidataFacade.class.getName()).log(Level.SEVERE, "[writePage] The objectDTO has no instanceof");
        }
    }

    public static void readPage(String Qid) {
        WikidataLogger.connectToWikibase();
        String instance = WikidataUtil.checkInstanceOf(Qid);
        switch (instance) {
            case "restaurant":
                RestaurantDTO restaurant = WikidataRestaurantReader.readRestaurantPage(Qid);
                System.out.println(restaurant);
                //Appel affichage restaurant
                break;
            case "human":
                ContactDTO contact = WikidataContactReader.readContactPage(Qid);
                System.out.println(contact);
                //Appel affichage contact
                break;
        }

    }
}
