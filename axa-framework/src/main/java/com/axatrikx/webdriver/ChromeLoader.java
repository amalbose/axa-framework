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

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.axatrikx.common.Utils;

/**
 * Loads the ChromeDriver object
 * 
 * @author amalbose
 *
 */
public class ChromeLoader implements IDriverLoader {

	private String chromeBinaryPath;
	private String chromeProfilePath;
	private String downloadDir;
	private boolean useProxy;
	private String proxyHost;
	private String proxyPort;

	/**
	 * @param chromeBinaryPath
	 * @param chromeProfilePath
	 * @param downloadDir
	 * @param useProxy
	 * @param proxyHost
	 * @param proxyPort
	 */
	public ChromeLoader(String chromeBinaryPath, String chromeProfilePath, String downloadDir, boolean useProxy,
			String proxyHost, String proxyPort) {
		super();
		this.chromeBinaryPath = chromeBinaryPath;
		this.chromeProfilePath = chromeProfilePath;
		this.downloadDir = downloadDir;
		this.useProxy = useProxy;
		this.proxyHost = proxyHost;
		this.proxyPort = proxyPort;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.axatrikx.webdriver.IDriverLoader#getDriver()
	 */
	@Override
	public WebDriver getDriver() {
		String resource = "chromedriver.exe";
		if (Utils.getOS().equals("Unix")) {
			resource = "chromedriver";
		}
		System.setProperty("webdriver.chrome.driver", Utils.getResource(resource).getAbsolutePath());
		DesiredCapabilities capabilities = DesiredCapabilities.chrome();
		ChromeOptions chromeOptions = new ChromeOptions();
		chromeOptions.addArguments("disable-popup-blocking");
		Map<String, Object> prefs = new HashMap<String, Object>();
		prefs.put("download.default_directory", downloadDir);
		prefs.put("profile.default_content_settings.popups", 0);

		// set chrome custom binary
		if (!chromeBinaryPath.isEmpty()) {
			prefs.put("binary", chromeBinaryPath);
		}

		chromeOptions.setExperimentalOption("prefs", prefs);

		if (useProxy) {
			String proxyURL = proxyHost + ":" + proxyPort;
			Proxy proxy = new Proxy();
			proxy.setHttpProxy(proxyURL).setFtpProxy(proxyURL).setSslProxy(proxyURL);
			capabilities.setCapability(CapabilityType.PROXY, proxy);
		}

		if (!chromeProfilePath.isEmpty()) {
			chromeOptions.addArguments("user-data-dir=" + chromeProfilePath);
		}

		capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
		return new ChromeDriver(capabilities);
	}

	
	public static void main(String[] a){
		new ChromeLoader("","","",false,"","").getDriver();
	}
}
