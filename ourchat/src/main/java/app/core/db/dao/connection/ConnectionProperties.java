package app.core.db.dao.connection;

import app.core.utils.Config;
import app.core.utils.ConfigKeys;

public class ConnectionProperties {

    private static final String DEFAULT_CONNECTION_DRIVER = "oracle.jdbc.driver.OracleDriver";
    private static final String DEFAULT_DB_URL = "jdbc:oracle:thin:BGDEV@localhost:1521/ORCL";
    private static final String DEFAULT_DB_USER = "dmpnikola";
    private static final String DEFAULT_DB_PASS = "dmpnikolap";

    private String connectionDriver = null;
    private String dbUrl = null;
    private String dbUser = null;
    private String dbPass = null;

    public ConnectionProperties(String typePrefix) {
        connectionDriver = Config.get(typePrefix + ConfigKeys.CONNECTION_DRIVER);
        dbUrl = Config.get(typePrefix + ConfigKeys.DB_URL);
        dbUser = Config.get(typePrefix + ConfigKeys.DB_USER);
        dbPass = Config.get(typePrefix + ConfigKeys.DB_PASS);
    }

    public String getConnectionDriver() {
        return connectionDriver;
    }

    public String getDbUrl() {
        return dbUrl;
    }

    public String getDbUser() {
        return dbUser;
    }

    public String getDbPass() {
        return dbPass;
    }

    @Override
    public String toString() {
        return String.format("dbUrl: %s\ndbUser: %s\ndbPass: %s", dbUrl, dbUser, dbPass);
    }

}
