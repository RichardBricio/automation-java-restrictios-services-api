package properties;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class GetProperties {
	
	Properties prop = new Properties();
	
	public String getValueFromEnvironment(String key) throws IOException {
		
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "java" + File.separator + "com" + File.separator + "serasa" + File.separator + "properties" + File.separator + "env.properties");
		prop.load(fis);
		return prop.getProperty(key);
	}
	
	public String getValueFromCredential(String key) throws IOException {
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "java" + File.separator + "com" + File.separator + "serasa" + File.separator + "properties" + File.separator + "credential.properties");
		prop.load(fis);
		return prop.getProperty(key);
	}
	
}
