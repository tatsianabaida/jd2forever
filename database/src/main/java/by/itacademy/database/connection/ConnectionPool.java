package by.itacademy.database.connection;

import by.itacademy.database.util.PropertyManager;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;

import java.sql.Connection;
import java.sql.SQLException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ConnectionPool {

    private static final String URL_KEY = "db.url";
    private static final String USER_KEY = "db.user";
    private static final String PASSWORD_KEY = "db.password";
    private static final String DRIVER_KEY = "db.driver";
    private static final String POOL_SIZE_KEY = "db.pool.size";
    private static final String POOL_IDLE_KEY = "db.pool.idle";

    private static DataSource dataSource;

    static {
        initConnectionPool();
    }

    private static void initConnectionPool() {
        PoolProperties poolProperties = new PoolProperties();
        poolProperties.setDriverClassName(PropertyManager.get(DRIVER_KEY));
        poolProperties.setUrl(PropertyManager.get(URL_KEY));
        poolProperties.setUsername(PropertyManager.get(USER_KEY));
        poolProperties.setPassword(PropertyManager.get(PASSWORD_KEY));
        poolProperties.setMaxActive(Integer.parseInt(PropertyManager.get(POOL_SIZE_KEY)));
        poolProperties.setMaxIdle(Integer.parseInt(PropertyManager.get(POOL_IDLE_KEY)));
        dataSource = new DataSource(poolProperties);
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
