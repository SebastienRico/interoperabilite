package com.interoperability.interoperability.wikidata.wikidataWriter;

import com.interoperability.interoperability.objetsDTO.HostelDTO;
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

public class WikidataHostelWriter {
    
    @Autowired
    ItemDocumentRepository itemDocumentRepository;

    @Autowired
    PropertyDocumentRepository propertyDocumentRepository;

    private static final String ITEM_HOSTEL = "Q88";
    private static final String PROPERTY_INSTANCE_OF = "P16";
    private static final String PROPERTY_ADDRESS = "P1076";
    private static final String PROPERTY_SEATING_CAPACITY = "P1064";
    private static final String PROPERTY_CONTACT = "P61";
    private static final String PROPERTY_PRICE = "P1087";
    private static final String PROPERTY_STAR_HOUSING = "P1088";
    private static final String PROPERTY_SCHEDULE = "P1073";
    private static final String PROPERTY_PERIODE = "P1089";

    private PropertyDocument propertyInstanceOf;
    private PropertyDocument propertyCapacity;
    private PropertyDocument propertyAddress;
    private PropertyDocument propertyContact;
    private PropertyDocument propertyPrice;
    private PropertyDocument propertyStarHousing;
    private PropertyDocument propertyPeriode;
    private PropertyDocument propertySchedule;

    public void writeHostelPage(HostelDTO hostel) {
        WikibaseDataEditor wbde = new WikibaseDataEditor(WikidataLogger.WikibaseConnexion, WikidataLogger.WIKIBASE_SITE_IRI);
        try {
            propertyInstanceOf = (PropertyDocument) WikidataLogger.WikibaseWbdf.getEntityDocument(PROPERTY_INSTANCE_OF);
            propertyAddress = (PropertyDocument) WikidataLogger.WikibaseWbdf.getEntityDocument(PROPERTY_ADDRESS);
            propertyContact = (PropertyDocument) WikidataLogger.WikibaseWbdf.getEntityDocument(PROPERTY_CONTACT);
            propertyCapacity = (PropertyDocument) WikidataLogger.WikibaseWbdf.getEntityDocument(PROPERTY_SEATING_CAPACITY);
            propertyPrice = (PropertyDocument) WikidataLogger.WikibaseWbdf.getEntityDocument(PROPERTY_PRICE);
            propertyStarHousing = (PropertyDocument) WikidataLogger.WikibaseWbdf.getEntityDocument(PROPERTY_STAR_HOUSING);
            propertyPeriode = (PropertyDocument) WikidataLogger.WikibaseWbdf.getEntityDocument(PROPERTY_PERIODE);
            propertySchedule = (PropertyDocument) WikidataLogger.WikibaseWbdf.getEntityDocument(PROPERTY_SCHEDULE);
        } catch (MediaWikiApiErrorException ex) {
            Logger.getLogger(WikidataActivityWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
        ItemIdValue noid = ItemIdValue.NULL;
        Statement statementInstanceOf = StatementBuilder
                .forSubjectAndProperty(noid, propertyInstanceOf.getPropertyId())
                .withValue(Datamodel.makeItemIdValue(ITEM_HOSTEL, WikidataLogger.WIKIBASE_SITE_IRI))
                .build();
        Statement statementAddress = StatementBuilder
                .forSubjectAndProperty(noid, propertyAddress.getPropertyId())
                .withValue(Datamodel.makeStringValue(hostel.getAddressHostel()))
                .build();
        String contactQid = WikidataUtil.getOwner(hostel.getContactHostel());
        Statement statementContact = StatementBuilder
                .forSubjectAndProperty(noid, propertyContact.getPropertyId())
                .withValue(Datamodel.makeItemIdValue(contactQid, WikidataLogger.WIKIBASE_SITE_IRI))
                .build();
        Statement statementCapacity = StatementBuilder
                .forSubjectAndProperty(noid, propertyCapacity.getPropertyId())
                .withValue(Datamodel.makeQuantityValue(new BigDecimal(hostel.getCapacityHostel())))
                .build();
        Statement statementPrice = StatementBuilder
                .forSubjectAndProperty(noid, propertyPrice.getPropertyId())
                .withValue(Datamodel.makeStringValue(hostel.getPriceHostel().toString()))
                .build();
        Statement statementSchedule = StatementBuilder
                .forSubjectAndProperty(noid, propertySchedule.getPropertyId())
                .withValue(Datamodel.makeStringValue(hostel.getTimetableOpenHostel()))
                .build();
        Statement statementPeriode = StatementBuilder
                .forSubjectAndProperty(noid, propertyPeriode.getPropertyId())
                .withValue(Datamodel.makeStringValue(hostel.getOpeningPeriodHostel()))
                .build();
        Statement statementStarHousing = StatementBuilder
                .forSubjectAndProperty(noid, propertyStarHousing.getPropertyId())
                .withValue(Datamodel.makeStringValue(hostel.getStarHostel().toString()))
                .build();
        ItemDocument itemDocument = ItemDocumentBuilder.forItemId(noid)
                .withLabel(hostel.getNameHostel(), "en")
                .withLabel(hostel.getNameHostel(), "fr")
                .withStatement(statementAddress)
                .withStatement(statementContact)
                .withStatement(statementCapacity)
                .withStatement(statementSchedule)
                .withStatement(statementPrice)
                .withStatement(statementPeriode)
                .withStatement(statementStarHousing)
                .withStatement(statementInstanceOf)
                .build();
        try {
            ItemDocument newItemDocument = wbde.createItemDocument(itemDocument, "Statement created by the bot " + Util.getProperty("usn_wikibase"));
        } catch (IOException | MediaWikiApiErrorException ex) {
            Logger.getLogger(WikidataActivityWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
        Logger.getLogger(WikidataActivityWriter.class.getName()).log(Level.INFO, "Created or updating {0}", hostel.getNameHostel());
        com.interoperability.interoperability.models.ItemDocument databaseItemDocument = new com.interoperability.interoperability.models.ItemDocument();
        databaseItemDocument.setId(itemDocument.getItemId().getId());
        databaseItemDocument.setLabel(hostel.getNameHostel());
    }
}
