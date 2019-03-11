package com.interoperability.interoperability.wikidata.wikidataWriter;

import com.interoperability.interoperability.objetsDTO.ContactDTO;
import com.interoperability.interoperability.repositories.ItemDocumentRepository;
import com.interoperability.interoperability.repositories.PropertyDocumentRepository;
import com.interoperability.interoperability.utilities.Util;
import com.interoperability.interoperability.wikidata.WikidataLogger;
import com.interoperability.interoperability.wikidata.WikidataUtil;
import java.io.IOException;
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

public class WikidataContactWriter {

    @Autowired
    ItemDocumentRepository itemDocumentRepository;

    @Autowired
    PropertyDocumentRepository propertyDocumentRepository;

    private static String ITEM_PERSON = "";
    private static final String PROPERTY_INSTANCE_OF = "P16";
    private static final String PROPERTY_NAME = "";
    private static final String PROPERTY_FIRSTNAME = "";
    private static final String PROPERTY_FAX = "";
    private static final String PROPERTY_PHONE = "";
    private static final String PROPERTY_MAIL = "";
    private static final String PROPERTY_WEBSITE = "";
    
    private PropertyDocument propertyInstanceOf;
    private PropertyDocument propertyName;
    private PropertyDocument propertyFirstName;
    private PropertyDocument propertyFax;
    private PropertyDocument propertyPhone;
    private PropertyDocument propertyMail;
    private PropertyDocument propertyWebsite;

    public void writeContactPage(ContactDTO contact) {
        WikibaseDataEditor wbde = new WikibaseDataEditor(WikidataLogger.WikibaseConnexion, WikidataLogger.WIKIBASE_SITE_IRI);

        ITEM_PERSON = WikidataUtil.getOwner(contact);
        
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
        Statement statement = StatementBuilder
                .forSubjectAndProperty(noid, propertyInstanceOf.getPropertyId())
                .withValue(Datamodel.makeItemIdValue(ITEM_PERSON, WikidataLogger.WIKIBASE_SITE_IRI))
                .forSubjectAndProperty(noid, propertyName.getPropertyId())
                .withValue(Datamodel.makeItemIdValue(contact.getNamePerson(), WikidataLogger.WIKIBASE_SITE_IRI))
                .forSubjectAndProperty(noid, propertyFirstName.getPropertyId())
                .withValue(Datamodel.makeItemIdValue(contact.getFirstnamePerson(), WikidataLogger.WIKIBASE_SITE_IRI))
                .forSubjectAndProperty(noid, propertyFax.getPropertyId())
                .withValue(Datamodel.makeItemIdValue(contact.getFaxContact(), WikidataLogger.WIKIBASE_SITE_IRI))
                .forSubjectAndProperty(noid, propertyPhone.getPropertyId())
                .withValue(Datamodel.makeItemIdValue(contact.getPhoneContact().toString(), WikidataLogger.WIKIBASE_SITE_IRI))
                .forSubjectAndProperty(noid, propertyMail.getPropertyId())
                .withValue(Datamodel.makeItemIdValue(contact.getMailContact(), WikidataLogger.WIKIBASE_SITE_IRI))
                .forSubjectAndProperty(noid, propertyWebsite.getPropertyId())
                .withValue(Datamodel.makeItemIdValue(contact.getWebsiteContact(), WikidataLogger.WIKIBASE_SITE_IRI))
                .build();
        ItemDocument itemDocument = ItemDocumentBuilder.forItemId(noid)
                .withLabel("La mandarine", "en")
                .withLabel("La mandarine", "fr")
                .withStatement(statement)
                .build();
        try {
            ItemDocument newItemDocument = wbde.createItemDocument(itemDocument,
                    "Statement created by the bot " + Util.getProperty("usn_wikibase"));
        } catch (IOException | MediaWikiApiErrorException e) {
            e.printStackTrace();
        }
        Logger.getLogger(WikidataRestaurantWriter.class.getName()).log(Level.INFO, "Created or updating {0}", contact.getFirstnamePerson() + " " + contact.getNamePerson());
        com.interoperability.interoperability.models.ItemDocument databaseItemDocument = new com.interoperability.interoperability.models.ItemDocument();
        databaseItemDocument.setId(itemDocument.getItemId().getId());
        databaseItemDocument.setLabel(contact.getFirstnamePerson() + " " + contact.getNamePerson());
        itemDocumentRepository.save(databaseItemDocument);//System.out.println(itemDocument.getItemId().getId());
    }
}
