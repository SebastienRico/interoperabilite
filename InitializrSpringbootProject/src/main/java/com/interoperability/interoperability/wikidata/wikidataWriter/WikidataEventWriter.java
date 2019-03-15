package com.interoperability.interoperability.wikidata.wikidataWriter;

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

        ItemIdValue noid = WikidataUtil.getObjectItemIdValue((ObjectDTO)event); // used when creating new items

        ItemDocumentBuilder.forItemId(noid)
                .withLabel(event.getNameEvent(), "en")
                .withLabel(event.getNameEvent(), "fr")
                .withDescription(event.getDescriptionEvent(), "fr");

        Statement statementInstanceOf = StatementBuilder
                .forSubjectAndProperty(noid, propertyInstanceOf.getPropertyId())
                .withValue(Datamodel.makeItemIdValue(WikidataConstantes.ITEM_EVENT, WikidataLogger.WIKIBASE_SITE_IRI))
                .build();
                ItemDocumentBuilder.forItemId(noid).withStatement(statementInstanceOf);

        if (event.getAddressEvent() != null && !event.getAddressEvent().isEmpty()) {
        Statement statementAddress = StatementBuilder
                .forSubjectAndProperty(noid, propertyAddress.getPropertyId())
                .withValue(Datamodel.makeStringValue(event.getAddressEvent()))
                .build();
                ItemDocumentBuilder.forItemId(noid).withStatement(statementAddress);
            }

        if (event.getTypeEvent() != null && !event.getTypeEvent().isEmpty()) {
        Statement statementType = StatementBuilder
                .forSubjectAndProperty(noid, propertyType.getPropertyId())
                .withValue(Datamodel.makeStringValue(event.getTypeEvent()))
                .build();
                ItemDocumentBuilder.forItemId(noid).withStatement(statementType);
            }

        String contactQid = WikidataUtil.getOwner(event.getContactEvent());
        if (!contactQid.isEmpty()) {
        Statement statementContact = StatementBuilder
                .forSubjectAndProperty(noid, propertyContact.getPropertyId())
                .withValue(Datamodel.makeItemIdValue(contactQid, WikidataLogger.WIKIBASE_SITE_IRI))
                .build();
                ItemDocumentBuilder.forItemId(noid).withStatement(statementContact);
            }

        if (event.getDateStartEvent() != null && !event.getDateStartEvent().isEmpty()) {
        Statement statementDateStart = StatementBuilder
                .forSubjectAndProperty(noid, propertyDateStart.getPropertyId())
                .withValue(Datamodel.makeStringValue(event.getDateStartEvent()))
                .build();
                ItemDocumentBuilder.forItemId(noid).withStatement(statementDateStart);
        }

        if (event.getDateEndEvent() != null && !event.getDateEndEvent().isEmpty()) {
        Statement statementDateEnd = StatementBuilder
                .forSubjectAndProperty(noid, propertyDateEnd.getPropertyId())
                .withValue(Datamodel.makeStringValue(event.getDateEndEvent()))
                .build();
                ItemDocumentBuilder.forItemId(noid).withStatement(statementDateEnd);
        }

        if (event.getPriceEvent() != null && !event.getPriceEvent().toString().isEmpty()) {
        Statement statementPrice = StatementBuilder
                .forSubjectAndProperty(noid, propertyPrice.getPropertyId())
                .withValue(Datamodel.makeStringValue(event.getPriceEvent().toString()))
                .build();
                ItemDocumentBuilder.forItemId(noid).withStatement(statementPrice);
        }
        ItemDocument itemDocument = ItemDocumentBuilder.forItemId(noid).build();

        try {
            ItemDocument newItemDocument = wbde.createItemDocument(itemDocument, "Statement created by the bot " + Util.getProperty("usn_wikibase"));
        } catch (IOException | MediaWikiApiErrorException ex) {
            Logger.getLogger(WikidataEventWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
        Logger.getLogger(WikidataEventWriter.class.getName()).log(Level.INFO, "Created or updating {0}", event.getNameEvent());
    }
}
