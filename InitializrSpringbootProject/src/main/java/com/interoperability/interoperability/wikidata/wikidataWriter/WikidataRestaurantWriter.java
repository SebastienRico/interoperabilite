package com.interoperability.interoperability.wikidata.wikidataWriter;

import com.interoperability.interoperability.objetsDTO.RestaurantDTO;
import com.interoperability.interoperability.utilities.Util;
import com.interoperability.interoperability.wikidata.WikidataLogger;
import static com.interoperability.interoperability.wikidata.WikidataLogger.WikibaseWbdf;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.wikidata.wdtk.datamodel.helpers.ItemDocumentBuilder;
import org.wikidata.wdtk.datamodel.helpers.StatementBuilder;
import org.wikidata.wdtk.datamodel.interfaces.ItemDocument;
import org.wikidata.wdtk.datamodel.interfaces.ItemIdValue;
import org.wikidata.wdtk.datamodel.interfaces.PropertyDocument;
import org.wikidata.wdtk.datamodel.interfaces.Statement;
import org.wikidata.wdtk.wikibaseapi.WikibaseDataEditor;
import org.wikidata.wdtk.wikibaseapi.apierrors.MediaWikiApiErrorException;

public class WikidataRestaurantWriter {
    
    private static final String PROPERTY_ADDRESS = "P706";
    /**private static final String PROPERTY_TYPE = "P50";
    private static final String PROPERTY_SITING_CAPACITY = "P50";
    private static final String PROPERTY_CONTACT = "P50";
    private static final String PROPERTY_MENU = "P50";
    private static final String PROPERTY_HORAIRE = "P50";**/
    
    private static final String SERGE_QID = null;

    public static void writeRestaurantPage(RestaurantDTO restaurant) {
        WikibaseDataEditor wbde = new WikibaseDataEditor(WikidataLogger.WikibaseConnexion, WikidataLogger.WIKIBASE_SITE_IRI);
        
        PropertyDocument propertyAddress = null;
        /**PropertyDocument propertyType = null;
        PropertyDocument propertyCapacity = null;
        PropertyDocument propertyContact = null;
        PropertyDocument propertyMenu = null;
        PropertyDocument propertyHoraire = null;**/
        
        String restaurantQId = "";
        
        switch(restaurant.getNameRestaurant()){
            case "Serge":
            case "serge":
                restaurantQId = SERGE_QID;
            default:
                Logger.getLogger(WikidataRestaurantWriter.class.getName()).log(Level.SEVERE, "The restaurant " + restaurant.getNameRestaurant() + " dose not exist");
        }
                
        ItemDocument item = null;
        try {
            item = (ItemDocument) WikidataLogger.WikibaseWbdf.getEntityDocument(restaurantQId);
            //propertyTravaille = (PropertyDocument) WikidataLogger.wbdf.getEntityDocument("P242");
        } catch (MediaWikiApiErrorException ex) {
            Logger.getLogger(WikidataRestaurantWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
        //System.out.println(propertyTravaille.getLabels());
        
        
        try {
            // Add the property address
            if(restaurant.getAdresseRestaurant() != null){
                propertyAddress = (PropertyDocument) WikidataLogger.WikibaseWbdf.getEntityDocument(PROPERTY_ADDRESS);
            }
            // Add the property type
            /**if(restaurant.getTypeRestaurant()!= null){
                propertyType = (PropertyDocument) WikidataLogger.wbdf.getEntityDocument(PROPERTY_TYPE);
            }
            // Add the property siting capacity
            if(restaurant.getCapaciteRestaurant()!= null){
                propertyAddress = (PropertyDocument) WikidataLogger.wbdf.getEntityDocument(PROPERTY_SITING_CAPACITY);
            }
            // Add the property contact
            if(restaurant.getContactRestaurant() != null){
                propertyAddress = (PropertyDocument) WikidataLogger.wbdf.getEntityDocument(PROPERTY_CONTACT);
            }
            // Add the property menu
            if(restaurant.getMenuRestaurant() != null){
                propertyAddress = (PropertyDocument) WikidataLogger.wbdf.getEntityDocument(PROPERTY_MENU);
            }
            // Add the property horaire
            if(restaurant.getHoraireOuvertureRestaurant()!= null){
                propertyAddress = (PropertyDocument) WikidataLogger.wbdf.getEntityDocument(PROPERTY_HORAIRE);
            }**/
        } catch (MediaWikiApiErrorException ex) {
            Logger.getLogger(WikidataRestaurantWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
        

        ItemIdValue noid = ItemIdValue.NULL; // used when creating new items
        Statement statement = StatementBuilder
                .forSubjectAndProperty(noid, propertyAddress.getPropertyId())
                .withValue(item.getItemId()).build();
        ItemDocument itemDocument = ItemDocumentBuilder.forItemId(noid)
                .withLabel("Serge", "en")
                .withLabel("Serge", "fr")
                .withStatement(statement).build();
        try {
            ItemDocument newItemDocument = wbde.createItemDocument(itemDocument,
                    "Statement created by the bot " + Util.getProperty("usn_wikibase"));
        } catch (IOException | MediaWikiApiErrorException e) {
            e.printStackTrace();
        }
        //System.out.println("Created Florence Garrelie");
        //System.out.println("Created statement: Florence Garrelie travaille Laboratoire Huber Curien");
        //System.out.println(itemDocument.getItemId().getId());
    }

}
