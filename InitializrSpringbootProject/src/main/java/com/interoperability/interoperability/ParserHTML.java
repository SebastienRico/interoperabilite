package com.interoperability.interoperability;
//Cinema scoop : h3 class= tribe-events-month-event-title (plusieurs) https://www.cinema-scoop.fr/seances/categorie/seances/

import com.interoperability.interoperability.objetsDTO.ActivitesDTO;
import com.interoperability.interoperability.objetsDTO.AddressDTO;
import com.interoperability.interoperability.objetsDTO.EventDTO;
import com.interoperability.interoperability.objetsDTO.ContactDTO;
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
        AddressDTO address = new AddressDTO();
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
                            address.setCity(element.getTextContent().trim());
                            this.event.setAddressEvent(address);
                        }
                        if (element.getAttribute("class").equals("wpetItemContainerContentDate")) {
                            this.event.setDateStartEvent(element.getTextContent().trim());
                            this.event.setDateEndEvent(element.getTextContent().trim());
                        }
                    }
                    Element type = (Element) e.getElementsByTagName("div").item(0);
                    this.event.setTypeEvent(type.getTextContent().trim());
                    this.event.setContactEvent(contact);
                    System.out.println("Evenement " + j);
                    System.out.println("----------");
                    System.out.println("Nom : " + this.event.getNameEvent());
                    System.out.println("Ville : " + this.event.getAddressEvent().getCity());
                    System.out.println("Date : " + this.event.getDateStartEvent());
                    System.out.println("Type : " + this.event.getTypeEvent());
                    System.out.println("----------");
                    //TODO enregistrer event
                }
            }
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
        AddressDTO address = new AddressDTO();
        address.setNumberStreet(18);
        address.setNameStreet("rue de la poste");
        address.setCity("Chambon-sur-Lignon");
        this.activity.setAddressActivity(address);
        this.activity.setNameActivity("Séance de cinéma");
        Document document;
        try {
            constructor = fabric.newDocumentBuilder();
            document = (Document) constructor.parse(fichier);
            document.normalize();
            for (int i = 0; i < document.getElementsByTagName("td").getLength(); i++) {
                e = (Element) document.getElementsByTagName("td").item(i);
                if (e.getElementsByTagName("a").getLength() != 0) {
                    this.activity.setScheduleActivity(e.getAttribute("data-day"));
                    this.activity.setDescriptionActivity(e.getElementsByTagName("a").item(1).getTextContent());
                }
                System.out.println("Seance de Cinema :");
                System.out.println("Horaire : " + this.activity.getScheduleActivity());
                System.out.println("Description : " + this.activity.getDescriptionActivity());
                System.out.println("-------------------------");
            }
        } catch (SAXException | IOException | ParserConfigurationException ex) {
            Logger.getLogger(ParserHTML.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
