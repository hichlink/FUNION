package com.hichlink.funion.mh.common.servlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aspire.webbas.configuration.config.ConfigurationHelper;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;

public class DefaultInitServlet implements ServletContextListener {
	/**
	 * logger
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(DefaultInitServlet.class);
	/**
	 * LOG root key
	 */
	private static final String LOG_ROOT = "logbase.dir";
	/**
	 * application root key
	 */
	private static final String APP_ROOT_KEY = "APP_ROOT";
	/**
	 * config root key
	 */
	private static final String CONFIG_ROOT = "configPath";
	/**
	 * application root
	 */
	private String appRootPath;

	public void contextDestroyed(ServletContextEvent event) {
		LOGGER.info("Web Application Context【" + appRootPath + "】 Destroyed.");
	}

	public void contextInitialized(ServletContextEvent event) {
		ServletContext sc = event.getServletContext();
		LOGGER.info("Initializing Web Application Context【" + sc.getRealPath("/") + "】.");
		appRootPath = sc.getRealPath("/");
		System.setProperty(APP_ROOT_KEY, appRootPath);
		if (System.getProperty(APP_ROOT_KEY) == null) {
			System.setProperty(APP_ROOT_KEY, sc.getRealPath("/"));
			LOGGER.info("Initializing Web Application Context[" + System.getProperty(APP_ROOT_KEY) + "].");
		}
		// 初始化配置文件根目录
		initRootConfigPath(sc);

		// 初始化log4j配置(日志输出目录等)
		initLogback(sc);
	}

	/**
	 * 清理资源（相关timer的关闭）.
	 */
	public void destroy() {
	}

	/**
	 * 初始化配置文件根目录.
	 */
	private void initRootConfigPath(ServletContext sc) {

		System.out.print("设置系统配置文件根目录....");
		// 初始化配置文件根目录
		/**********************************
		 * 常用服务器缺省存放配置文件根目录： TOMCAT：$TOMCAT_HOME/bin/config OC4J:
		 * $OC4J_HOME/j2ee/home/sims/config
		 **********************************/
		String userDir = getUserDir();
		String configRootPath = userDir + "/config";
		ConfigurationHelper.setBasePath(configRootPath);

		System.out.println("done! 配置文件根目录:" + configRootPath);
	}

	/**
	 * 初始化日志输出文件根目录
	 * 
	 * @param sc
	 */
	private void initLogback(ServletContext sc) {
		// 首先从系统属性中读取
		String logRoot = System.getProperty(LOG_ROOT);
		// 再从web 上下文中读取
		if (StringUtils.isEmpty(logRoot)) {
			logRoot = sc.getInitParameter(LOG_ROOT);
		}
		// 默认
		if (StringUtils.isEmpty(logRoot)) {
			logRoot = getUserDir();
		}
		System.setProperty(LOG_ROOT, logRoot);
		LOGGER.info("Set the system Log file root directory is successful, the Log directory is:【{}】", logRoot);

		String logbackFile = "";
		try {
			logbackFile = ConfigurationHelper.getBasePath() + "/logback/logback.xml";
			if (logbackFile != null && !logbackFile.isEmpty()) {
				LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
				lc.reset();
				JoranConfigurator configurator = new JoranConfigurator();
				configurator.setContext(lc);
				configurator.doConfigure(logbackFile);
				LOGGER.info("Loaded logback configure file from {}.", logbackFile);
			} else {
				LOGGER.info("No logback configuration location specified, keepping default.");
			}
		} catch (Exception e) {
			LOGGER.error("Fail to loading logback configuration from " + logbackFile + ", keepping default.", e);
		}
	}

	/**
	 * 得到user.dir
	 * 
	 * @return user.dir
	 */
	private String getUserDir() {
		// 首先从系统属性中读取
		String configPath = System.getProperty(CONFIG_ROOT);
		if (!StringUtils.isEmpty(configPath)) {
			return configPath;
		}
		return System.getProperty("user.dir");
	}
}