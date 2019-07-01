/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.core.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dark
 */
public class ConnectionProperties {

    public static final String HOST = "localhost";
    public static final String PORT = "3306";
    public static final String DATABASE = "ourchat";
    public static final String USER = "";
    public static final String PASSWORD = "";
    public static final String OTHER_PARAMS = "autoReconnect=true&useUnicode=true&characterEncoding=utf-8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

    private static Connection connection;

    public static Connection createConnection() {
        if (null == connection) {
            try {
                Class.forName("com.mysql.jdbc.Driver"); 
                connection = DriverManager.getConnection(connectionUri());
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(ConnectionProperties.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return connection;
    }

    private static String connectionUri() {
        StringBuilder connectionUriBuilder = new StringBuilder();
        connectionUriBuilder.append("jdbc:mysql://").append(HOST).append(":").append(PORT)
                .append("/").append(DATABASE).append("?user=").append(USER)
                .append("&password=").append(PASSWORD).append("&").append(OTHER_PARAMS);
        return connectionUriBuilder.toString();
    }

    public static void closeConnection(Connection connection) {
        try {
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionProperties.class.getName()).log(Level.SEVERE, null, ex);
        }
        connection = null;
    }

}
