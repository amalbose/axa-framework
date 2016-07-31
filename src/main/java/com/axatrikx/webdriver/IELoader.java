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

import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.axatrikx.common.Utils;

/**
 * Loads Internet Explorer Driver
 * @author amalbose
 *
 */
public class IELoader implements IDriverLoader {

	private String downloadDir;
	private boolean useProxy;
	private String proxyHost;
	private String proxyPort;

	/**
	 * @param downloadDir
	 * @param useProxy
	 * @param proxyHost
	 * @param proxyPort
	 */
	public IELoader(String downloadDir, boolean useProxy, String proxyHost, String proxyPort) {
		super();
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

}
