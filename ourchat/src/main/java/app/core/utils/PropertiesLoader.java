package app.core.utils;

import java.io.InputStream;
import java.util.Properties;

public class PropertiesLoader {
	public static Properties getProperties(String fileName) {
		Properties properties = null;
		try {
			properties = new Properties();
			InputStream is = PropertiesLoader.class.getClassLoader().getResourceAsStream(fileName + ".properties");
			properties.load(is);
		} catch (Exception e) {		
			e.printStackTrace();
		}
		return properties;
	}
}
