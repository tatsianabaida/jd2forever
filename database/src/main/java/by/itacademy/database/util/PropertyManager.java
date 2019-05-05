package by.itacademy.database.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PropertyManager {

    private static final Properties PROPERTIES = new Properties();

    static {
        loadApplicationProperties();
    }

    private static void loadApplicationProperties() {
        InputStream inputStream = Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream("application.properties");
        if (!Objects.isNull(inputStream)) {
            try {
                PROPERTIES.load(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String get(String key) {
        return PROPERTIES.getProperty(key);
    }
}
