package com.interoperability.interoperability.wikidata;

import com.interoperability.interoperability.utilities.Util;
import org.wikidata.wdtk.util.WebResourceFetcherImpl;
import org.wikidata.wdtk.wikibaseapi.ApiConnection;
import org.wikidata.wdtk.wikibaseapi.LoginFailedException;
import org.wikidata.wdtk.wikibaseapi.WikibaseDataFetcher;

public class WikidataLogger {
    
    public static final String WIKIDATA_SITE_IRI = Util.getProperty("uri_wikidata");
    private static final String WIKIDATA_URL = Util.getProperty("url_wikidataapi");
    public static WikibaseDataFetcher WikidataWbdf;
    public static ApiConnection WikidataConnexion;
    
    public static final String WIKIBASE_SITE_IRI = Util.getProperty("uri_wikibase");
    private static final String WIKIBASE_URL = Util.getProperty("url_wikibaseapi");
    public static WikibaseDataFetcher WikibaseWbdf;
    public static ApiConnection WikibaseConnexion;
    
    private static final String WIKIBASE_LOGIN = Util.getProperty("usn_wikibase");
    private static final String WIKIBASE_PASSWORD = Util.getProperty("pwd_wikibase");
    
    private static final String WIKIDATA_LOGIN = Util.getProperty("pwd_wikibase");
    private static final String WIKIDATA_PASSWORD = Util.getProperty("pwd_wikidata");

    public static void connectToWikidata() {
        WebResourceFetcherImpl.setUserAgent("Wikidata Toolkit EditOnlineDataExample");
        WikidataConnexion = new ApiConnection(WIKIDATA_URL);
        WikidataWbdf = new WikibaseDataFetcher(WikidataConnexion, WIKIDATA_SITE_IRI);

        try {
            //Put in the first place the user with which you created the bot account
            //Put as password what you get when you create the bot account
            WikidataConnexion.login(WIKIDATA_LOGIN, WIKIBASE_PASSWORD);
        } catch (LoginFailedException e) {
            e.printStackTrace();
        }
    }
    
    public static void connectToWikibase() {
        WebResourceFetcherImpl.setUserAgent("Wikidata Toolkit EditOnlineDataExample");
        WikibaseConnexion = new ApiConnection(WIKIBASE_URL);
        WikibaseWbdf = new WikibaseDataFetcher(WikibaseConnexion, WIKIBASE_SITE_IRI);

        try {
            //Put in the first place the user with which you created the bot account
            //Put as password what you get when you create the bot account
            WikibaseConnexion.login(WIKIBASE_LOGIN, WIKIBASE_PASSWORD);
        } catch (LoginFailedException e) {
            e.printStackTrace();
        }
    }
}
