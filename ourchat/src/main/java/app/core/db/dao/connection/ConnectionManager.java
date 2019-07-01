package app.core.db.dao.connection;

import app.core.db.dao.enums.ConnectionType;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import app.core.utils.ConfigKeys;

public class ConnectionManager {

    private ConnectionProperties consumerProperties = null;
    private ConnectionProperties producerProperties = null;

    private static ConnectionManager instance = null;

    private ConnectionManager() {
        consumerProperties = new ConnectionProperties(ConfigKeys.CONSUMER_PREFIX);
        producerProperties = new ConnectionProperties(ConfigKeys.PRODUCER_PREFIX);
    }

    public static ConnectionManager getInstance() {
        if (instance == null) {
            synchronized (ConnectionManager.class) {
                if (instance == null) {
                    instance = new ConnectionManager();
                }
            }
        }
        return instance;
    }

    public Connection getConnection(ConnectionType type) {
        return loadConnection(type.equals(ConnectionType.CONSUMER) ? consumerProperties : producerProperties);
    }

    private Connection loadConnection(ConnectionProperties properties) {
        Connection conn = null;
        try {
            Class.forName(properties.getConnectionDriver());
            conn = DriverManager.getConnection(properties.getDbUrl(), properties.getDbUser(), properties.getDbPass());
            conn.setAutoCommit(false);
            return conn;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public void closeConnection(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
