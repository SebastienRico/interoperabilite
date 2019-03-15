package com.interoperability.interoperability.wikidata.wikidataWriter;

import com.interoperability.interoperability.ObjectDTO;
import com.interoperability.interoperability.objetsDTO.ContactDTO;
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

public class WikidataContactWriter {

    private PropertyDocument propertyInstanceOf;
    private PropertyDocument propertyName;
    private PropertyDocument propertyFirstName;
    private PropertyDocument propertyFax;
    private PropertyDocument propertyPhone;
    private PropertyDocument propertyMail;
    private PropertyDocument propertyWebsite;

    private Boolean firstTry = true;

    public void writeContactPage(ContactDTO contact) {
        WikibaseDataEditor wbde = new WikibaseDataEditor(WikidataLogger.WikibaseConnexion, WikidataLogger.WIKIBASE_SITE_IRI);

        try {
            propertyInstanceOf = (PropertyDocument) WikidataLogger.WikibaseWbdf.getEntityDocument(WikidataConstantes.PROPERTY_INSTANCE_OF);
            propertyName = (PropertyDocument) WikidataLogger.WikibaseWbdf.getEntityDocument(WikidataConstantes.PROPERTY_NAME);
            propertyFirstName = (PropertyDocument) WikidataLogger.WikibaseWbdf.getEntityDocument(WikidataConstantes.PROPERTY_FIRSTNAME);
            propertyFax = (PropertyDocument) WikidataLogger.WikibaseWbdf.getEntityDocument(WikidataConstantes.PROPERTY_FAX);
            propertyPhone = (PropertyDocument) WikidataLogger.WikibaseWbdf.getEntityDocument(WikidataConstantes.PROPERTY_PHONE);
            propertyMail = (PropertyDocument) WikidataLogger.WikibaseWbdf.getEntityDocument(WikidataConstantes.PROPERTY_MAIL);
            propertyWebsite = (PropertyDocument) WikidataLogger.WikibaseWbdf.getEntityDocument(WikidataConstantes.PROPERTY_WEBSITE);
        } catch (MediaWikiApiErrorException ex) {
            Logger.getLogger(WikidataContactWriter.class.getName()).log(Level.SEVERE, null, ex);
        }

        ItemIdValue noid = ItemIdValue.NULL;
        if (!firstTry) {
            noid = WikidataUtil.getObjectItemIdValue((ObjectDTO) contact);
        }

        ItemDocumentBuilder itemDocumentBuilder = ItemDocumentBuilder.forItemId(noid)
                .withLabel(contact.getNamePerson(), "en")
                .withLabel(contact.getNamePerson(), "fr");

        Statement statementInstanceOf = StatementBuilder
                .forSubjectAndProperty(noid, propertyInstanceOf.getPropertyId())
                .withValue(Datamodel.makeItemIdValue(WikidataConstantes.ITEM_PERSON, WikidataLogger.WIKIBASE_SITE_IRI))
                .build();
        itemDocumentBuilder.withStatement(statementInstanceOf);

        if (contact.getNamePerson() != null && !contact.getNamePerson().isEmpty()) {
            Statement statementName = StatementBuilder
                    .forSubjectAndProperty(noid, propertyName.getPropertyId())
                    .withValue(Datamodel.makeStringValue(contact.getNamePerson()))
                    .build();
            itemDocumentBuilder.withStatement(statementName);
        }
        if (contact.getFirstnamePerson() != null && !contact.getFirstnamePerson().isEmpty()) {
            Statement statementFirstname = StatementBuilder
                    .forSubjectAndProperty(noid, propertyFirstName.getPropertyId())
                    .withValue(Datamodel.makeStringValue(contact.getFirstnamePerson()))
                    .build();
            itemDocumentBuilder.withStatement(statementFirstname);
        }
        if (contact.getFaxContact() != null && !contact.getFaxContact().isEmpty()) {
            Statement statementFax = StatementBuilder
                    .forSubjectAndProperty(noid, propertyFax.getPropertyId())
                    .withValue(Datamodel.makeStringValue(contact.getFaxContact()))
                    .build();
            itemDocumentBuilder.withStatement(statementFax);
        }
        if (contact.getPhoneContact() != null && !contact.getPhoneContact().isEmpty()) {
            Statement statementPhone = StatementBuilder
                    .forSubjectAndProperty(noid, propertyPhone.getPropertyId())
                    .withValue(Datamodel.makeStringValue(contact.getPhoneContact()))
                    .build();
            itemDocumentBuilder.withStatement(statementPhone);
        }
        if (contact.getMailContact() != null && !contact.getMailContact().isEmpty()) {
            Statement statementMail = StatementBuilder
                    .forSubjectAndProperty(noid, propertyMail.getPropertyId())
                    .withValue(Datamodel.makeStringValue(contact.getMailContact()))
                    .build();
            itemDocumentBuilder.withStatement(statementMail);
        }
        if (contact.getWebsiteContact() != null && !contact.getWebsiteContact().isEmpty()) {
            Statement statementWebsite = StatementBuilder
                    .forSubjectAndProperty(noid, propertyWebsite.getPropertyId())
                    .withValue(Datamodel.makeStringValue(contact.getWebsiteContact()))
                    .build();
            itemDocumentBuilder.withStatement(statementWebsite);
        }

        ItemDocument itemDocument = itemDocumentBuilder.build();

        String contactName = contact.getFirstnamePerson() + " " + contact.getNamePerson();
        if (firstTry) {
            firstTry = false;
            try {
                wbde.createItemDocument(itemDocument, "Statement created by the bot " + Util.getProperty("usn_wikibase"));
                Logger.getLogger(WikidataRestaurantWriter.class.getName()).log(Level.INFO, "{0} created", contactName);
            } catch (IOException | MediaWikiApiErrorException e) {
                Logger.getLogger(WikidataRestaurantWriter.class.getName()).log(Level.SEVERE, "Canot create " + contactName, e);
            } finally {
                writeContactPage(contact);
            }
        } else {
            try {
                wbde.editItemDocument(itemDocument, true, "Statement updated by the bot " + Util.getProperty("usn_wikibase"));
                Logger.getLogger(WikidataRestaurantWriter.class.getName()).log(Level.INFO, "{0} updated", contactName);
            } catch (IOException | MediaWikiApiErrorException ex) {
                Logger.getLogger(WikidataRestaurantWriter.class.getName()).log(Level.SEVERE, "Canot update " + contactName, ex);
            }
        }
    }
}
