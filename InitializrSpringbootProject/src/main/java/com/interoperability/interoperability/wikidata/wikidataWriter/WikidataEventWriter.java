package com.interoperability.interoperability.wikidata.wikidataWriter;

import com.interoperability.interoperability.objetsDTO.EventDTO;
import com.interoperability.interoperability.repositories.ItemDocumentRepository;
import com.interoperability.interoperability.repositories.PropertyDocumentRepository;
import com.interoperability.interoperability.utilities.Util;
import com.interoperability.interoperability.wikidata.WikidataLogger;
import com.interoperability.interoperability.wikidata.WikidataUtil;
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

public class WikidataEventWriter {

    @Autowired
    ItemDocumentRepository itemDocumentRepository;

    @Autowired
    PropertyDocumentRepository propertyDocumentRepository;

    private static final String ITEM_EVENT = "Q1571";
    private static final String PROPERTY_INSTANCE_OF = "P16";
    private static final String PROPERTY_ADDRESS = "P1076";
    private static final String PROPERTY_SEATING_CAPACITY = "P1064";
    private static final String PROPERTY_CONTACT = "P61";
    private static final String PROPERTY_TYPE = "P1077";
    private static final String PROPERTY_DATE_START = "P34";
    private static final String PROPERTY_DATE_END = "P43";
    private static final String PROPERTY_PRICE = "P1080";

    private PropertyDocument propertyInstanceOf;
    private PropertyDocument propertyCapacity;
    private PropertyDocument propertyAddress;
    private PropertyDocument propertyType;
    private PropertyDocument propertyContact;
    private PropertyDocument propertyDateStart;
    private PropertyDocument propertyDateEnd;
    private PropertyDocument propertyPrice;

    public void writeEventPage(EventDTO event) {
        WikibaseDataEditor wbde = new WikibaseDataEditor(WikidataLogger.WikibaseConnexion, WikidataLogger.WIKIBASE_SITE_IRI);
        try {
            propertyInstanceOf = (PropertyDocument) WikidataLogger.WikibaseWbdf.getEntityDocument(PROPERTY_INSTANCE_OF);
            propertyAddress = (PropertyDocument) WikidataLogger.WikibaseWbdf.getEntityDocument(PROPERTY_ADDRESS);
            propertyType = (PropertyDocument) WikidataLogger.WikibaseWbdf.getEntityDocument(PROPERTY_TYPE);
            propertyCapacity = (PropertyDocument) WikidataLogger.WikibaseWbdf.getEntityDocument(PROPERTY_SEATING_CAPACITY);
            propertyContact = (PropertyDocument) WikidataLogger.WikibaseWbdf.getEntityDocument(PROPERTY_CONTACT);
            propertyDateStart = (PropertyDocument) WikidataLogger.WikibaseWbdf.getEntityDocument(PROPERTY_DATE_START);
            propertyDateEnd = (PropertyDocument) WikidataLogger.WikibaseWbdf.getEntityDocument(PROPERTY_DATE_END);
            propertyPrice = (PropertyDocument) WikidataLogger.WikibaseWbdf.getEntityDocument(PROPERTY_PRICE);
        } catch (MediaWikiApiErrorException ex) {
            Logger.getLogger(WikidataEventWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
        ItemIdValue noid = ItemIdValue.NULL;
        Statement statementInstanceOf = StatementBuilder
                .forSubjectAndProperty(noid, propertyInstanceOf.getPropertyId())
                .withValue(Datamodel.makeItemIdValue(ITEM_EVENT, WikidataLogger.WIKIBASE_SITE_IRI))
                .build();
        Statement statementAddress = StatementBuilder
                .forSubjectAndProperty(noid, propertyAddress.getPropertyId())
                .withValue(Datamodel.makeStringValue(event.getAddressEvent()))
                .build();
        Statement statementType = StatementBuilder
                .forSubjectAndProperty(noid, propertyType.getPropertyId())
                .withValue(Datamodel.makeStringValue(event.getTypeEvent()))
                .build();
        Statement statementCapacity = StatementBuilder
                .forSubjectAndProperty(noid, propertyCapacity.getPropertyId())
                .withValue(Datamodel.makeQuantityValue(new BigDecimal(event.getCapacityEvent())))
                .build();
        String contactQid = WikidataUtil.getOwner(event.getContactEvent());
        Statement statementContact = StatementBuilder
                .forSubjectAndProperty(noid, propertyContact.getPropertyId())
                .withValue(Datamodel.makeItemIdValue(contactQid, WikidataLogger.WIKIBASE_SITE_IRI))
                .build();
        Statement statementDateStart = StatementBuilder
                .forSubjectAndProperty(noid, propertyDateStart.getPropertyId())
                .withValue(Datamodel.makeStringValue(event.getDateStartEvent()))
                .build();
        Statement statementDateEnd = StatementBuilder
                .forSubjectAndProperty(noid, propertyDateEnd.getPropertyId())
                .withValue(Datamodel.makeStringValue(event.getDateEndEvent()))
                .build();
        Statement statementPrice = StatementBuilder
                .forSubjectAndProperty(noid, propertyPrice.getPropertyId())
                .withValue(Datamodel.makeQuantityValue(new BigDecimal(event.getPriceEvent())))
                .build();
        ItemDocument itemDocument = ItemDocumentBuilder.forItemId(noid)
                .withLabel(event.getNameEvent(), "en")
                .withLabel(event.getNameEvent(), "fr")
                .withStatement(statementInstanceOf)
                .withStatement(statementAddress)
                .withStatement(statementType)
                .withStatement(statementCapacity)
                .withStatement(statementContact)
                .withStatement(statementDateStart)
                .withStatement(statementDateEnd)
                .withStatement(statementPrice)
                .build();
        
        try {
            ItemDocument newItemDocument = wbde.createItemDocument(itemDocument, "Statement created by the bot " + Util.getProperty("usn_wikibase"));
        } catch (IOException | MediaWikiApiErrorException ex) {
            Logger.getLogger(WikidataEventWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
        Logger.getLogger(WikidataEventWriter.class.getName()).log(Level.INFO, "Created or updating {0}", event.getNameEvent());
        com.interoperability.interoperability.models.ItemDocument databaseItemDocument = new com.interoperability.interoperability.models.ItemDocument();
        databaseItemDocument.setId(itemDocument.getItemId().getId());
        databaseItemDocument.setLabel(event.getNameEvent());
    }
}
