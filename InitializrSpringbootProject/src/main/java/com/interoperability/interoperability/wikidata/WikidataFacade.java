package com.interoperability.interoperability.wikidata;

import com.interoperability.interoperability.ObjectDTO;
import com.interoperability.interoperability.objetsDTO.ActivitesDTO;
import com.interoperability.interoperability.objetsDTO.ContactDTO;
import com.interoperability.interoperability.objetsDTO.EventDTO;
import com.interoperability.interoperability.objetsDTO.HostelDTO;
import com.interoperability.interoperability.objetsDTO.RentDTO;
import com.interoperability.interoperability.objetsDTO.RestaurantDTO;
import com.interoperability.interoperability.wikidata.wikidataReader.WikidataActivitiesReader;
import com.interoperability.interoperability.wikidata.wikidataReader.WikidataContactReader;
import com.interoperability.interoperability.wikidata.wikidataReader.WikidataEventReader;
import com.interoperability.interoperability.wikidata.wikidataReader.WikidataHostelReader;
import com.interoperability.interoperability.wikidata.wikidataReader.WikidataRentReader;
import com.interoperability.interoperability.wikidata.wikidataReader.WikidataRestaurantReader;
import com.interoperability.interoperability.wikidata.wikidataWriter.WikidataActivityWriter;
import com.interoperability.interoperability.wikidata.wikidataWriter.WikidataContactWriter;
import com.interoperability.interoperability.wikidata.wikidataWriter.WikidataEventWriter;
import com.interoperability.interoperability.wikidata.wikidataWriter.WikidataHostelWriter;
import com.interoperability.interoperability.wikidata.wikidataWriter.WikidataRestaurantWriter;
import com.interoperability.interoperability.wikidata.wikidataWriter.WikidataRentWriter;
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
        } else if (objectDTO instanceof RentDTO) {
            WikidataRentWriter wikidataRentWriter = new WikidataRentWriter();
            wikidataRentWriter.writeRentPage((RentDTO) objectDTO);
        } else if (objectDTO instanceof HostelDTO) {
            WikidataHostelWriter wikidataHostelWriter = new WikidataHostelWriter();
            wikidataHostelWriter.writeHostelPage((HostelDTO) objectDTO);
        } else {
            Logger.getLogger(WikidataFacade.class.getName()).log(Level.SEVERE, "The objectDTO has no instanceof");
        }
    }

    public static ObjectDTO readPage(String Qid) {
        ObjectDTO objectToShow = new ObjectDTO();
        WikidataLogger.connectToWikibase();
        String instance = WikidataUtil.checkInstanceOf(Qid);
        switch (instance) {
            case "restaurant":
                RestaurantDTO restaurant = WikidataRestaurantReader.readRestaurantPage(Qid);
                System.out.println(restaurant);
                objectToShow = restaurant;
                break;
            case "human":
                ContactDTO contact = WikidataContactReader.readContactPage(Qid);
                System.out.println(contact);
                objectToShow = contact;
                break;
            case "event":
                EventDTO event = WikidataEventReader.readEventPage(Qid);
                System.out.println(event);
                objectToShow = event;
                break;
            case "activity":
                ActivitesDTO activity = WikidataActivitiesReader.readActivitiesPage(Qid);
                System.out.println(activity);
                objectToShow = activity;
                break;
            case "location":
                RentDTO rental = WikidataRentReader.readRentPage(Qid);
                System.out.println(rental);
                objectToShow = rental;
                break;
            case "hostel":
                HostelDTO hostel = WikidataHostelReader.readHostelPage(Qid);
                System.out.println(hostel);
                objectToShow = hostel;
                break;
        }
        return objectToShow;
    }
}
