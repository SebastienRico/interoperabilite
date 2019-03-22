package com.interoperability.interoperability.wikidata.wikidataReader;

import com.interoperability.interoperability.objetsDTO.ActivitesDTO;
import com.interoperability.interoperability.objetsDTO.ContactDTO;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.wikidata.wdtk.datamodel.interfaces.ItemDocument;
import org.wikidata.wdtk.wikibaseapi.ApiConnection;
import org.wikidata.wdtk.wikibaseapi.WikibaseDataFetcher;
import org.wikidata.wdtk.wikibaseapi.apierrors.MediaWikiApiErrorException;

public class WikidataActivitiesReader {

    public static ActivitesDTO readActivitiesPage(String QID) {
        ActivitesDTO activity = new ActivitesDTO();
        ContactDTO contactActivity = new ContactDTO();

        String siteIri = "http://qanswer-svc1.univ-st-etienne.fr/index.php";
        ApiConnection con = new ApiConnection("http://qanswer-svc1.univ-st-etienne.fr/api.php");

        WikibaseDataFetcher wbdf = new WikibaseDataFetcher(con, siteIri);
        ItemDocument item = null;

        try {
            item = (ItemDocument) wbdf.getEntityDocument(QID);
        } catch (MediaWikiApiErrorException ex) {
            Logger.getLogger(WikidataRestaurantReader.class.getName()).log(Level.SEVERE, null, ex);
        }

        activity.setNameActivity(item.getLabels().get("en").getText());

        activity.setDescriptionActivity(item.getDescriptions().get("fr").getText());

        for (int i = 0; i < item.getStatementGroups().size(); i++) {
            String statement = item.getStatementGroups().get(i).getStatements().get(0).toString();
            if (statement.contains("P1076")) {
                String adresse = item.getStatementGroups().get(i).getStatements().get(0).getValue().toString().replaceAll("^\"|\"$", "");
                activity.setAddressActivity(adresse);
            }
            if (statement.contains("P1073")) {
                String schedule = item.getStatementGroups().get(i).getStatements().get(0).getValue().toString().replaceAll("^\"|\"$", "");
                activity.setScheduleActivity(schedule);
            }
            if (statement.contains("P1064")) {
                activity.setCapacityActivity(Integer.parseInt(item.getStatementGroups().get(i).getStatements().get(0).getValue().toString()));
            }
            if (statement.contains("P61")) {
                
                //Get The contact Qid
                String contactsplit = item.getStatementGroups().get(i).getStatements().get(0).getValue().toString();
                String[] array = contactsplit.split("php");
                String[] array2 = array[1].split(" ");

                contactActivity = WikidataContactReader.readContactPage(array2[0]);
                activity.setContactActivity(contactActivity);
            }
        }
        return activity;
    }
}
