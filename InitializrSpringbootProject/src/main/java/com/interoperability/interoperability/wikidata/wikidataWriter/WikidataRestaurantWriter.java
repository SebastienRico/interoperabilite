package com.interoperability.interoperability.wikidata.wikidataWriter;

import com.interoperability.interoperability.objetsDTO.RestaurantDTO;
import com.interoperability.interoperability.repositories.ItemDocumentRepository;
import com.interoperability.interoperability.repositories.PropertyDocumentRepository;
import com.interoperability.interoperability.utilities.Util;
import com.interoperability.interoperability.wikidata.WikidataUtil;
import com.interoperability.interoperability.wikidata.WikidataLogger;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.wikidata.wdtk.datamodel.helpers.Datamodel;
import org.wikidata.wdtk.datamodel.helpers.ItemDocumentBuilder;
import org.wikidata.wdtk.datamodel.helpers.StatementBuilder;
import org.wikidata.wdtk.datamodel.interfaces.ItemDocument;
import org.wikidata.wdtk.datamodel.interfaces.ItemIdValue;
import org.wikidata.wdtk.datamodel.interfaces.PropertyDocument;
import org.wikidata.wdtk.datamodel.interfaces.Statement;
import org.wikidata.wdtk.wikibaseapi.WikibaseDataEditor;
import org.wikidata.wdtk.wikibaseapi.apierrors.MediaWikiApiErrorException;

public class WikidataRestaurantWriter {

    @Autowired
    ItemDocumentRepository itemDocumentRepository;

    @Autowired
    PropertyDocumentRepository propertyDocumentRepository;

    private static final String ITEM_RESTAURANT = "Q50";
    private static final String PROPERTY_INSTANCE_OF = "P16";
    private static final String PROPERTY_ADDRESS = "P1076";
    private static final String PROPERTY_TYPE = "P1077";
    private static final String PROPERTY_SEATING_CAPACITY = "P1064";
    private static final String PROPERTY_CONTACT = "P61";
    private static final String PROPERTY_MENU = "P1072";
    private static final String PROPERTY_SCHEDULE = "P1073";

    private PropertyDocument propertyInstanceOf;
    private PropertyDocument propertyCapacity;
    private PropertyDocument propertyAddress;
    private PropertyDocument propertyType;
    private PropertyDocument propertyContact;
    private PropertyDocument propertyMenu;
    private PropertyDocument propertySchedule;

    public void writeRestaurantPage(RestaurantDTO restaurant) {
        WikibaseDataEditor wbde = new WikibaseDataEditor(WikidataLogger.WikibaseConnexion, WikidataLogger.WIKIBASE_SITE_IRI);
        
        try {
            propertyInstanceOf = (PropertyDocument) WikidataLogger.WikibaseWbdf.getEntityDocument(PROPERTY_INSTANCE_OF);
            propertyAddress = (PropertyDocument) WikidataLogger.WikibaseWbdf.getEntityDocument(PROPERTY_ADDRESS);
            propertyType = (PropertyDocument) WikidataLogger.WikibaseWbdf.getEntityDocument(PROPERTY_TYPE);
            propertyCapacity = (PropertyDocument) WikidataLogger.WikibaseWbdf.getEntityDocument(PROPERTY_SEATING_CAPACITY);
            propertyContact = (PropertyDocument) WikidataLogger.WikibaseWbdf.getEntityDocument(PROPERTY_CONTACT);
            propertyMenu = (PropertyDocument) WikidataLogger.WikibaseWbdf.getEntityDocument(PROPERTY_MENU);
            propertySchedule = (PropertyDocument) WikidataLogger.WikibaseWbdf.getEntityDocument(PROPERTY_SCHEDULE);
        } catch (MediaWikiApiErrorException ex) {
            Logger.getLogger(WikidataRestaurantWriter.class.getName()).log(Level.SEVERE, null, ex);
        }

        ItemIdValue noid = ItemIdValue.NULL; // used when creating new items
        Statement statementInstanceOf = StatementBuilder
                .forSubjectAndProperty(noid, propertyInstanceOf.getPropertyId())
                .withValue(Datamodel.makeItemIdValue(ITEM_RESTAURANT, WikidataLogger.WIKIBASE_SITE_IRI))
                .build();
        Statement statatementAddress = StatementBuilder
                .forSubjectAndProperty(noid, propertyAddress.getPropertyId())
                .withValue(Datamodel.makeStringValue(restaurant.getAddressRestaurant().toString()))
                .build();
        Statement statatementType = StatementBuilder
                .forSubjectAndProperty(noid, propertyType.getPropertyId())
                .withValue(Datamodel.makeStringValue(restaurant.getTypeRestaurant()))
                .build();
        Statement statatementCapacity = StatementBuilder
                .forSubjectAndProperty(noid, propertyCapacity.getPropertyId())
                .withValue(Datamodel.makeQuantityValue(new BigDecimal(restaurant.getCapacityRestaurant())))
                .build();
        String contactQid = WikidataUtil.getOwner(restaurant.getContactRestaurant());
        System.out.println("contactQid : " + contactQid);
        Statement statatementContact = StatementBuilder
                .forSubjectAndProperty(noid, propertyContact.getPropertyId())
                .withValue(Datamodel.makeItemIdValue(contactQid, WikidataLogger.WIKIBASE_SITE_IRI))
                .build();
        Statement statatementMenu = StatementBuilder
                .forSubjectAndProperty(noid, propertyMenu.getPropertyId())
                .withValue(Datamodel.makeStringValue(restaurant.getMenuRestaurant()))
                .build();
        Statement statatementSchedule = StatementBuilder
                .forSubjectAndProperty(noid, propertySchedule.getPropertyId())
                .withValue(Datamodel.makeStringValue(restaurant.getScheduleRestaurant()))
                .build();
        ItemDocument itemDocument = ItemDocumentBuilder.forItemId(noid)
                .withLabel("La mandarine", "en")
                .withLabel("La mandarine", "fr")
                .withDescription(restaurant.getDescriptionRestaurant(), "fr")
                .withStatement(statatementAddress)
                .withStatement(statatementType)
                .withStatement(statatementCapacity)
                .withStatement(statatementContact)
                .withStatement(statatementMenu)
                .withStatement(statatementSchedule)
                .withStatement(statementInstanceOf)
                .build();
        try {
            ItemDocument newItemDocument = wbde.createItemDocument(itemDocument, "Statement created by the bot " + Util.getProperty("usn_wikibase"));
        } catch (IOException | MediaWikiApiErrorException e) {
            e.printStackTrace();
        }
        Logger.getLogger(WikidataRestaurantWriter.class.getName()).log(Level.INFO, "Created or updating {0}", restaurant.getNameRestaurant());
        com.interoperability.interoperability.models.ItemDocument databaseItemDocument = new com.interoperability.interoperability.models.ItemDocument();
        databaseItemDocument.setId(itemDocument.getItemId().getId());
        databaseItemDocument.setLabel(restaurant.getNameRestaurant());
        //itemDocumentRepository.save(databaseItemDocument);//System.out.println(itemDocument.getItemId().getId());
    }

}
