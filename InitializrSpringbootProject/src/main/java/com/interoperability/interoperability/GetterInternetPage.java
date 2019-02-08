/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.interoperability.interoperability;

import java.io.BufferedInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author qbiss
 */
public class GetterInternetPage extends TimerTask {

    @Override
    public void run() {
        HttpURLConnection conn;
        try {
            conn = (HttpURLConnection) new URL(
                    "http://www.office-tourisme-haut-lignon.com/").openConnection();
            conn.connect();
            BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());

            byte[] bytes = new byte[1024];
            int tmp;
            FileWriter fichier = new FileWriter("maPage.html");
            while ((tmp = bis.read(bytes)) != -1) {

                String chaine = new String(bytes, 0, tmp);

                fichier.write(chaine);
            }

            conn.disconnect();

        } catch (MalformedURLException ex) {
            Logger.getLogger(GetterInternetPage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GetterInternetPage.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    /*
    public static void affPage() throws Exception {
        HttpURLConnection conn = (HttpURLConnection) new URL(
                "http://www.office-tourisme-haut-lignon.com/").openConnection();
        conn.connect();

        BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());

        byte[] bytes = new byte[1024];
        int tmp;
        FileWriter fichier = new FileWriter("maPage.html");
        while ((tmp = bis.read(bytes)) != -1) {

            String chaine = new String(bytes, 0, tmp);

            fichier.write(chaine);
            System.out.print(chaine);
        }

        conn.disconnect();
    }*/

}
