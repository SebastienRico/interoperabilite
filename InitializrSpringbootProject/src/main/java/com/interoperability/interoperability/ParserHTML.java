package com.interoperability.interoperability;
//Cinema scoop : h3 class= tribe-events-month-event-title (plusieurs) https://www.cinema-scoop.fr/seances/categorie/seances/

import com.interoperability.interoperability.objetsDTO.ActivitesDTO;
import com.interoperability.interoperability.objetsDTO.EventDTO;
import com.interoperability.interoperability.objetsDTO.ContactDTO;
import com.interoperability.interoperability.wikidata.WikidataFacade;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

public class ParserHTML {

    private EventDTO event;
    private ActivitesDTO activity;
    
    public ParserHTML() {

    }
    //Evenement : div class = wpetItem resultsListItem wrapper_wpet_offer agenda > a class = resultItemDetail (nom) < > p class = wpetItemContainerContentCity (lieu) < > p class = wpetItemContainerDate (date) < > li (ce que c'est) http://www.office-tourisme-haut-lignon.com/info_pratique/agenda/

    public void parserEvenementOfficeTourisme(String path) {
        this.event = new EventDTO();
        File file = new File("agenda.html");
        DocumentBuilderFactory fabric = DocumentBuilderFactory.newInstance();
        DocumentBuilder constructor;
        Element e, element;
        String address = "";
        ContactDTO contact = new ContactDTO();
        contact.setNamePerson("Office du Tourisme");
        contact.setWebsiteContact("http://www.office-tourisme-haut-lignon.com/");
        try {
            constructor = fabric.newDocumentBuilder();
            Document document = (Document) constructor.parse(file);
            document.normalize();
            for (int j = 0; j < document.getElementsByTagName("div").getLength(); j++) {
                e = (Element) document.getElementsByTagName("div").item(j);
                if (e.getAttribute("class").equals("wpetItemContainerContent")) {
                    this.event.setNameEvent(e.getElementsByTagName("a").item(0).getTextContent().trim());
                    for (int i = 0; i < e.getElementsByTagName("p").getLength(); i++) {
                        element = (Element) e.getElementsByTagName("p").item(i);
                        if (element.getAttribute("class").equals("wpetItemContainerContentCity")) {
                            address += element.getTextContent().trim();
                            this.event.setAddressEvent(address);
                        }
                        if (element.getAttribute("class").equals("wpetItemContainerContentDate")) {
                            this.event.setDateStartEvent(element.getTextContent().trim());
                            this.event.setDateEndEvent(element.getTextContent().trim());
                        }
                    }
                    Element type = (Element) e.getElementsByTagName("div").item(0);
                    if(type.getTextContent() != null){
                        this.event.setTypeEvent(type.getTextContent().trim());
                    } else {
                        this.event.setTypeEvent(" ");
                    }
                    this.event.setContactEvent(contact);
                    WikidataFacade.writePage(event);
                    System.out.println("Evenement enregistré");
                }
            }
            System.out.println("Fin de l'enregistrement d'evenement");
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            Logger.getLogger(ParserHTML.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //https://www.cinema-scoop.fr/seances/categorie/seances/
    public void parserCinemaScoop(String path) {
        this.activity = new ActivitesDTO();
        File fichier = new File("cinemaScoop.html");
        DocumentBuilderFactory fabric = DocumentBuilderFactory.newInstance();
        DocumentBuilder constructor;
        Element e;
        ContactDTO contact = new ContactDTO();
        contact.setNamePerson("Cinema Scoop");
        contact.setWebsiteContact("https://www.cinema-scoop.fr/");
        contact.setPhoneContact("0471597937");
        this.activity.setContactActivity(contact);
        String address = "";
        address += "18";
        address += "rue de la poste";
        address += "Chambon-sur-Lignon";
        this.activity.setAddressActivity(address);
        this.activity.setDescriptionActivity("Séance de cinéma");
        this.activity.setCapacityActivity(125);
        Document document;
        try {
            constructor = fabric.newDocumentBuilder();
            document = (Document) constructor.parse(fichier);
            document.normalize();
            for (int i = 0; i < document.getElementsByTagName("td").getLength(); i++) {
                e = (Element) document.getElementsByTagName("td").item(i);
                if (e.getElementsByTagName("a").getLength() != 0) {
                    this.activity.setScheduleActivity(e.getAttribute("data-day"));
                    this.activity.setNameActivity(e.getElementsByTagName("a").item(1).getTextContent());
                }
                WikidataFacade.writePage(activity);
            }
        } catch (SAXException | IOException | ParserConfigurationException ex) {
            Logger.getLogger(ParserHTML.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
