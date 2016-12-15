package util;


import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;


public class ConnectionManager {

    private static BasicDataSource basicDataSource = new BasicDataSource();
    private static String url;
    private static String psd;
    private static String name;
    private static String driver;

    static {
        Properties prop = new Properties();
        try {
            prop.load(ConnectionManager.class.getClassLoader().getResourceAsStream("config.properties"));

            driver = prop.getProperty("jdbc.driver");
            url = prop.getProperty("jdbc.url");
            name = prop.getProperty("jdbc.username");
            psd = prop.getProperty("jdbc.password");

            basicDataSource.setUrl(url);
            basicDataSource.setDriverClassName(driver);
            basicDataSource.setUsername(name);
            basicDataSource.setPassword(psd);


            basicDataSource.setInitialSize(5);
            basicDataSource.setMinIdle(5);
            basicDataSource.setMaxIdle(20);
            basicDataSource.setMaxWaitMillis(5000);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static DataSource getBasicDataSource() {
        return basicDataSource;
    }

    public static Connection getConnection() throws SQLException {
        return basicDataSource.getConnection();
    }
}

