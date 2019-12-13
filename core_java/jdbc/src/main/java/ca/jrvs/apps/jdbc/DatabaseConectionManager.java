package ca.jrvs.apps.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConectionManager {

    private final String url;
    private final Properties properties;

    /**
     * Constructor for Database Connection Manager
     * param:host name, database name, user name, password
     */
    public DatabaseConectionManager (String host, String databaseName, String username, String password) {
        this.url = "jdbc:postgresql://" + host + "/" + databaseName;
        this.properties = new Properties();
        this.properties.setProperty("user", username);
        this.properties.setProperty("password", password);
    }

    /**
     * Set the connection to RDBMS
     * param:
     * return:Connection for RDBMS
     */
    public Connection getConnection () throws SQLException{
        return DriverManager.getConnection(this.url,this.properties);
    }
}
