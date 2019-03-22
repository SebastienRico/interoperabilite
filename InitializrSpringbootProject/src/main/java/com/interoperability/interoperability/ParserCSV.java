package com.interoperability.interoperability;

import au.com.bytecode.opencsv.CSVReader;
import com.interoperability.interoperability.objetsDTO.ContactDTO;
import com.interoperability.interoperability.objetsDTO.RestaurantDTO;
import com.interoperability.interoperability.wikidata.WikidataFacade;
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

    private final RestaurantDTO restaurant = new RestaurantDTO();
    private final List<String[]> data = new ArrayList<>();

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
            restaurant.setNameRestaurant(file.getName().split("\\.")[0]);
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
        ContactDTO contact = new ContactDTO();
        for (String[] oneData : data) {
            if(dataAreRightFormatted(oneData)){
                restaurant.setScheduleRestaurant(oneData[0]);
                restaurant.setMenuRestaurant(oneData[1]);
                Integer capacite = Integer.parseInt(oneData[2]);
                restaurant.setCapacityRestaurant(capacite);
                restaurant.setAddressRestaurant(oneData[3]);
                restaurant.setTypeRestaurant(oneData[4]);
                String[] nameSplited = oneData[5].split(" ");
                contact.setFirstnamePerson(nameSplited[0]);
                contact.setNamePerson(nameSplited[1]);
                contact.setPhoneContact(oneData[6]);
                contact.setMailContact(oneData[7]);
                contact.setFaxContact(oneData[8]);
                contact.setWebsiteContact(oneData[9]);
                restaurant.setDescriptionRestaurant(oneData[10]);
            }
        }
        restaurant.setContactRestaurant(contact);
        WikidataFacade.writePage(restaurant);
    }

    private boolean dataAreRightFormatted(String[] oneData) {
        if (oneData[0] != null && !oneData[0].isEmpty()
                && oneData[1] != null && !oneData[1].isEmpty()
                && oneData[2] != null && !oneData[2].isEmpty()
                && oneData[3] != null && !oneData[3].isEmpty()
                && oneData[4] != null && !oneData[4].isEmpty()
                && oneData[5] != null && !oneData[5].isEmpty()
                && oneData[6] != null && !oneData[6].isEmpty()
                && oneData[7] != null && !oneData[7].isEmpty()
                && oneData[8] != null && !oneData[8].isEmpty()
                && oneData[9] != null && !oneData[9].isEmpty()
                && oneData[10] != null && !oneData[10].isEmpty()) {
            return true;
        }
        return false;
    }
}
