package com.interoperability.interoperability.wikidata;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.wikidata.wdtk.datamodel.helpers.ItemDocumentBuilder;
import org.wikidata.wdtk.datamodel.helpers.StatementBuilder;
import org.wikidata.wdtk.datamodel.interfaces.ItemDocument;
import org.wikidata.wdtk.datamodel.interfaces.ItemIdValue;
import org.wikidata.wdtk.datamodel.interfaces.PropertyDocument;
import org.wikidata.wdtk.datamodel.interfaces.Statement;
import org.wikidata.wdtk.wikibaseapi.WikibaseDataEditor;
import org.wikidata.wdtk.wikibaseapi.apierrors.MediaWikiApiErrorException;

public class WikidataWritter {

    public void writeWikidataPage() {
        //Example for writing information about an entity, here the example of creating Florance Garrelie working at Laboratoire Huber Curien
        //For more examples give a look at: https://github.com/Wikidata/Wikidata-Toolkit-Examples/blob/master/src/examples/EditOnlineDataExample.java
        /**WikibaseDataEditor wbde = new WikibaseDataEditor(WikidataLogger.WikibaseConnexion, WikidataLogger.WIKIBASE_SITE_IRI);
        PropertyDocument propertyTravaille = null;
        ItemDocument item = null;
        try {
            item = (ItemDocument) WikidataLogger.WikibaseWbdf.getEntityDocument("Q543654");
            //propertyTravaille = (PropertyDocument) WikidataLogger.wbdf.getEntityDocument("P242");
        } catch (MediaWikiApiErrorException ex) {
            Logger.getLogger(WikidataWritter.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(propertyTravaille.getLabels());

        ItemIdValue noid = ItemIdValue.NULL; // used when creating new items
        Statement statement = StatementBuilder
                .forSubjectAndProperty(noid, propertyTravaille.getPropertyId())
                .withValue(item.getItemId()).build();
        ItemDocument itemDocument = ItemDocumentBuilder.forItemId(noid)
                .withLabel("Florence Garrelie", "en")
                .withLabel("Florence Garrelie", "fr")
                .withStatement(statement).build();
        try {
            ItemDocument newItemDocument = wbde.createItemDocument(itemDocument,
                    "Statement created by our bot");
        } catch (IOException | MediaWikiApiErrorException e) {
            e.printStackTrace();
        }
        System.out.println("Created Florence Garrelie");
        System.out.println("Created statement: Florence Garrelie travaille Laboratoire Huber Curien");
        System.out.println(itemDocument.getItemId().getId());**/
    }

}
