package com.hichlink.funion.game.common.config;

import org.apache.commons.configuration.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aspire.webbas.configuration.config.ConfigurationHelper;

public class SystemConfig {
	private static final Logger LOGGER = LoggerFactory.getLogger(SystemConfig.class);

	private static Configuration configuration = null;
	private static SystemConfig config;
	private static final String DEFAULT_CONFIGURATION_FILENAME = "system-config.xml";
	private static String configurationFileName = DEFAULT_CONFIGURATION_FILENAME;

	private SystemConfig() {
		if (configuration == null)
			refresh();
	}

	public static SystemConfig getInstance() {
		if (config == null) {
			config = new SystemConfig();
		}
		return config;
	}

	private static void refresh() {
		configuration = ConfigurationHelper.getConfiguration(configurationFileName, 50000L);
		if (configuration == null)
			LOGGER.error("读portal配置文件失败, 配置文件：" + configurationFileName);
	}

	public String getString(String arg) {
		if (configuration == null) {
			return null;
		}
		return configuration.getString(arg);
	}

	public int getInt(String arg) {
		if (configuration == null) {
			return 0;
		}
		return configuration.getInt(arg);
	}

}