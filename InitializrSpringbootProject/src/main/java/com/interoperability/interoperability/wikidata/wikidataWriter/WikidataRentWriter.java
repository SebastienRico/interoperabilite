package com.interoperability.interoperability.wikidata.wikidataWriter;

import com.interoperability.interoperability.ObjectDTO;
import com.interoperability.interoperability.objetsDTO.RentDTO;
import com.interoperability.interoperability.utilities.Util;
import com.interoperability.interoperability.wikidata.WikidataConstantes;
import com.interoperability.interoperability.wikidata.WikidataLogger;
import com.interoperability.interoperability.wikidata.WikidataUtil;
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

public class WikidataRentWriter {

    private PropertyDocument propertyInstanceOf;
    private PropertyDocument propertyCapacity;
    private PropertyDocument propertyAddress;
    private PropertyDocument propertyContact;
    private PropertyDocument propertyDateStart;
    private PropertyDocument propertyDateEnd;
    private PropertyDocument propertyDisponibility;
    private PropertyDocument propertyPrice;

    private Boolean firstTry = true;

    public void writeRentPage(RentDTO rent) {
        WikibaseDataEditor wbde = new WikibaseDataEditor(WikidataLogger.WikibaseConnexion, WikidataLogger.WIKIBASE_SITE_IRI);
        try {
            propertyInstanceOf = (PropertyDocument) WikidataLogger.WikibaseWbdf.getEntityDocument(WikidataConstantes.PROPERTY_INSTANCE_OF);
            propertyAddress = (PropertyDocument) WikidataLogger.WikibaseWbdf.getEntityDocument(WikidataConstantes.PROPERTY_ADDRESS);
            propertyContact = (PropertyDocument) WikidataLogger.WikibaseWbdf.getEntityDocument(WikidataConstantes.PROPERTY_CONTACT);
            propertyCapacity = (PropertyDocument) WikidataLogger.WikibaseWbdf.getEntityDocument(WikidataConstantes.PROPERTY_SEATING_CAPACITY);
            propertyDateStart = (PropertyDocument) WikidataLogger.WikibaseWbdf.getEntityDocument(WikidataConstantes.PROPERTY_DATE_START);
            propertyDateEnd = (PropertyDocument) WikidataLogger.WikibaseWbdf.getEntityDocument(WikidataConstantes.PROPERTY_DATE_END);
            propertyDisponibility = (PropertyDocument) WikidataLogger.WikibaseWbdf.getEntityDocument(WikidataConstantes.PROPERTY_DISPONIBILITY);
            propertyPrice = (PropertyDocument) WikidataLogger.WikibaseWbdf.getEntityDocument(WikidataConstantes.PROPERTY_PRICE);
        } catch (MediaWikiApiErrorException ex) {
            Logger.getLogger(WikidataActivityWriter.class.getName()).log(Level.SEVERE, null, ex);
        }

        ItemIdValue noid = ItemIdValue.NULL;
        if (!firstTry) {
            noid = WikidataUtil.getObjectItemIdValue((ObjectDTO) rent);
        }

        ItemDocumentBuilder itemDocumentBuilder = ItemDocumentBuilder.forItemId(noid)
                .withLabel(rent.getNameRent(), "en")
                .withLabel(rent.getNameRent(), "fr")
                .withDescription(rent.getDescriptionRent(), "fr");

        Statement statementInstanceOf = StatementBuilder
                .forSubjectAndProperty(noid, propertyInstanceOf.getPropertyId())
                .withValue(Datamodel.makeItemIdValue(WikidataConstantes.ITEM_RENT, WikidataLogger.WIKIBASE_SITE_IRI))
                .build();
        itemDocumentBuilder.withStatement(statementInstanceOf);

        if (rent.getAddressRent() != null && !rent.getAddressRent().isEmpty()) {
            Statement statementAddress = StatementBuilder
                    .forSubjectAndProperty(noid, propertyAddress.getPropertyId())
                    .withValue(Datamodel.makeStringValue(rent.getAddressRent()))
                    .build();
            itemDocumentBuilder.withStatement(statementAddress);
        }
        String contactQid = WikidataUtil.getOwner(rent.getContactRent());
        if (!contactQid.isEmpty()) {
            Statement statementContact = StatementBuilder
                    .forSubjectAndProperty(noid, propertyContact.getPropertyId())
                    .withValue(Datamodel.makeItemIdValue(contactQid, WikidataLogger.WIKIBASE_SITE_IRI))
                    .build();
            itemDocumentBuilder.withStatement(statementContact);
        }
        if (rent.getCapacityRent() != null) {
            Statement statementCapacity = StatementBuilder
                    .forSubjectAndProperty(noid, propertyCapacity.getPropertyId())
                    .withValue(Datamodel.makeQuantityValue(new BigDecimal(rent.getCapacityRent())))
                    .build();
            itemDocumentBuilder.withStatement(statementCapacity);
        }
        if (rent.getDateStartRent() != null && !rent.getDateStartRent().isEmpty()) {
            Statement statementDateStart = StatementBuilder
                    .forSubjectAndProperty(noid, propertyDateStart.getPropertyId())
                    .withValue(Datamodel.makeStringValue(rent.getDateStartRent()))
                    .build();
            itemDocumentBuilder.withStatement(statementDateStart);
        }
        if (rent.getDateEndRent() != null && !rent.getDateEndRent().isEmpty()) {
            Statement statementDateEnt = StatementBuilder
                    .forSubjectAndProperty(noid, propertyDateEnd.getPropertyId())
                    .withValue(Datamodel.makeStringValue(rent.getDateEndRent()))
                    .build();
            itemDocumentBuilder.withStatement(statementDateEnt);
        }
        if (rent.getDisponibilityRent() != null && !rent.getDisponibilityRent().isEmpty()) {
            Statement statementDisponibility = StatementBuilder
                    .forSubjectAndProperty(noid, propertyDisponibility.getPropertyId())
                    .withValue(Datamodel.makeStringValue(rent.getDisponibilityRent()))
                    .build();
            itemDocumentBuilder.withStatement(statementDisponibility);
        }
        if (rent.getPriceRent() != null && !rent.getPriceRent().toString().isEmpty()) {
            Statement statementPrice = StatementBuilder
                    .forSubjectAndProperty(noid, propertyPrice.getPropertyId())
                    .withValue(Datamodel.makeStringValue(rent.getPriceRent().toString()))
                    .build();
            itemDocumentBuilder.withStatement(statementPrice);
        }

        ItemDocument itemDocument = itemDocumentBuilder.build();

        if (firstTry) {
            firstTry = false;
            try {
                wbde.createItemDocument(itemDocument, "Statement created by the bot " + Util.getProperty("usn_wikibase"));
                Logger.getLogger(WikidataRestaurantWriter.class.getName()).log(Level.INFO, "{0} created", rent.getNameRent());
            } catch (IOException | MediaWikiApiErrorException e) {
                Logger.getLogger(WikidataRestaurantWriter.class.getName()).log(Level.SEVERE, "Canot create " + rent.getNameRent(), e);
            } finally {
                writeRentPage(rent);
            }
        } else {
            try {
                wbde.editItemDocument(itemDocument, true, "Statement updated by the bot " + Util.getProperty("usn_wikibase"));
                Logger.getLogger(WikidataRestaurantWriter.class.getName()).log(Level.INFO, "{0} updated", rent.getNameRent());
            } catch (IOException | MediaWikiApiErrorException ex) {
                Logger.getLogger(WikidataRestaurantWriter.class.getName()).log(Level.SEVERE, "Canot update " + rent.getNameRent(), ex);
            }
        }
    }
}
