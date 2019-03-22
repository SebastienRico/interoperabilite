package com.interoperability.interoperability;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GetterInternetPage extends TimerTask {

    private ParserHTML parser;

    @Override
    public void run() {

        try {

            URL url = new URL("http://www.office-tourisme-haut-lignon.com/info_pratique/agenda/");

            URLConnection con = url.openConnection();

            InputStream input = con.getInputStream();
            int readHead = 0;
            String header = "";
            String code = "";
            try {
                OutputStream fichier = new FileOutputStream("maPage.html");
                fichier.write(("<html>").getBytes());
                try {
                    byte[] buffer = new byte[1];
                    int len;

                    // boucle de lecture/ecriture
                    while ((len = input.read(buffer)) > 0) {
                        String chaine = new String(buffer, 0, len);

                        if (readHead == 1) {
                            code = code + chaine;
                        }

                        header = header + chaine;
                        if (header.equals("</head>")) {
                            readHead = 1;
                        }

                        if (chaine.equals("\n")) {
                            header = "";
                        }
                    }
                    code = this.clearHTML(code);
                    fichier.write(code.getBytes());
                    fichier.flush();
                } finally {
                    fichier.close();
                }
            } finally {
                input.close();
            }

            parser = new ParserHTML();
            parser.parserEvenementOfficeTourisme("maPage.html");
            parser.parserCinemaScoop("maPage.html");

        } catch (MalformedURLException ex) {
            Logger.getLogger(GetterInternetPage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GetterInternetPage.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public String clearHTML(String code) {
        String text = code.replaceAll("<input[^>]*>", "");
        text = text.replaceAll("<img[^>]*>", "");
        text = text.replaceAll("&.*;", "");
        text = text.replaceAll("<scr[^\u00AE]*pt>","");
        return text;
    }

    public void readReplace(String fileName, String oldpattern, String replPattern) {
        String line;
        StringBuilder sb = new StringBuilder();
        try {
            FileInputStream fis = new FileInputStream(fileName);

            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            while ((line = reader.readLine()) != null) {

                line = line.replaceAll(oldpattern, replPattern);
                sb.append(line).append("\n");

            }
            reader.close();
            BufferedWriter out = new BufferedWriter(new FileWriter(fileName));
            out.write(sb.toString());
            out.close();
        } catch (IOException e) {
            System.err.println("------------ exception" + e);
        }
    }
}
