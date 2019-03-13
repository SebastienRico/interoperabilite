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

        String phone = item.getStatementGroups().get(1).getStatements().get(0).getValue().toString().replaceAll("^\"|\"$", "");
        contact.setPhoneContact(phone);

        String website = item.getStatementGroups().get(2).getStatements().get(0).getValue().toString().replaceAll("^\"|\"$", "");
        contact.setWebsiteContact(website);

        String fax = item.getStatementGroups().get(3).getStatements().get(0).getValue().toString().replaceAll("^\"|\"$", "");
        contact.setFaxContact(fax);

        String name = item.getStatementGroups().get(4).getStatements().get(0).getValue().toString().replaceAll("^\"|\"$", "");
        contact.setNamePerson(name);

        String firstName = item.getStatementGroups().get(5).getStatements().get(0).getValue().toString().replaceAll("^\"|\"$", "");
        contact.setFirstnamePerson(firstName);

        //System.out.println("Mail " + item.getStatementGroups().get(6).getStatements().get(0).getValue().toString());
        String mail = item.getStatementGroups().get(6).getStatements().get(0).getValue().toString().replaceAll("^\"|\"$", "");
        contact.setMailContact(mail);

        return contact;
    }
}
