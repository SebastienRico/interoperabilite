package com.interoperability.interoperability.wikidata.wikidataReader;

import com.interoperability.interoperability.objetsDTO.ContactDTO;
import org.wikidata.wdtk.datamodel.interfaces.ItemDocument;
import org.wikidata.wdtk.wikibaseapi.ApiConnection;
import org.wikidata.wdtk.wikibaseapi.WikibaseDataFetcher;
import org.wikidata.wdtk.wikibaseapi.apierrors.MediaWikiApiErrorException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WikidataContactReader {

    public static ContactDTO readContactPage(String QID) {
        ContactDTO contact = new ContactDTO();

        String siteIri = "http://qanswer-svc1.univ-st-etienne.fr/index.php";
        ApiConnection con = new ApiConnection("http://qanswer-svc1.univ-st-etienne.fr/api.php");

        WikibaseDataFetcher wbdf = new WikibaseDataFetcher(con, siteIri);
        ItemDocument item = null;

        try {
            item = (ItemDocument) wbdf.getEntityDocument(QID);
        } catch (MediaWikiApiErrorException ex) {
            Logger.getLogger(WikidataRestaurantReader.class.getName()).log(Level.SEVERE, null, ex);
        }

        for (int i = 0; i < item.getStatementGroups().size(); i++) {
            String statement = item.getStatementGroups().get(i).getStatements().get(0).toString();
            if (statement.contains("P981")) {
                String phone = item.getStatementGroups().get(i).getStatements().get(0).getValue().toString().replaceAll("^\"|\"$", "");
                contact.setPhoneContact(phone);
            }
            if (statement.contains("P172")) {
                String website = item.getStatementGroups().get(i).getStatements().get(0).getValue().toString().replaceAll("^\"|\"$", "");
                contact.setWebsiteContact(website);
            }
            if (statement.contains("P1069")) {
                String fax = item.getStatementGroups().get(i).getStatements().get(0).getValue().toString().replaceAll("^\"|\"$", "");
                contact.setFaxContact(fax);
            }
            if (statement.contains("P1078")) {
                String name = item.getStatementGroups().get(i).getStatements().get(0).getValue().toString().replaceAll("^\"|\"$", "");
                contact.setNamePerson(name);
            }
            if (statement.contains("P1068")) {
                String firstName = item.getStatementGroups().get(i).getStatements().get(0).getValue().toString().replaceAll("^\"|\"$", "");
                contact.setFirstnamePerson(firstName);
            }
            if (statement.contains("P1079")) {
                String mail = item.getStatementGroups().get(i).getStatements().get(0).getValue().toString().replaceAll("^\"|\"$", "");
                contact.setMailContact(mail);
            }
        }

        return contact;
    }
}
