package wikibase.utilities;

import java.io.InputStream;
import java.util.Properties;

public class Util {
    /**
     * get property from params.properties file
     *
     * @param paramName parameter name
     * @return the of the given parameter
     */
    public static String getProperty(String paramName) {
        String param = "";
        try {
            Properties prop = new Properties();
            String propFileName = "application.properties";
            InputStream inputStream = Util.class.getClassLoader().getResourceAsStream(propFileName);
            if (inputStream != null) {
                prop.load(inputStream);
            }
            param = prop.getProperty(paramName);
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return param;

    }
}
