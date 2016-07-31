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
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

/**
 * Loads HTMLUnit driver
 * @author amalbose
 *
 */
public class HTMLUnitLoader implements IDriverLoader {

	private boolean javaScriptEnabled;

	/**
	 * @param javaScriptEnabled
	 */
	public HTMLUnitLoader(boolean javaScriptEnabled) {
		super();
		this.javaScriptEnabled = javaScriptEnabled;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.axatrikx.webdriver.IDriverLoader#getDriver()
	 */
	@Override
	public WebDriver getDriver() {
		return new HtmlUnitDriver(javaScriptEnabled);
	}

}
