package com.interoperability.interoperability;
//Cinema scoop : h3 class= tribe-events-month-event-title (plusieurs) https://www.cinema-scoop.fr/seances/categorie/seances/

import com.interoperability.interoperability.objetsDTO.ActivitesDTO;
import com.interoperability.interoperability.objetsDTO.AddressDTO;
import com.interoperability.interoperability.objetsDTO.EventDTO;
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

    private EventDTO evenement;
    private ActivitesDTO activite;

    public ParserHTML() {

    }
    //Evenement : div class = wpetItem resultsListItem wrapper_wpet_offer agenda > a class = resultItemDetail (nom) < > p class = wpetItemContainerContentCity (lieu) < > p class = wpetItemContainerDate (date) < > li (ce que c'est) http://www.office-tourisme-haut-lignon.com/info_pratique/agenda/

    public void parserEvenementOfficeTourisme(String path) {
        this.evenement = new EventDTO();
        File fichier = new File("agenda.html");
        DocumentBuilderFactory fabrique = DocumentBuilderFactory.newInstance();
        DocumentBuilder constructeur;
        Element e, element;
        AddressDTO adresse = new AddressDTO();
        try {
            constructeur = fabrique.newDocumentBuilder();
            Document document = (Document) constructeur.parse(fichier);
            document.normalize();
            for (int j = 0; j < document.getElementsByTagName("div").getLength(); j++) {
                e = (Element) document.getElementsByTagName("div").item(j);
                if (e.getAttribute("class").equals("wpetItemContainerContent")) {
                    this.evenement.setNameEvent(e.getElementsByTagName("a").item(0).getTextContent().trim());
                    for (int i = 0; i < e.getElementsByTagName("p").getLength(); i++) {
                        element = (Element) e.getElementsByTagName("p").item(i);
                        if (element.getAttribute("class").equals("wpetItemContainerContentCity")) {
                            adresse.setCity(element.getTextContent().trim());
                            this.evenement.setAddressEvent(adresse);
                        }
                        if (element.getAttribute("class").equals("wpetItemContainerContentDate")) {
                            this.evenement.setDateStartEvent(element.getTextContent().trim());
                            this.evenement.setDateEndEvent(element.getTextContent().trim());
                        }
                    }
                    Element type = (Element) e.getElementsByTagName("div").item(0);
                    this.evenement.setTypeEvent(type.getTextContent().trim());
                    System.out.println("Evenement " + j);
                    System.out.println("----------");
                    System.out.println("Nom : " + this.evenement.getNameEvent());
                    System.out.println("Ville : " + this.evenement.getAddressEvent().getCity());
                    System.out.println("Date : " + this.evenement.getDateStartEvent());
                    System.out.println("Type : " + this.evenement.getTypeEvent());
                    System.out.println("----------");
                    //TODO enregistrer evenement
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            Logger.getLogger(ParserHTML.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //https://www.cinema-scoop.fr/seances/categorie/seances/
    public void parserCinemaScoop(String path) {
        this.activite = new ActivitesDTO();
        File fichier = new File("cinemaScoop.html");
        DocumentBuilderFactory fabrique = DocumentBuilderFactory.newInstance();
        DocumentBuilder constructeur;
        Element e;
        AddressDTO adresse = new AddressDTO();
        adresse.setNumberStreet(18);
        adresse.setNameStreet("rue de la poste");
        adresse.setCity("Chambon-sur-Lignon");
        this.activite.setAddressActivity(adresse);
        this.activite.setNameActivity("Séance de cinéma");
        Document document;
        try {
            constructeur = fabrique.newDocumentBuilder();
            document = (Document) constructeur.parse(fichier);
            document.normalize();
            for (int i = 0; i < document.getElementsByTagName("td").getLength(); i++) {
                e = (Element) document.getElementsByTagName("td").item(i);
                if (e.getElementsByTagName("a").getLength() != 0) {
                    this.activite.setScheduleActivity(e.getAttribute("data-day"));
                    this.activite.setDescriptionActivity(e.getElementsByTagName("a").item(1).getTextContent());
                }
                System.out.println("Seance de Cinema :");
                System.out.println("Horaire : " + this.activite.getScheduleActivity());
                System.out.println("Description : " + this.activite.getDescriptionActivity());
                System.out.println("-------------------------");
            }
        } catch (SAXException | IOException | ParserConfigurationException ex) {
            Logger.getLogger(ParserHTML.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
