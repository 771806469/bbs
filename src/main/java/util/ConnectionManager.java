package util;


import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;



public class ConnectionManager {

    private static BasicDataSource basicDataSource = new BasicDataSource();
    private static String url;
    private static String psd;
    private static String name;
    private static String driver;

    static {

            driver = Config.get("jdbc.driver");
            url = Config.get("jdbc.url");
            name = Config.get("jdbc.username");
            psd = Config.get("jdbc.password");

            basicDataSource.setUrl(url);
            basicDataSource.setDriverClassName(driver);
            basicDataSource.setUsername(name);
            basicDataSource.setPassword(psd);


            basicDataSource.setInitialSize(5);
            basicDataSource.setMinIdle(5);
            basicDataSource.setMaxIdle(20);
            basicDataSource.setMaxWaitMillis(5000);

    }

    public static DataSource getBasicDataSource() {
        return basicDataSource;
    }

    public static Connection getConnection() throws SQLException {
        return basicDataSource.getConnection();
    }
}

