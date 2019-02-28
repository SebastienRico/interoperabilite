package com.interoperability.interoperability;

import au.com.bytecode.opencsv.CSVReader;
import com.interoperability.interoperability.objetsDTO.AdresseDTO;
import com.interoperability.interoperability.objetsDTO.ContactDTO;
import com.interoperability.interoperability.objetsDTO.RestaurantDTO;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ParserCSV {

    private final static char SEPARATOR = ';';

    private RestaurantDTO restaurant;
    private List<String[]> data = new ArrayList<String[]>();

    public ParserCSV() {
    }

    public void parsingFichier(String filePath) {
        readFile(filePath);
        processParsing();
    }

    private void readFile(String filePath) {
        FileReader fileReader = null;
        try {
            File file = new File(filePath);
            fileReader = new FileReader(file);
            CSVReader csvReader = new CSVReader(fileReader, SEPARATOR);
            String[] nextLine = null;
            
            while ((nextLine = csvReader.readNext()) != null) {
                int size = nextLine.length;
                // ligne vide
                if (size == 0) {
                    continue;
                }
                String debut = nextLine[0].trim();
                if (debut.length() == 0 && size == 1) {
                    continue;
                }
                // ligne de commentaire
                if (debut.startsWith("#")) {
                    continue;
                }
                data.add(nextLine);
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(ParserCSV.class.getName()).log(Level.SEVERE, "File not found" + filePath, ex);
        } catch (IOException ex) {
            Logger.getLogger(ParserCSV.class.getName()).log(Level.SEVERE, "csvReader canot read" + filePath, ex);
        } finally {
            try {
                fileReader.close();
            } catch (IOException ex) {
                Logger.getLogger(ParserCSV.class.getName()).log(Level.SEVERE, "fileReader canot close" + filePath, ex);
            }
        }
    }

    private void processParsing() {
        restaurant = new RestaurantDTO();
        AdresseDTO adresse = new AdresseDTO();
        ContactDTO contact = new ContactDTO();
        for (String[] oneData : data) {
            if (oneData[0] != null && !oneData[0].isEmpty()) {
                restaurant.setHoraireOuvertureRestaurant(oneData[0]);
            }
            if (oneData[1] != null && !oneData[1].isEmpty()) {
                restaurant.setMenuRestaurant(oneData[1]);
            }
            if (oneData[2] != null && !oneData[2].isEmpty()) {
                try {
                    int capacite = Integer.parseInt(oneData[2]);
                    restaurant.setCapaciteRestaurant(capacite);
                } catch (Exception ex) {
                    Logger.getLogger(ParserCSV.class.getName()).log(Level.SEVERE, "Capacity is not number", ex);
                }
            }
            if (oneData[3] != null && !oneData[3].isEmpty()) {
                try {
                    int numeroRue = Integer.parseInt(oneData[3]);
                    adresse.setNumeroRue(numeroRue);
                } catch (Exception ex) {
                    Logger.getLogger(ParserCSV.class.getName()).log(Level.SEVERE, "Address number is not number", ex);
                }
            }
            if (oneData[4] != null && !oneData[4].isEmpty()) {
                adresse.setNomRue(oneData[4]);
            }
            if (oneData[5] != null && !oneData[5].isEmpty()) {
                adresse.setVille(oneData[5]);
            }
            if (oneData[6] != null && !oneData[6].isEmpty()) {
                restaurant.setTypeRestaurant(oneData[6]);
            }
            if (oneData[7] != null && !oneData[7].isEmpty()) {
                contact.setNomPersonne(oneData[7]);
            }
            if (oneData[8] != null && !oneData[8].isEmpty()) {
                try {
                    int tel = Integer.parseInt(oneData[8]);
                    contact.setTelephoneContact(tel);
                } catch (Exception ex) {
                    Logger.getLogger(ParserCSV.class.getName()).log(Level.SEVERE, "Tel number is not number", ex);
                }
            }
            if (oneData[9] != null && !oneData[9].isEmpty()) {
                contact.setEmailContact(oneData[9]);
            }
            if (oneData[10] != null && !oneData[10].isEmpty()) {
                contact.setFaxContact(oneData[10]);
            }
            if (oneData[11] != null && !oneData[11].isEmpty()) {
                contact.setSiteWebContact(oneData[11]);
            }
            /*Integer classe = Integer.parseInt(classeStr);
            Sexe sexe = (sexeStr.equalsIgnoreCase("F")) ? FEMME : HOMME;
            Date dateNaissance = dateFormat.parse(dateNaissanceStr);*/
        }
        restaurant.setAdresseRestaurant(adresse);
        restaurant.setContactRestaurant(contact);
    }
}
