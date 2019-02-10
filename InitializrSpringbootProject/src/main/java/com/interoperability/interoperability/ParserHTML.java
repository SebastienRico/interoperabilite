package com.interoperability.interoperability;
//Cinema scoop : h3 class= tribe-events-month-event-title (plusieurs) https://www.cinema-scoop.fr/seances/categorie/seances/
//marche : div class = contentstyle > p (je recup saison estivale aussi ? https://www.ville-lechambonsurlignon.fr/tourisme/les-marches-3.html#.XFlD

import com.interoperability.interoperability.ObjetsDTO.AdresseDTO;
import com.interoperability.interoperability.ObjetsDTO.EvenementsDTO;
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

//marche encore : div id = marche2937 > p > b (date) < b > (lieu) https://www.jours-de-marche.fr/43400-le-chambon-sur-lignon/
//marche toujours : div id = marcheNUMBER > p > strong (lieu) < (date) https://www.jours-de-marche.fr/43400-le-chambon-sur-lignon/
//Locaux : div class = col-md-8 col-lg-8 col-xs-12 col-sm-8 inside > h2 (lieu)(plusieurs !!! dernier !=) <> p (plusieurs) < strong (nom) <> br (ville - telephone - Capacité capacite)
//Locaux : dernier h2 : > p (plusieurs) > strong (- lieu) < (description)
//Piscine : div id = content_size > div(2) > div (nom) < > div class = contentStyle > p > strong (adresse) <<>p > strong (adresse) << >p > strong (adresse) << > p > strong (telephone) <<> p > strong > em (description) <<<> p < >p > strong (horaires) <<>p (horaires again) <<<> table > tbody> tr (deuxieme) > td (2) > span (Entrée : prix) <>span (Carte 10 entrées : prix)
public class ParserHTML {

    private EvenementsDTO evenement;

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
                if (e.getAttribute("class").equals("wpetItem resultsListItem wrapper_wpet_offer agenda")) {
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
                }
                System.out.println("Evenement " + j);
                System.out.println("----------");
                System.out.println("Nom : " + this.evenement.getNomEvenement());
                System.out.println("Ville : " + this.evenement.getAdresseEvenement().getVille());
                System.out.println("Date : " + this.evenement.getDateDebutEvenement());
                System.out.println("Type : " + this.evenement.getTypeEvenement());
                System.out.println("----------");
                //TODO enregistrer evenement
            }
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            Logger.getLogger(ParserHTML.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
