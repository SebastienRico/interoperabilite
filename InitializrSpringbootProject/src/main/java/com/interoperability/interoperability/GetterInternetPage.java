/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.interoperability.interoperability;

import java.io.BufferedInputStream;
import java.io.FileWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *
 * @author qbiss
 */
public class GetterInternetPage {
    public static void affPage() throws Exception {
        HttpURLConnection conn = (HttpURLConnection) new URL(
                "http://www.office-tourisme-haut-lignon.com/").openConnection();
        conn.connect();
 
        BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
 
        byte[] bytes = new byte[1024];
        int tmp ;
        FileWriter fichier = new FileWriter("maPage.html");
        while( (tmp = bis.read(bytes) ) != -1 ) {
            
            String chaine = new String(bytes,0,tmp);
            
            fichier.write(chaine);
            System.out.print(chaine);
        }
         
        conn.disconnect();
    }
    
}
