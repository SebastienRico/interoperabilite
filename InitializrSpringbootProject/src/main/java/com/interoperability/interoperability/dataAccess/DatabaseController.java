package com.interoperability.interoperability.dataAccess;

import com.interoperability.interoperability.utilities.Util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseController {
    
    private static final String DATABASE_URL = Util.getProperty("spring.datasource.url");
    private static final String DATABASE_USERNAME = Util.getProperty("usn_database");
    private static final String DATABASE_PASSWORD = Util.getProperty("pwd_database");
    
    private Connection connexion;
    
    public void setDatabase(){
        try {
            connexion = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            insertItemDocuments();
            insertPropertyDocuments();
            Logger.getLogger(DatabaseController.class.getName()).log(Level.INFO, "Inserting data ok");
            connexion.close();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseController.class.getName()).log(Level.SEVERE, "The SQL scripts don't work", ex);
        }
    }

    private void insertItemDocuments() throws SQLException {
        Statement statement;
        try {
            statement = connexion.createStatement();
            statement.execute("insert into item(id, label) values ('50', 'restaurant');");
            // put here all items to insert
            Logger.getLogger(DatabaseController.class.getName()).log(Level.INFO, "Inserting itemDocuments ok");
            statement.close();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseController.class.getName()).log(Level.SEVERE, "Impossible to insert ItemDocuments", ex);
        }
    }

    private void insertPropertyDocuments() throws SQLException {
        Statement statement;
        try {
            statement = connexion.createStatement();
            // put here all properties to insert
            //statement.execute("insert into property(id, label) values ('50', 'restaurant');");
            Logger.getLogger(DatabaseController.class.getName()).log(Level.INFO, "Inserting propertyDocuments ok");
            statement.close();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseController.class.getName()).log(Level.SEVERE, "Impossible to insert PropertyDocuments", ex);
        }
    }
}
