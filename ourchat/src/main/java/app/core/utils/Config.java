package app.core.utils;

import java.util.Properties;

public class Config {

    private static final Properties CONFIG = PropertiesLoader.getProperties("config");

    public static String get(String key) {
        return CONFIG.getProperty(key);
    }

    public static Integer getInt(String key) {
        try {
            return Integer.parseInt(get(key));
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    public static Long getLong(String key) {

        try {
            return Long.parseLong(get(key));
        } catch (NumberFormatException ex) {
            return null;
        }
    }
}
