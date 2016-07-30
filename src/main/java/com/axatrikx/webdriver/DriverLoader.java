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
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.axatrikx.common.Utils;
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
	private boolean useChromeProfilePath;
	private String chromeProfilePath;
	private boolean useFirefoxProfile;
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
	 * @param useFirefoxProfile
	 * @param firefoxProfile
	 * @param downloadDir
	 */
	public DriverLoader(String browser, int defaultTimeOut, boolean maximizeWindow, boolean useChromeProfilePath,
			String chromeProfilePath, boolean useFirefoxProfile, String firefoxProfile, String downloadDir,
			boolean useProxy, String proxyHost, String proxyPort, boolean javaScriptEnabled) {
		super();
		this.browser = browser;
		this.defaultTimeOut = defaultTimeOut;
		this.maximizeWindow = maximizeWindow;
		this.useChromeProfilePath = useChromeProfilePath;
		this.chromeProfilePath = chromeProfilePath;
		this.useFirefoxProfile = useFirefoxProfile;
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

		FirefoxProfile profile;
		if (useFirefoxProfile) {
			ProfilesIni allProfiles = new ProfilesIni();
			try {
				profile = allProfiles.getProfile(firefoxProfile);
			} catch (Exception e) {
				profile = new FirefoxProfile();
			}
		} else {
			profile = new FirefoxProfile();
		}
		profile.setPreference("browser.download.dir", downloadDir);
		profile.setPreference("browser.download.folderList", 2);
		if (useProxy) {
			profile.setPreference("network.proxy.type", 1);
			profile.setPreference("network.proxy.http", proxyHost);
			profile.setPreference("network.proxy.http_port", proxyPort);
			profile.setPreference("network.proxy.ssl", proxyHost);
			profile.setPreference("network.proxy.ssl_port", proxyPort);
			profile.setPreference("network.proxy.ftp", proxyHost);
			profile.setPreference("network.proxy.ftp_port", proxyPort);
		} else {
			profile.setPreference("network.proxy.type", Proxy.ProxyType.AUTODETECT.ordinal());
		}

		return new FirefoxDriver(profile);
	}

	private WebDriver getChromeDriver() {
		System.setProperty("webdriver.chrome.driver", Utils.getResource("chromedriver.exe").getAbsolutePath());
		DesiredCapabilities capabilities = DesiredCapabilities.chrome();
		ChromeOptions chromeOptions = new ChromeOptions();
		chromeOptions.addArguments("disable-popup-blocking");
		Map<String, Object> prefs = new HashMap<String, Object>();
		prefs.put("download.default_directory", downloadDir);
		prefs.put("profile.default_content_settings.popups", 0);
		chromeOptions.setExperimentalOption("prefs", prefs);

		if (useProxy) {
			String proxyURL = proxyHost + ":" + proxyPort;
			Proxy proxy = new Proxy();
			proxy.setHttpProxy(proxyURL).setFtpProxy(proxyURL).setSslProxy(proxyURL);
			capabilities.setCapability(CapabilityType.PROXY, proxy);
		}

		if (useChromeProfilePath) {
			chromeOptions.addArguments("user-data-dir=" + chromeProfilePath);
		}

		capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
		return new ChromeDriver(capabilities);
	}

	private WebDriver getIEDriver() {
		System.setProperty("webdriver.ie.driver", Utils.getResource("IEDriverServer.exe").getAbsolutePath());
		System.setProperty("java.io.tmpdir", downloadDir);
		DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
		capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
		if (useProxy) {
			String proxyURL = proxyHost + ":" + proxyPort;
			Proxy proxy = new Proxy();
			proxy.setHttpProxy(proxyURL).setFtpProxy(proxyURL).setSslProxy(proxyURL);
			capabilities.setCapability(CapabilityType.PROXY, proxy);
		}
		return new InternetExplorerDriver(capabilities);
	}

	private WebDriver getHTMLUnitDriver() {
		return new HtmlUnitDriver(javaScriptEnabled);
	}

}
