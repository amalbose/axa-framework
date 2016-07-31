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

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;

import com.axatrikx.exceptions.ConfigurationException;

/**
 * Loads the webdriver instance.
 * 
 * @author amalbose
 *
 */
public class DriverLoader {

	private String browser;
	private int defaultTimeOut;
	private boolean maximizeWindow;
	private String chromeBinaryPath;
	private String chromeProfilePath;
	private String firefoxBinaryPath;
	private String firefoxProfile;
	private String downloadDir;
	private boolean useProxy;
	private String proxyHost;
	private String proxyPort;
	private boolean javaScriptEnabled;

	/**
	 * Initializes the browser with the given data.
	 * 
	 * @param browser
	 * @param defaultTimeOut
	 *            timeout in seconds
	 * @param maximizeWindow
	 * @param useChromeFilePath
	 * @param chromeFilePath
	 * @param firefoxBinaryPath
	 * @param firefoxProfile
	 * @param downloadDir
	 */
	public DriverLoader(String browser, int defaultTimeOut, boolean maximizeWindow, String chromeBinaryPath,
			String chromeProfilePath, String firefoxBinaryPath, String firefoxProfile, String downloadDir,
			boolean useProxy, String proxyHost, String proxyPort, boolean javaScriptEnabled) {
		super();
		this.browser = browser;
		this.defaultTimeOut = defaultTimeOut;
		this.maximizeWindow = maximizeWindow;
		this.chromeBinaryPath = chromeBinaryPath;
		this.chromeProfilePath = chromeProfilePath;
		this.firefoxBinaryPath = firefoxBinaryPath;
		this.firefoxProfile = firefoxProfile;
		this.downloadDir = downloadDir;
		this.useProxy = useProxy;
		this.proxyHost = proxyHost;
		this.proxyPort = proxyPort;
		this.javaScriptEnabled = javaScriptEnabled;
		validateData();
	}

	/**
	 * 
	 */
	private void validateData() {
		new File(this.downloadDir).mkdirs();
	}

	public WebDriver getDriver() {
		WebDriver driver = null;
		if (browser.toLowerCase().startsWith("f")) {
			driver = getFirefoxDriver();
		} else if (browser.toLowerCase().startsWith("c")) {
			driver = getChromeDriver();
		} else if (browser.toLowerCase().startsWith("i")) {
			driver = getIEDriver();
		} else if (browser.toLowerCase().startsWith("h")) {
			driver = getHTMLUnitDriver();
		} else {
			throw new ConfigurationException("Invalid browser specified : " + this.browser);
		}

		// Set default timeout
		driver.manage().timeouts().implicitlyWait(defaultTimeOut, TimeUnit.SECONDS);

		// set maximize window
		if (maximizeWindow)
			driver.manage().window().maximize();

		return driver;
	}

	/**
	 * @return
	 */
	private WebDriver getFirefoxDriver() {
		return new FirefoxLoader(firefoxBinaryPath, firefoxProfile, downloadDir, useProxy, proxyHost, proxyPort).getDriver();
	}

	private WebDriver getChromeDriver() {
		return new ChromeLoader(chromeBinaryPath, chromeProfilePath, downloadDir, useProxy, proxyHost, proxyPort).getDriver();
	}

	private WebDriver getIEDriver() {
		return new IELoader(downloadDir, useProxy, proxyHost, proxyPort).getDriver();
	}

	private WebDriver getHTMLUnitDriver() {
		return new HTMLUnitLoader(javaScriptEnabled).getDriver();
	}

}
