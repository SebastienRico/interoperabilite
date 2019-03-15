package com.interoperability.interoperability.wikidata.wikidataWriter;

import com.interoperability.interoperability.objetsDTO.RestaurantDTO;
import com.interoperability.interoperability.utilities.Util;
import com.interoperability.interoperability.wikidata.WikidataConstantes;
import com.interoperability.interoperability.wikidata.WikidataUtil;
import com.interoperability.interoperability.wikidata.WikidataLogger;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;
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
            propertyInstanceOf = (PropertyDocument) WikidataLogger.WikibaseWbdf.getEntityDocument(WikidataConstantes.PROPERTY_INSTANCE_OF);
            propertyAddress = (PropertyDocument) WikidataLogger.WikibaseWbdf.getEntityDocument(WikidataConstantes.PROPERTY_ADDRESS);
            propertyType = (PropertyDocument) WikidataLogger.WikibaseWbdf.getEntityDocument(WikidataConstantes.PROPERTY_TYPE);
            propertyCapacity = (PropertyDocument) WikidataLogger.WikibaseWbdf.getEntityDocument(WikidataConstantes.PROPERTY_SEATING_CAPACITY);
            propertyContact = (PropertyDocument) WikidataLogger.WikibaseWbdf.getEntityDocument(WikidataConstantes.PROPERTY_CONTACT);
            propertyMenu = (PropertyDocument) WikidataLogger.WikibaseWbdf.getEntityDocument(WikidataConstantes.PROPERTY_MENU);
            propertySchedule = (PropertyDocument) WikidataLogger.WikibaseWbdf.getEntityDocument(WikidataConstantes.PROPERTY_SCHEDULE);
        } catch (MediaWikiApiErrorException ex) {
            Logger.getLogger(WikidataRestaurantWriter.class.getName()).log(Level.SEVERE, null, ex);
        }

        ItemIdValue noid = ItemIdValue.NULL; // used when creating new items

        ItemDocument itemDocument = ItemDocumentBuilder.forItemId(noid)
                .withLabel(restaurant.getNameRestaurant(), "en")
                .withLabel(restaurant.getNameRestaurant(), "fr")
                .withDescription(restaurant.getDescriptionRestaurant(), "fr");

        Statement statementInstanceOf = StatementBuilder
                .forSubjectAndProperty(noid, propertyInstanceOf.getPropertyId())
                .withValue(Datamodel.makeItemIdValue(WikidataConstantes.ITEM_RESTAURANT, WikidataLogger.WIKIBASE_SITE_IRI))
                .build();
        itemDocument.withStatement(statementInstanceOf);

        if (restaurant.getAddressRestaurant() != null && !restaurant.getAddressRestaurant().isEmpty()) {
            Statement statementAddress = StatementBuilder
                .forSubjectAndProperty(noid, propertyAddress.getPropertyId())
                .withValue(Datamodel.makeStringValue(restaurant.getAddressRestaurant()))
                .build();
                itemDocument.withStatement(statementAddress);
            }

        if (restaurant.getTypeRestaurant() != null && !restaurant.getTypeRestaurant().isEmpty()) {
        Statement statementType = StatementBuilder
                .forSubjectAndProperty(noid, propertyType.getPropertyId())
                .withValue(Datamodel.makeStringValue(restaurant.getTypeRestaurant()))
                .build();
                itemDocument.withStatement(statementType);
            }

        if (restaurant.getCapacityRestaurant() != null) {
        Statement statementCapacity = StatementBuilder
                .forSubjectAndProperty(noid, propertyCapacity.getPropertyId())
                .withValue(Datamodel.makeQuantityValue(new BigDecimal(restaurant.getCapacityRestaurant())))
                .build();
                itemDocument.withStatement(statementCapacity);
            }

        String contactQid = WikidataUtil.getOwner(restaurant.getContactRestaurant());
        System.out.println("contactQid : " + contactQid);

        if (!contactQid.isEmpty()) {
            Statement statementContact = StatementBuilder
                .forSubjectAndProperty(noid, propertyContact.getPropertyId())
                .withValue(Datamodel.makeItemIdValue(contactQid, WikidataLogger.WIKIBASE_SITE_IRI))
                .build();
                itemDocument.withStatement(statementContact);
            }

        if (restaurant.getMenuRestaurant() != null && !restaurant.getMenuRestaurant().isEmpty()) {
        Statement statementMenu = StatementBuilder
                .forSubjectAndProperty(noid, propertyMenu.getPropertyId())
                .withValue(Datamodel.makeStringValue(restaurant.getMenuRestaurant()))
                .build();
                itemDocument.withStatement(statementMenu);
            }

        if (restaurant.getScheduleRestaurant() != null && !restaurant.getScheduleRestaurant().isEmpty()) {
        Statement statementSchedule = StatementBuilder
                .forSubjectAndProperty(noid, propertySchedule.getPropertyId())
                .withValue(Datamodel.makeStringValue(restaurant.getScheduleRestaurant()))
                .build();
                itemDocument.withStatement(statementSchedule);
            }

            itemDocument.build();
            /*
        ItemDocument itemDocument = ItemDocumentBuilder.forItemId(noid)
                .withLabel(restaurant.getNameRestaurant(), "en")
                .withLabel(restaurant.getNameRestaurant(), "fr")
                .withDescription(restaurant.getDescriptionRestaurant(), "fr")
                .withStatement(statementAddress)
                .withStatement(statementType)
                .withStatement(statementCapacity)
                .withStatement(statementContact)
                .withStatement(statementMenu)
                .withStatement(statementSchedule)
                .withStatement(statementInstanceOf)
                .build();*/
        try {
            ItemDocument newItemDocument = wbde.createItemDocument(itemDocument, "Statement created by the bot " + Util.getProperty("usn_wikibase"));
        } catch (IOException | MediaWikiApiErrorException e) {
            e.printStackTrace();
        }
        Logger.getLogger(WikidataRestaurantWriter.class.getName()).log(Level.INFO, "Created or updating {0}", restaurant.getNameRestaurant());
    }

}
