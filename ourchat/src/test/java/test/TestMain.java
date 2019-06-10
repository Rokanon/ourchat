/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import app.utils.ConnectionProperties;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dark
 */
public class TestMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Connection connection = ConnectionProperties.createConnection();
        Statement statement;
        try {
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select table_name from information_schema.tables where table_schema='" + ConnectionProperties.DATABASE + "';");
            while (rs.next()) {
                System.out.println("table_name: " + rs.getString("table_name"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(TestMain.class.getName()).log(Level.SEVERE, null, ex);
        }
        ConnectionProperties.closeConnection(connection);
 
    }
}
