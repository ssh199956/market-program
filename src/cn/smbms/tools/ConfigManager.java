package cn.smbms.tools;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 读取配置文件工具类--单例模式
 * 
 * @author ASUS123
 * 
 */
public class ConfigManager {
	private static ConfigManager configManager;
	private static Properties properties;

	private ConfigManager() throws IOException {
		// 读取配置文件
		properties = new Properties();
		InputStream is = ConfigManager.class.getClassLoader()
				.getResourceAsStream("database.properties");
		properties.load(is);
		is.close();
	}

	// 全局访问点
	public static synchronized ConfigManager getInstance() throws IOException {
		if (configManager == null) {
			configManager = new ConfigManager();
		}
		return configManager;
	}

	public String getValue(String key) {
		return properties.getProperty(key);
	}
}
