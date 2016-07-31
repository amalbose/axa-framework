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

import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;

/**
 * Loads FirefoxDriver object
 * @author amalbose
 *
 */
public class FirefoxLoader implements IDriverLoader {

	private String firefoxBinaryPath;
	private String firefoxProfile;
	private String downloadDir;
	private boolean useProxy;
	private String proxyHost;
	private String proxyPort;

	/**
	 * @param firefoxBinaryPath
	 * @param firefoxProfile
	 * @param downloadDir
	 * @param useProxy
	 * @param proxyHost
	 * @param proxyPort
	 */
	public FirefoxLoader(String firefoxBinaryPath, String firefoxProfile, String downloadDir, boolean useProxy,
			String proxyHost, String proxyPort) {
		super();
		this.firefoxBinaryPath = firefoxBinaryPath;
		this.firefoxProfile = firefoxProfile;
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
		WebDriver driver;
		FirefoxProfile profile;
		if (!firefoxProfile.isEmpty()) {
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

		if (!firefoxBinaryPath.isEmpty()) {
			// set custom firefox binary
			FirefoxBinary binary = new FirefoxBinary(new File(firefoxBinaryPath));
			driver = new FirefoxDriver(binary, profile);
		} else {
			driver = new FirefoxDriver(profile);
		}
		return driver;
	}

}
