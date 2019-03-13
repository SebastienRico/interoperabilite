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
            //insertConnexion();
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
            // put here all items to insert
            statement.execute("insert into item(id, label) values ('Q50', 'restaurant');");
            statement.execute("insert into item(id, label) values ('Q101', 'activite');");
            statement.execute("insert into item(id, label) values ('Q1298', 'human');");
            statement.execute("insert into item(id, label) values ('Q99', 'event');");
            statement.execute("insert into item(id, label) values ('Q88', 'hostel');");
            statement.execute("insert into item(id, label) values ('Q1640', 'rent');");
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
            statement.execute("insert into property(id, label) values ('P16', 'instance of');");
            statement.execute("insert into property(id, label) values ('P1076', 'has for address');");
            statement.execute("insert into property(id, label) values ('P1064', 'has for capacity');");
            statement.execute("insert into property(id, label) values ('P61', 'owner');");
            statement.execute("insert into property(id, label) values ('P1073', 'has for schedule');");
            statement.execute("insert into property(id, label) values ('P1078', 'has for name');");
            statement.execute("insert into property(id, label) values ('P1068', 'has for firstname');");
            statement.execute("insert into property(id, label) values ('P1069', 'has for fax');");
            statement.execute("insert into property(id, label) values ('P981', 'has for phone');");
            statement.execute("insert into property(id, label) values ('P1079', 'has for mail');");
            statement.execute("insert into property(id, label) values ('P172', 'has for website');");
            statement.execute("insert into property(id, label) values ('P1077', 'has for type');");
            statement.execute("insert into property(id, label) values ('P1083', 'has for date start');");
            statement.execute("insert into property(id, label) values ('P1084', 'has for date end');");
            statement.execute("insert into property(id, label) values ('P1087', 'has for price');");
            statement.execute("insert into property(id, label) values ('P1088', 'has for stars');");
            statement.execute("insert into property(id, label) values ('P1089', 'has for periode');");
            statement.execute("insert into property(id, label) values ('P1085', 'has for disponibility');");
            statement.execute("insert into property(id, label) values ('P1072', 'has for menu');");
            Logger.getLogger(DatabaseController.class.getName()).log(Level.INFO, "Inserting propertyDocuments ok");
            statement.close();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseController.class.getName()).log(Level.SEVERE, "Impossible to insert PropertyDocuments", ex);
        }
    }
    
    private void insertConnexion() throws SQLException {
        Statement statement;
        try {
            statement = connexion.createStatement();
            // put here all properties to insert
            statement.execute("insert into connexion(login, password) values ('brigite', 'dusecretariat);");
            Logger.getLogger(DatabaseController.class.getName()).log(Level.INFO, "Inserting connexions ok");
            statement.close();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseController.class.getName()).log(Level.SEVERE, "Impossible to insert connexions", ex);
        }
    }
}
