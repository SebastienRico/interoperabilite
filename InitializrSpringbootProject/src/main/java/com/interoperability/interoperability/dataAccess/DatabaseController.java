package com.interoperability.interoperability.dataAccess;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseController {
    
    public static Connection connection;
    
    public void instanciateDatabase() {
        getConnection();
        try {
            createDatabaseIfNotExiste();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void getConnection(){
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:database.db");
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DatabaseController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void createDatabaseIfNotExiste() throws SQLException {
        if(!databaseExiste()){
            File file = new File("./././database.db");
            createTables();
            insertData();
        } else {
            Logger.getLogger(DatabaseController.class.getName()).log(Level.INFO, "La BDD existe deja");
        }
    }

    private boolean databaseExiste() throws SQLException {
        boolean result = false;
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT name from sqlite_master WHERE type='table'");
        if(rs.next()){
            result = true;
        }
        return result;
    }

    private void createTables() {
        createTable();
    }
    
    private void createTable() {
        Statement statement;
        try {
            statement = connection.createStatement();
            statement.execute("create table wikibase (" +
            "    id INT, " +
            "    type VARCHAR" +
            "    designation VARCHAR, " +
            "    PRIMARY KEY (id, ), " +
            ");");
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseController.class.getName()).log(Level.SEVERE, null, "Impossible de creer la table liste_demande_objet :" + ex);
        }
    }

    private void insertData() {
        Statement statement;
        try {
            statement = connection.createStatement();
            statement.execute("insert into wikibase values (50, 'Q', 'restaurant'); ");
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseController.class.getName()).log(Level.SEVERE, "Impossible to insert element or properties", ex);
        }
    }
}
