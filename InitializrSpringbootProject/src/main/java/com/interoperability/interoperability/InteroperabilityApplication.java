package com.interoperability.interoperability;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class InteroperabilityApplication {

    public static final String DIRECTORY_PATH = "";

    public static void main(String[] args) throws Exception {
        SpringApplication.run(InteroperabilityApplication.class, args);
        try {
            
            DirectoryWatch.StartFileWatcherThread(DIRECTORY_PATH);
        } catch (Exception ex) {
            Logger.getLogger(InteroperabilityApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
