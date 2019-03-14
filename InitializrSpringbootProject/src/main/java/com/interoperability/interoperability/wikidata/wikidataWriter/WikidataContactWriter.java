package com.interoperability.interoperability.wikidata.wikidataWriter;

import com.interoperability.interoperability.objetsDTO.ContactDTO;
import com.interoperability.interoperability.utilities.Util;
import com.interoperability.interoperability.wikidata.WikidataLogger;
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

public class WikidataContactWriter {

    private static final String ITEM_PERSON = "Q1298";
    private static final String PROPERTY_INSTANCE_OF = "P16";
    private static final String PROPERTY_NAME = "P1078";
    private static final String PROPERTY_FIRSTNAME = "P1068";
    private static final String PROPERTY_FAX = "P1069";
    private static final String PROPERTY_PHONE = "P981";
    private static final String PROPERTY_MAIL = "P1079";
    private static final String PROPERTY_WEBSITE = "P172";
    
    private PropertyDocument propertyInstanceOf;
    private PropertyDocument propertyName;
    private PropertyDocument propertyFirstName;
    private PropertyDocument propertyFax;
    private PropertyDocument propertyPhone;
    private PropertyDocument propertyMail;
    private PropertyDocument propertyWebsite;

    public void writeContactPage(ContactDTO contact) {
        WikibaseDataEditor wbde = new WikibaseDataEditor(WikidataLogger.WikibaseConnexion, WikidataLogger.WIKIBASE_SITE_IRI);

        try {
            propertyInstanceOf = (PropertyDocument) WikidataLogger.WikibaseWbdf.getEntityDocument(PROPERTY_INSTANCE_OF);
            propertyName = (PropertyDocument) WikidataLogger.WikibaseWbdf.getEntityDocument(PROPERTY_NAME);
            propertyFirstName = (PropertyDocument) WikidataLogger.WikibaseWbdf.getEntityDocument(PROPERTY_FIRSTNAME);
            propertyFax = (PropertyDocument) WikidataLogger.WikibaseWbdf.getEntityDocument(PROPERTY_FAX);
            propertyPhone = (PropertyDocument) WikidataLogger.WikibaseWbdf.getEntityDocument(PROPERTY_PHONE);
            propertyMail = (PropertyDocument) WikidataLogger.WikibaseWbdf.getEntityDocument(PROPERTY_MAIL);
            propertyWebsite = (PropertyDocument) WikidataLogger.WikibaseWbdf.getEntityDocument(PROPERTY_WEBSITE);
        } catch (MediaWikiApiErrorException ex) {
            Logger.getLogger(WikidataContactWriter.class.getName()).log(Level.SEVERE, null, ex);
        }

        ItemIdValue noid = ItemIdValue.NULL; // used when creating new items
        Statement statementInstanceOf = StatementBuilder
                .forSubjectAndProperty(noid, propertyInstanceOf.getPropertyId())
                .withValue(Datamodel.makeItemIdValue(ITEM_PERSON, WikidataLogger.WIKIBASE_SITE_IRI))
                .build();
        Statement statementName = StatementBuilder
                .forSubjectAndProperty(noid, propertyName.getPropertyId())
                .withValue(Datamodel.makeStringValue(contact.getNamePerson()))
                .build();
        Statement statementFirstname = StatementBuilder
                .forSubjectAndProperty(noid, propertyFirstName.getPropertyId())
                .withValue(Datamodel.makeStringValue(contact.getFirstnamePerson()))
                .build();
        Statement statementFax = StatementBuilder
                .forSubjectAndProperty(noid, propertyFax.getPropertyId())
                .withValue(Datamodel.makeStringValue(contact.getFaxContact()))
                .build();
        Statement statementPhone = StatementBuilder
                .forSubjectAndProperty(noid, propertyPhone.getPropertyId())
                .withValue(Datamodel.makeStringValue(contact.getPhoneContact()))
                .build();
        Statement statementMail = StatementBuilder
                .forSubjectAndProperty(noid, propertyMail.getPropertyId())
                .withValue(Datamodel.makeStringValue(contact.getMailContact()))
                .build();
        Statement statementWebsite = StatementBuilder
                .forSubjectAndProperty(noid, propertyWebsite.getPropertyId())
                .withValue(Datamodel.makeStringValue(contact.getWebsiteContact()))
                .build();
        ItemDocument itemDocument = ItemDocumentBuilder.forItemId(noid)
                .withLabel(contact.getFirstnamePerson() + " " + contact.getNamePerson(), "en")
                .withLabel(contact.getFirstnamePerson() + " " + contact.getNamePerson(), "fr")
                .withStatement(statementInstanceOf)
                .withStatement(statementName)
                .withStatement(statementFirstname)
                .withStatement(statementFax)
                .withStatement(statementPhone)
                .withStatement(statementMail)
                .withStatement(statementWebsite)
                .build();
        try {
            ItemDocument newItemDocument = wbde.createItemDocument(itemDocument, "Statement created by the bot " + Util.getProperty("usn_wikibase"));
        } catch (IOException | MediaWikiApiErrorException e) {
            e.printStackTrace();
        }
        Logger.getLogger(WikidataRestaurantWriter.class.getName()).log(Level.INFO, "Created or updating {0}", contact.getFirstnamePerson() + " " + contact.getNamePerson());
    }
}
