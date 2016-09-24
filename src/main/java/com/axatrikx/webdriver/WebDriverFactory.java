/**
 * Licensed to Axatrikx under one or more contributor
 * license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright
 * ownership. Axatrikx licenses this file to you under
 * the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.axatrikx.webdriver;

import org.openqa.selenium.WebDriver;

import com.axatrikx.common.AxaConfig;
import com.axatrikx.common.Common;
import com.axatrikx.common.Utils;

/**
 * Factory for creating {@link WebDriver} and {@link ElementHelper} instances
 * 
 * @author amalbose
 *
 */
public class WebDriverFactory {

	private WebDriver driver;

	private static WebDriverFactory factory;

	/**
	 * Hides constructor
	 */
	private WebDriverFactory() {
	}

	/**
	 * Gets the factory instance.
	 * 
	 * @return
	 */
	public static WebDriverFactory getInstance() {
		if (factory == null)
			factory = new WebDriverFactory();
		return factory;
	}

	/**
	 * Returns the {@link WebDriver} object
	 * 
	 * @return
	 */
	public WebDriver getWebDriver() {
		if (driver == null || !isSessiveActive(driver)) {
			String browser = AxaConfig.getExecutionProperty(Common.EXEC_CONF_BROWSER);
			int defaultTimeOut = Integer.parseInt(AxaConfig.getExecutionProperty(Common.EXEC_CONF_DEFAULT_TIMEOUT));
			boolean maximizeWindow = Utils.getBoolean(AxaConfig.getExecutionProperty(Common.EXEC_CONF_MAXIMIZE_WINDOW));
			String chromeBinaryPath = AxaConfig.getConfiguration(Common.CONF_CHROME_BINARY);
			String chromeProfileFilePath = AxaConfig.getExecutionProperty(Common.EXEC_CONF_CHROME_PROFILEPATH);
			String firefoxBinary = AxaConfig.getConfiguration(Common.CONF_FIREFOX_BINARY);
			String firefoxProfile = AxaConfig.getExecutionProperty(Common.EXEC_CONF_CHROME_FIREFOX_PROFILE);
			String downloadDir = AxaConfig.getExecutionProperty(Common.EXEC_CONF_CHROME_DOWNLOAD_DIR);
			boolean useProxy = Utils.getBoolean(AxaConfig.getConfiguration(Common.CONF_USE_PROXY));
			String proxyHost = AxaConfig.getConfiguration(Common.CONF_PROXY_HOST);
			String proxyPort = AxaConfig.getConfiguration(Common.CONF_PROXY_PORT);
			boolean javaScriptEnabled = Utils
					.getBoolean(AxaConfig.getExecutionProperty(Common.EXEC_CONF_HTMLUNIT_JAVASCRIPT_ENABLED));

			driver = new DriverLoader(browser, defaultTimeOut, maximizeWindow, chromeBinaryPath, chromeProfileFilePath,
					firefoxBinary, firefoxProfile, downloadDir, useProxy, proxyHost, proxyPort, javaScriptEnabled)
							.getDriver();
		}
		return driver;
	}

	/**
	 * Checks if sesstion is active or not
	 * @param driver
	 * @return
	 */
	private boolean isSessiveActive(WebDriver driver) {
		return !driver.toString().contains("(null)");
	}

	/**
	 * Returns the {@link ElementHelper} object
	 * 
	 * @return
	 */
	public ElementHelper getElementHelper() {

		String prefix = AxaConfig.getConfiguration("OR_TOKEN_PREFIX");
		String suffix = AxaConfig.getConfiguration("OR_TOKEN_SUFFIX");
		String seperator = AxaConfig.getConfiguration("OR_TOKEN_SEPERATOR");
		String res_folder = AxaConfig.getConfiguration("REPOSITORY_FOLDER");
		String scr_res_folder = AxaConfig.getConfiguration("SCRIPT_REPO_FOLDER");
		String or_extn = AxaConfig.getConfiguration("OR_EXTENSION");

		LocatorTokenParser locatorParser = new LocatorTokenParser(prefix, suffix, seperator, res_folder, scr_res_folder,
				or_extn);

		return new ElementHelper(driver, locatorParser, new RepositoryManager());
	}

}
