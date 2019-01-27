package com.interoperability.interoperability;

import au.com.bytecode.opencsv.CSVReader;
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

    private ObjetDTO objetDTO;
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
            Logger.getLogger(ParserCSV.class.getName()).log(Level.SEVERE, "File not found", ex);
        } catch (IOException ex) {
            Logger.getLogger(ParserCSV.class.getName()).log(Level.SEVERE, "csvReader canot read", ex);
        } finally {
            try {
                fileReader.close();
            } catch (IOException ex) {
                Logger.getLogger(ParserCSV.class.getName()).log(Level.SEVERE, "fileReader canot close", ex);
            }
        }
    }

    private void processParsing() {
        for (String[] oneData : data) {
            //objetDTO.setNom(oneData[0]);
            String nom = oneData[0];
            String prenom = oneData[1];
            String classeStr = oneData[2];
            String sexeStr = oneData[3];
            String dateNaissanceStr = oneData[4];
            String adresse = oneData[5];

            /*Integer classe = Integer.parseInt(classeStr);
            Sexe sexe = (sexeStr.equalsIgnoreCase("F")) ? FEMME : HOMME;
            Date dateNaissance = dateFormat.parse(dateNaissanceStr);*/
        }
    }
}
