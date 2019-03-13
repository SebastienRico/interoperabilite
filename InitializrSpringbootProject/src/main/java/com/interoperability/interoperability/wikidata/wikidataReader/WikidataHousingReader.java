package com.interoperability.interoperability.wikidata.wikidataReader;

import com.interoperability.interoperability.objetsDTO.HostelDTO;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.wikidata.wdtk.datamodel.interfaces.ItemDocument;
import org.wikidata.wdtk.wikibaseapi.ApiConnection;
import org.wikidata.wdtk.wikibaseapi.WikibaseDataFetcher;
import org.wikidata.wdtk.wikibaseapi.apierrors.MediaWikiApiErrorException;

public class WikidataHousingReader {

    public static HostelDTO readHousingPage(String QID) {
        HostelDTO housing = new HostelDTO();

        String siteIri = "http://qanswer-svc1.univ-st-etienne.fr/index.php";
        ApiConnection con = new ApiConnection("http://qanswer-svc1.univ-st-etienne.fr/api.php");

        WikibaseDataFetcher wbdf = new WikibaseDataFetcher(con, siteIri);
        ItemDocument item = null;

        try {
            item = (ItemDocument) wbdf.getEntityDocument(QID);
        } catch (MediaWikiApiErrorException ex) {
            Logger.getLogger(WikidataRestaurantReader.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println("Phone " + item.getStatementGroups().get(1).getStatements().get(0).getValue().toString());

        System.out.println("Website " + item.getStatementGroups().get(2).getStatements().get(0).getValue().toString());

        System.out.println("Fax " + item.getStatementGroups().get(3).getStatements().get(0).getValue().toString());

        System.out.println("Nom " + item.getStatementGroups().get(4).getStatements().get(0).getValue().toString());

        System.out.println("Prenom " + item.getStatementGroups().get(5).getStatements().get(0).getValue().toString());

        System.out.println("Mail " + item.getStatementGroups().get(6).getStatements().get(0).getValue().toString());

        return housing;
    }
}
