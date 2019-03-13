package com.interoperability.interoperability;

import com.interoperability.interoperability.dataAccess.DatabaseController;
import com.interoperability.interoperability.wikidata.wikidataWriter.WikidataRestaurantWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class InteroperabilityApplication {

    public static void main(String[] args) throws Exception {
       SpringApplication.run(InteroperabilityApplication.class, args);
        try {
            // We start the directory watcher to parse CSV files
            Logger.getLogger(InteroperabilityApplication.class.getName()).log(Level.INFO, "Launch DirectoryWatcher");
            DirectoryWatch directoryWatch = new DirectoryWatch();
            directoryWatch.start();
            Logger.getLogger(InteroperabilityApplication.class.getName()).log(Level.INFO, "DirectoryWatcher launched");
            
            // We start the scheduler to update datas from HTML pages
            Logger.getLogger(InteroperabilityApplication.class.getName()).log(Level.INFO, "Launch Scheduler");
            Scheduler scheduler = new Scheduler();
            scheduler.start();
            Logger.getLogger(InteroperabilityApplication.class.getName()).log(Level.INFO, "Scheduler launched");
            
            // If the database existe, we connect to
            // If the database does not exist, we create it and we instanciate datas
            Logger.getLogger(InteroperabilityApplication.class.getName()).log(Level.INFO, "Set database");
            DatabaseController databaseController = new DatabaseController();
            databaseController.setDatabase();
            Logger.getLogger(InteroperabilityApplication.class.getName()).log(Level.INFO, "Database set");
            /**RestaurantDTO r = new RestaurantDTO();
            r.setNameRestaurant("Serge");
            r.setDescriptionRestaurant("Super creperie");
            WikidataLogger.connectToWikibase();
            WikidataRestaurantWriter w = new WikidataRestaurantWriter();
            w.writeRestaurantPage(r);**/
        } catch (Exception ex) {
            Logger.getLogger(InteroperabilityApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
