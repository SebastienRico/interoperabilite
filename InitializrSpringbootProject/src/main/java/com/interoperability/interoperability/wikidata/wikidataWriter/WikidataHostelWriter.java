package com.interoperability.interoperability.wikidata.wikidataWriter;

import com.interoperability.interoperability.ObjectDTO;
import com.interoperability.interoperability.objetsDTO.HostelDTO;
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

public class WikidataHostelWriter {

    private PropertyDocument propertyInstanceOf;
    private PropertyDocument propertyCapacity;
    private PropertyDocument propertyAddress;
    private PropertyDocument propertyContact;
    private PropertyDocument propertyPrice;
    private PropertyDocument propertyStarHousing;
    private PropertyDocument propertyPeriode;
    private PropertyDocument propertySchedule;

    private Boolean firstTry = true;

    public void writeHostelPage(HostelDTO hostel) {
        WikibaseDataEditor wbde = new WikibaseDataEditor(WikidataLogger.WikibaseConnexion, WikidataLogger.WIKIBASE_SITE_IRI);
        try {
            propertyInstanceOf = (PropertyDocument) WikidataLogger.WikibaseWbdf.getEntityDocument(WikidataConstantes.PROPERTY_INSTANCE_OF);
            propertyAddress = (PropertyDocument) WikidataLogger.WikibaseWbdf.getEntityDocument(WikidataConstantes.PROPERTY_ADDRESS);
            propertyContact = (PropertyDocument) WikidataLogger.WikibaseWbdf.getEntityDocument(WikidataConstantes.PROPERTY_CONTACT);
            propertyCapacity = (PropertyDocument) WikidataLogger.WikibaseWbdf.getEntityDocument(WikidataConstantes.PROPERTY_SEATING_CAPACITY);
            propertyPrice = (PropertyDocument) WikidataLogger.WikibaseWbdf.getEntityDocument(WikidataConstantes.PROPERTY_PRICE);
            propertyStarHousing = (PropertyDocument) WikidataLogger.WikibaseWbdf.getEntityDocument(WikidataConstantes.PROPERTY_STAR_HOUSING);
            propertyPeriode = (PropertyDocument) WikidataLogger.WikibaseWbdf.getEntityDocument(WikidataConstantes.PROPERTY_PERIODE);
            propertySchedule = (PropertyDocument) WikidataLogger.WikibaseWbdf.getEntityDocument(WikidataConstantes.PROPERTY_SCHEDULE);
        } catch (MediaWikiApiErrorException ex) {
            Logger.getLogger(WikidataActivityWriter.class.getName()).log(Level.SEVERE, null, ex);
        }

        ItemIdValue noid = ItemIdValue.NULL;
        if (!firstTry) {
            noid = WikidataUtil.getObjectItemIdValue((ObjectDTO) hostel);
        }

        ItemDocumentBuilder itemDocumentBuilder = ItemDocumentBuilder.forItemId(noid)
                .withLabel(hostel.getNameHostel(), "en")
                .withLabel(hostel.getNameHostel(), "fr")
                .withDescription(hostel.getDescriptionHostel(), "fr");

        Statement statementInstanceOf = StatementBuilder
                .forSubjectAndProperty(noid, propertyInstanceOf.getPropertyId())
                .withValue(Datamodel.makeItemIdValue(WikidataConstantes.ITEM_HOSTEL, WikidataLogger.WIKIBASE_SITE_IRI))
                .build();
        itemDocumentBuilder.withStatement(statementInstanceOf);

        if (hostel.getAddressHostel() != null && !hostel.getAddressHostel().isEmpty()) {
            Statement statementAddress = StatementBuilder
                    .forSubjectAndProperty(noid, propertyAddress.getPropertyId())
                    .withValue(Datamodel.makeStringValue(hostel.getAddressHostel()))
                    .build();
            itemDocumentBuilder.withStatement(statementAddress);
        }
        String contactQid = WikidataUtil.getOwner(hostel.getContactHostel());
        if (!contactQid.isEmpty()) {
            Statement statementContact = StatementBuilder
                    .forSubjectAndProperty(noid, propertyContact.getPropertyId())
                    .withValue(Datamodel.makeItemIdValue(contactQid, WikidataLogger.WIKIBASE_SITE_IRI))
                    .build();
            itemDocumentBuilder.withStatement(statementContact);
        }
        if (hostel.getCapacityHostel() != null) {
            Statement statementCapacity = StatementBuilder
                    .forSubjectAndProperty(noid, propertyCapacity.getPropertyId())
                    .withValue(Datamodel.makeQuantityValue(new BigDecimal(hostel.getCapacityHostel())))
                    .build();
            itemDocumentBuilder.withStatement(statementCapacity);
        }
        if (hostel.getPriceHostel() != null && !hostel.getPriceHostel().toString().isEmpty()) {
            Statement statementPrice = StatementBuilder
                    .forSubjectAndProperty(noid, propertyPrice.getPropertyId())
                    .withValue(Datamodel.makeStringValue(hostel.getPriceHostel().toString()))
                    .build();
            itemDocumentBuilder.withStatement(statementPrice);
        }
        if (hostel.getTimetableOpenHostel() != null && !hostel.getTimetableOpenHostel().isEmpty()) {
            Statement statementSchedule = StatementBuilder
                    .forSubjectAndProperty(noid, propertySchedule.getPropertyId())
                    .withValue(Datamodel.makeStringValue(hostel.getTimetableOpenHostel()))
                    .build();
            itemDocumentBuilder.withStatement(statementSchedule);
        }
        if (hostel.getOpeningPeriodHostel() != null && !hostel.getOpeningPeriodHostel().isEmpty()) {
            Statement statementPeriode = StatementBuilder
                    .forSubjectAndProperty(noid, propertyPeriode.getPropertyId())
                    .withValue(Datamodel.makeStringValue(hostel.getOpeningPeriodHostel()))
                    .build();
            itemDocumentBuilder.withStatement(statementPeriode);
        }
        if (hostel.getStarHostel() != null && !hostel.getStarHostel().toString().isEmpty()) {
            Statement statementStarHousing = StatementBuilder
                    .forSubjectAndProperty(noid, propertyStarHousing.getPropertyId())
                    .withValue(Datamodel.makeStringValue(hostel.getStarHostel().toString()))
                    .build();
            itemDocumentBuilder.withStatement(statementStarHousing);
        }

        ItemDocument itemDocument = itemDocumentBuilder.build();

        if (firstTry) {
            firstTry = false;
            try {
                wbde.createItemDocument(itemDocument, "Statement created by the bot " + Util.getProperty("usn_wikibase"));
                Logger.getLogger(WikidataRestaurantWriter.class.getName()).log(Level.INFO, "{0} created", hostel.getNameHostel());
            } catch (IOException | MediaWikiApiErrorException e) {
                Logger.getLogger(WikidataRestaurantWriter.class.getName()).log(Level.SEVERE, "Canot create " + hostel.getNameHostel(), e);
            } finally {
                writeHostelPage(hostel);
            }
        } else {
            try {
                wbde.editItemDocument(itemDocument, true, "Statement updated by the bot " + Util.getProperty("usn_wikibase"));
                Logger.getLogger(WikidataRestaurantWriter.class.getName()).log(Level.INFO, "{0} updated", hostel.getNameHostel());
            } catch (IOException | MediaWikiApiErrorException ex) {
                Logger.getLogger(WikidataRestaurantWriter.class.getName()).log(Level.SEVERE, "Canot update " + hostel.getNameHostel(), ex);
            }
        }
    }
}
