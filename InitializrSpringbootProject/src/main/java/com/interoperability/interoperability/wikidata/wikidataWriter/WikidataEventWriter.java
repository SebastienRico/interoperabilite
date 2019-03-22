package com.interoperability.interoperability.wikidata.wikidataWriter;

import com.interoperability.interoperability.ObjectDTO;
import com.interoperability.interoperability.objetsDTO.EventDTO;
import com.interoperability.interoperability.utilities.Util;
import com.interoperability.interoperability.wikidata.WikidataConstantes;
import com.interoperability.interoperability.wikidata.WikidataLogger;
import com.interoperability.interoperability.wikidata.WikidataUtil;
import java.io.IOException;
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

public class WikidataEventWriter {

    private PropertyDocument propertyInstanceOf;
    private PropertyDocument propertyAddress;
    private PropertyDocument propertyType;
    private PropertyDocument propertyContact;
    private PropertyDocument propertyDateStart;
    private PropertyDocument propertyDateEnd;
    private PropertyDocument propertyPrice;

    private Boolean firstTry = true;

    public void writeEventPage(EventDTO event) {
        WikibaseDataEditor wbde = new WikibaseDataEditor(WikidataLogger.WikibaseConnexion, WikidataLogger.WIKIBASE_SITE_IRI);
        try {
            propertyInstanceOf = (PropertyDocument) WikidataLogger.WikibaseWbdf.getEntityDocument(WikidataConstantes.PROPERTY_INSTANCE_OF);
            propertyAddress = (PropertyDocument) WikidataLogger.WikibaseWbdf.getEntityDocument(WikidataConstantes.PROPERTY_ADDRESS);
            propertyType = (PropertyDocument) WikidataLogger.WikibaseWbdf.getEntityDocument(WikidataConstantes.PROPERTY_TYPE);
            propertyContact = (PropertyDocument) WikidataLogger.WikibaseWbdf.getEntityDocument(WikidataConstantes.PROPERTY_CONTACT);
            propertyDateStart = (PropertyDocument) WikidataLogger.WikibaseWbdf.getEntityDocument(WikidataConstantes.PROPERTY_DATE_START);
            propertyDateEnd = (PropertyDocument) WikidataLogger.WikibaseWbdf.getEntityDocument(WikidataConstantes.PROPERTY_DATE_END);
            propertyPrice = (PropertyDocument) WikidataLogger.WikibaseWbdf.getEntityDocument(WikidataConstantes.PROPERTY_PRICE);
        } catch (MediaWikiApiErrorException ex) {
            Logger.getLogger(WikidataEventWriter.class.getName()).log(Level.SEVERE, null, ex);
        }

        ItemIdValue noid = ItemIdValue.NULL;
        if (!firstTry) {
            noid = WikidataUtil.getObjectItemIdValue((ObjectDTO) event);
        }

        ItemDocumentBuilder itemDocumentBuilder = ItemDocumentBuilder.forItemId(noid)
                .withLabel(event.getNameEvent(), "en")
                .withLabel(event.getNameEvent(), "fr");

        Statement statementInstanceOf = StatementBuilder
                .forSubjectAndProperty(noid, propertyInstanceOf.getPropertyId())
                .withValue(Datamodel.makeItemIdValue(WikidataConstantes.ITEM_EVENT, WikidataLogger.WIKIBASE_SITE_IRI))
                .build();
        itemDocumentBuilder.withStatement(statementInstanceOf);

        if (event.getAddressEvent() != null && !event.getAddressEvent().isEmpty()) {
            Statement statementAddress = StatementBuilder
                    .forSubjectAndProperty(noid, propertyAddress.getPropertyId())
                    .withValue(Datamodel.makeStringValue(event.getAddressEvent()))
                    .build();
            itemDocumentBuilder.withStatement(statementAddress);
        }
        if (event.getTypeEvent() != null && !event.getTypeEvent().isEmpty()) {
            Statement statementType = StatementBuilder
                    .forSubjectAndProperty(noid, propertyType.getPropertyId())
                    .withValue(Datamodel.makeStringValue(event.getTypeEvent()))
                    .build();
            itemDocumentBuilder.withStatement(statementType);
        }
        String contactQid = WikidataUtil.getOwner(event.getContactEvent());
        if (!contactQid.isEmpty()) {
            Statement statementContact = StatementBuilder
                    .forSubjectAndProperty(noid, propertyContact.getPropertyId())
                    .withValue(Datamodel.makeItemIdValue(contactQid, WikidataLogger.WIKIBASE_SITE_IRI))
                    .build();
            itemDocumentBuilder.withStatement(statementContact);
        }
        if (event.getDateStartEvent() != null && !event.getDateStartEvent().isEmpty()) {
            Statement statementDateStart = StatementBuilder
                    .forSubjectAndProperty(noid, propertyDateStart.getPropertyId())
                    .withValue(Datamodel.makeStringValue(event.getDateStartEvent()))
                    .build();
            itemDocumentBuilder.withStatement(statementDateStart);
        }
        if (event.getDateEndEvent() != null && !event.getDateEndEvent().isEmpty()) {
            Statement statementDateEnd = StatementBuilder
                    .forSubjectAndProperty(noid, propertyDateEnd.getPropertyId())
                    .withValue(Datamodel.makeStringValue(event.getDateEndEvent()))
                    .build();
            itemDocumentBuilder.withStatement(statementDateEnd);
        }
        if (event.getPriceEvent() != null && !event.getPriceEvent().toString().isEmpty()) {
            Statement statementPrice = StatementBuilder
                    .forSubjectAndProperty(noid, propertyPrice.getPropertyId())
                    .withValue(Datamodel.makeStringValue(event.getPriceEvent().toString()))
                    .build();
            itemDocumentBuilder.withStatement(statementPrice);
        }

        ItemDocument itemDocument = itemDocumentBuilder.build();

        if (firstTry) {
            firstTry = false;
            try {
                wbde.createItemDocument(itemDocument, "Statement created by the bot " + Util.getProperty("usn_wikibase"));
                Logger.getLogger(WikidataRestaurantWriter.class.getName()).log(Level.INFO, "{0} created", event.getNameEvent());
            } catch (IOException | MediaWikiApiErrorException e) {
                Logger.getLogger(WikidataRestaurantWriter.class.getName()).log(Level.SEVERE, "Canot create " + event.getNameEvent(), e);
            } finally {
                writeEventPage(event);
            }
        } else {
            try {
                wbde.editItemDocument(itemDocument, true, "Statement updated by the bot " + Util.getProperty("usn_wikibase"));
                Logger.getLogger(WikidataRestaurantWriter.class.getName()).log(Level.INFO, "{0} updated", event.getNameEvent());
            } catch (IOException | MediaWikiApiErrorException ex) {
                Logger.getLogger(WikidataRestaurantWriter.class.getName()).log(Level.SEVERE, "Canot update " + event.getNameEvent(), ex);
            }
        }
    }
}
