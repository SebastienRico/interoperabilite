package com.interoperability.interoperability.wikidata.wikidataWriter;

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
        Statement statementInstanceOf = StatementBuilder
                .forSubjectAndProperty(noid, propertyInstanceOf.getPropertyId())
                .withValue(Datamodel.makeItemIdValue(WikidataConstantes.ITEM_RENT, WikidataLogger.WIKIBASE_SITE_IRI))
                .build();
        Statement statementAddress = StatementBuilder
                .forSubjectAndProperty(noid, propertyAddress.getPropertyId())
                .withValue(Datamodel.makeStringValue(rent.getAddressRent()))
                .build();
        String contactQid = WikidataUtil.getOwner(rent.getContactRent());
        Statement statementContact = StatementBuilder
                .forSubjectAndProperty(noid, propertyContact.getPropertyId())
                .withValue(Datamodel.makeItemIdValue(contactQid, WikidataLogger.WIKIBASE_SITE_IRI))
                .build();
        Statement statementCapacity = StatementBuilder
                .forSubjectAndProperty(noid, propertyCapacity.getPropertyId())
                .withValue(Datamodel.makeQuantityValue(new BigDecimal(rent.getCapacityRent())))
                .build();
        Statement statementDateStart = StatementBuilder
                .forSubjectAndProperty(noid, propertyDateStart.getPropertyId())
                .withValue(Datamodel.makeStringValue(rent.getDateStartRent()))
                .build();
        Statement statementDateEnt = StatementBuilder
                .forSubjectAndProperty(noid, propertyDateEnd.getPropertyId())
                .withValue(Datamodel.makeStringValue(rent.getDateEndRent()))
                .build();
        Statement statementDisponibility = StatementBuilder
                .forSubjectAndProperty(noid, propertyDisponibility.getPropertyId())
                .withValue(Datamodel.makeStringValue(rent.getDisponibilityRent()))
                .build();
        Statement statementPrice = StatementBuilder
                .forSubjectAndProperty(noid, propertyPrice.getPropertyId())
                .withValue(Datamodel.makeStringValue(rent.getPriceRent().toString()))
                .build();
        ItemDocument itemDocument = ItemDocumentBuilder.forItemId(noid)
                .withLabel(rent.getDescriptionRent(), "en")
                .withLabel(rent.getDescriptionRent(), "fr")
                .withStatement(statementAddress)
                .withStatement(statementContact)
                .withStatement(statementCapacity)
                .withStatement(statementDateStart)
                .withStatement(statementDateEnt)
                .withStatement(statementDisponibility)
                .withStatement(statementInstanceOf)
                .withStatement(statementPrice)
                .build();
        try {
            ItemDocument newItemDocument = wbde.createItemDocument(itemDocument, "Statement created by the bot " + Util.getProperty("usn_wikibase"));
        } catch (IOException | MediaWikiApiErrorException ex) {
            Logger.getLogger(WikidataActivityWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
        Logger.getLogger(WikidataActivityWriter.class.getName()).log(Level.INFO, "Created or updating {0}", rent.getDescriptionRent());
    }
}
