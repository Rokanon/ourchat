package app.core.utils;

import java.util.Properties;

public class Config {

    private static final Properties CONFIG = PropertiesLoader.getProperties("config");

    public static String get(String key) {
        return CONFIG.getProperty(key);
    }

    public static int getInt(String key) {
        return Integer.parseInt(get(key));
    }

    public static long getLong(String key) {
        return Long.parseLong(get(key));
    }
}
