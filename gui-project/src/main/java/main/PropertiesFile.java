package main;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

public class PropertiesFile {
	private Properties prop;
	private static PropertiesFile instance;
	private OutputStream output;

	private PropertiesFile() throws FileNotFoundException {
		prop = new Properties();
		output = new FileOutputStream("config.properties");
	}

	public static PropertiesFile getInstance() throws FileNotFoundException {
		if (instance == null) {
			instance = new PropertiesFile();
		}
		return instance;
	}

	public void addProperty(String key, String value) throws FileNotFoundException {
		prop.setProperty(key, value);
	}

	public String getProperty(String key) {
		return prop.getProperty(key);
	}

	public void save() throws IOException {
		prop.store(output, "Configuring implementation type");
	}

}
