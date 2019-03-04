package com.interoperability.interoperability;
//Cinema scoop : h3 class= tribe-events-month-event-title (plusieurs) https://www.cinema-scoop.fr/seances/categorie/seances/
//marche : div class = contentstyle > p (je recup saison estivale aussi ? https://www.ville-lechambonsurlignon.fr/tourisme/les-marches-3.html#.XFlD

import com.interoperability.interoperability.objetsDTO.ActivitesDTO;
import com.interoperability.interoperability.objetsDTO.AdresseDTO;
import com.interoperability.interoperability.objetsDTO.EvenementsDTO;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class ParserHTML {

    private EvenementsDTO evenement;
    private ActivitesDTO activite;

    public ParserHTML() {

    }
    //Evenement : div class = wpetItem resultsListItem wrapper_wpet_offer agenda > a class = resultItemDetail (nom) < > p class = wpetItemContainerContentCity (lieu) < > p class = wpetItemContainerDate (date) < > li (ce que c'est) http://www.office-tourisme-haut-lignon.com/info_pratique/agenda/

    public void parserEvenementOfficeTourisme(String path) {
        this.evenement = new EvenementsDTO();
        File fichier = new File(path);
        DocumentBuilderFactory fabrique = DocumentBuilderFactory.newInstance();
        DocumentBuilder constructeur;
        Element e, element;
        AdresseDTO adresse = new AdresseDTO();
        try {
            constructeur = fabrique.newDocumentBuilder();
            Document document = (Document) constructeur.parse(fichier);
            document.normalize();
            for (int j = 0; j < document.getElementsByTagName("div").getLength(); j++) {
                e = (Element) document.getElementsByTagName("div").item(j);
                if (e.getAttribute("class").equals("wpetItemContainerContent")) {
                    this.evenement.setNomEvenement(e.getElementsByTagName("a").item(0).getTextContent());
                    for (int i = 0; i < e.getElementsByTagName("p").getLength(); i++) {
                        element = (Element) e.getElementsByTagName("p").item(i);
                        if (element.getAttribute("class").equals("wpetItemContainerContentCity")) {
                            adresse.setVille(element.getTextContent());
                            this.evenement.setAdresseEvenement(adresse);
                        }
                        if (element.getAttribute("class").equals("wpetItemContainerContentDate")) {
                            this.evenement.setDateDebutEvenement(element.getTextContent());
                            this.evenement.setDateFinEvenement(element.getTextContent());
                        }
                    }
                    this.evenement.setTypeEvenement(e.getElementsByTagName("li").item(0).getTextContent());
                    System.out.println("Evenement " + j);
                    System.out.println("----------");
                    System.out.println("Nom : " + this.evenement.getNomEvenement());
                    System.out.println("Ville : " + this.evenement.getAdresseEvenement().getVille());
                    System.out.println("Date : " + this.evenement.getDateDebutEvenement());
                    System.out.println("Type : " + this.evenement.getTypeEvenement());
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
        File fichier = new File(path);
        DocumentBuilderFactory fabrique = DocumentBuilderFactory.newInstance();
        DocumentBuilder constructeur;
        Element e;
        AdresseDTO adresse = new AdresseDTO();
        adresse.setNumeroRue(18);
        adresse.setNomRue("rue de la poste");
        adresse.setVille("Chambon-sur-Lignon");
        this.activite.setAdresseActivite(adresse);
        this.activite.setNomActivite("Séance de cinéma");
        Document document;
        try {
            constructeur = fabrique.newDocumentBuilder();
            document = (Document) constructeur.parse(fichier);
            document.normalize();
            for (int i = 0; i < document.getElementsByTagName("td").getLength(); i++) {
                e = (Element) document.getElementsByTagName("td").item(i);
                if (e.getElementsByTagName("a").getLength() != 0) {
                    this.activite.setHoraireActivite(e.getElementsByTagName("a").item(0).getTextContent());
                    this.activite.setDescriptionActivite(e.getElementsByTagName("a").item(1).getTextContent());
                }
            }
        } catch (SAXException | IOException | ParserConfigurationException ex) {
            Logger.getLogger(ParserHTML.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
