package com.interoperability.interoperability.wikidata.wikidataWriter;

import com.interoperability.interoperability.objetsDTO.ActivitesDTO;
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

public class WikidataActivityWriter {

    private PropertyDocument propertyInstanceOf;
    private PropertyDocument propertyCapacity;
    private PropertyDocument propertyAddress;
    private PropertyDocument propertyContact;
    private PropertyDocument propertySchedule;

    public void writeActivityPage(ActivitesDTO activity) {
        WikibaseDataEditor wbde = new WikibaseDataEditor(WikidataLogger.WikibaseConnexion, WikidataLogger.WIKIBASE_SITE_IRI);
        try {
            propertyInstanceOf = (PropertyDocument) WikidataLogger.WikibaseWbdf.getEntityDocument(WikidataConstantes.PROPERTY_INSTANCE_OF);
            propertyAddress = (PropertyDocument) WikidataLogger.WikibaseWbdf.getEntityDocument(WikidataConstantes.PROPERTY_ADDRESS);
            propertyContact = (PropertyDocument) WikidataLogger.WikibaseWbdf.getEntityDocument(WikidataConstantes.PROPERTY_CONTACT);
            propertyCapacity = (PropertyDocument) WikidataLogger.WikibaseWbdf.getEntityDocument(WikidataConstantes.PROPERTY_SEATING_CAPACITY);
            propertySchedule = (PropertyDocument) WikidataLogger.WikibaseWbdf.getEntityDocument(WikidataConstantes.PROPERTY_SCHEDULE);
        } catch (MediaWikiApiErrorException ex) {
            Logger.getLogger(WikidataActivityWriter.class.getName()).log(Level.SEVERE, null, ex);
        }

        ItemIdValue noid = WikidataUtil.getObjectItemIdValue((ObjectDTO)activity);;

        ItemDocumentBuilder.forItemId(noid)
                .withLabel(activity.getNameActivity(), "en")
                .withLabel(activity.getNameActivity(), "fr")
                .withDescription(activity.getDescriptionActivity(), "fr");

        Statement statementInstanceOf = StatementBuilder
                .forSubjectAndProperty(noid, propertyInstanceOf.getPropertyId())
                .withValue(Datamodel.makeItemIdValue(WikidataConstantes.ITEM_ACTIVITY, WikidataLogger.WIKIBASE_SITE_IRI))
                .build();
                ItemDocumentBuilder.forItemId(noid).withStatement(statementInstanceOf);

        if (activity.getAddressActivity() != null && !activity.getAddressActivity().isEmpty()) {
        Statement statementAddress = StatementBuilder
                .forSubjectAndProperty(noid, propertyAddress.getPropertyId())
                .withValue(Datamodel.makeStringValue(activity.getAddressActivity()))
                .build();
                ItemDocumentBuilder.forItemId(noid).withStatement(statementAddress);
        }

        String contactQid = WikidataUtil.getOwner(activity.getContactActivity());
        if (!contactQid.isEmpty()) {
        Statement statementContact = StatementBuilder
                .forSubjectAndProperty(noid, propertyContact.getPropertyId())
                .withValue(Datamodel.makeItemIdValue(contactQid, WikidataLogger.WIKIBASE_SITE_IRI))
                .build();
                ItemDocumentBuilder.forItemId(noid).withStatement(statementContact);
        }

        if (activity.getContactActivity() != null) {
        Statement statementCapacity = StatementBuilder
                .forSubjectAndProperty(noid, propertyCapacity.getPropertyId())
                .withValue(Datamodel.makeQuantityValue(new BigDecimal(activity.getCapacityActivity())))
                .build();
                ItemDocumentBuilder.forItemId(noid).withStatement(statementCapacity);
        }

        if (activity.getScheduleActivity() != null && !activity.getScheduleActivity().isEmpty()) {
        Statement statementSchedule = StatementBuilder
                .forSubjectAndProperty(noid, propertySchedule.getPropertyId())
                .withValue(Datamodel.makeStringValue(activity.getScheduleActivity()))
                .build();
                ItemDocumentBuilder.forItemId(noid).withStatement(statementSchedule);
        }
        ItemDocument itemDocument = ItemDocumentBuilder.forItemId(noid).build();
        try {
            ItemDocument newItemDocument = wbde.createItemDocument(itemDocument, "Statement created by the bot " + Util.getProperty("usn_wikibase"));
        } catch (IOException | MediaWikiApiErrorException ex) {
            Logger.getLogger(WikidataActivityWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
        Logger.getLogger(WikidataActivityWriter.class.getName()).log(Level.INFO, "Created or updating {0}", activity.getNameActivity());
    }
}
