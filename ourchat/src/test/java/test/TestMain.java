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
import ourchat.ourchat.user.User;
import ourchat.ourchat.user.dao.UserDao;

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
        
        UserDao dao = new UserDao();
//        
        User user = dao.getById(1); // ne ubacuje u polja super klase
        
        System.out.println("User:\n" + user.toJson().toString(2));
//        
//        user.setEmail("email.com");
//        user.setPassword("oasss");
//        
//        dao.insert(user);
        
        
        
//        Statement statement;
//        try {
//            statement = connection.createStatement();
////            ResultSet rs = statement.executeQuery("select table_name from information_schema.tables where table_schema='" + ConnectionProperties.DATABASE + "';");
//            ResultSet rs = statement.executeQuery("select id, mail, password from user;");
//            while (rs.next()) {
//                System.out.println("id: " + rs.getString("id"));
//                System.out.println("email: " + rs.getString("mail"));
//                System.out.println("password: " + rs.getString("password"));
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(TestMain.class.getName()).log(Level.SEVERE, null, ex);
//        }
        ConnectionProperties.closeConnection(connection);
 
    }
}
