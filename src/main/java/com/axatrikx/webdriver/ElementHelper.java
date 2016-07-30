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

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.axatrikx.common.Utils;
import com.axatrikx.exceptions.RepositoryException;

/**
 * Manages the conversion of OR locator string to {@link WebElement} objects
 * 
 * @author amalbose
 *
 */
public class ElementHelper {

	private LocatorTokenParser tokenParser;
	private RepositoryManager repoMan;
	private WebDriver driver;

	/**
	 * Initializes the Element helper
	 * 
	 * @param driver
	 * @param tokenParser
	 * @param repoMan
	 */
	ElementHelper(WebDriver driver, LocatorTokenParser tokenParser, RepositoryManager repoMan) {
		this.driver = driver;
		this.tokenParser = tokenParser;
		this.repoMan = repoMan;
	}

	/**
	 * Returns the {@link WebElement} based on the given locator
	 * 
	 * @param locator
	 *            Locator can be either OR or locator <br/>
	 *            1. repository OR in
	 *            {{&lt;OR_FileName_Without_Extension&gt;.&lt;Key&gt;}} format
	 *            <br/>
	 *            eg. {{HomePage.Image_Logo}}<br/>
	 *            2. script OR in {{&lt;Key&gt;}} format <br/>
	 *            eg. {{Input_SearchBox}} which will be taken from
	 *            com.axatrikx.test.SearchPageTest.properties file where
	 *            com.axatrikx.test.SearchPageTest is the test class <br/>
	 *            3. locator in XPATH=//xpath/to/element format <br/>
	 *            4. locator in //xpath/value or css.path or idValue <br/>
	 * @return
	 */
	protected WebElement findElement(String locator) {
		return driver.findElement(getByObject(locator));
	}

	/**
	 * Returns the List of {@link WebElement}
	 * 
	 * @param locator
	 *            Locator can be either OR or locator <br/>
	 *            1. repository OR in
	 *            {{&lt;OR_FileName_Without_Extension&gt;.&lt;Key&gt;}} format
	 *            <br/>
	 *            eg. {{HomePage.Image_Logo}}<br/>
	 *            2. script OR in {{&lt;Key&gt;}} format <br/>
	 *            eg. {{Input_SearchBox}} which will be taken from
	 *            com.axatrikx.test.SearchPageTest.properties file where
	 *            com.axatrikx.test.SearchPageTest is the test class <br/>
	 *            3. locator in XPATH=//xpath/to/element format <br/>
	 * @return
	 */
	protected List<WebElement> findElements(String locator) {
		return driver.findElements(getByObject(locator));
	}

	/**
	 * Returns By for the given locator
	 * 
	 * @param locator
	 * @return
	 */
	private By getByObject(String locator) {
		String locValue;
		if (tokenParser.isValidORToken(locator)) {
			String[] tokenVals = tokenParser.parseToken(locator);
			String orFile = tokenVals[0];

			if (!Utils.isResourcePresent(orFile)) {
				throw new RepositoryException(
						"Invalid locator " + locator + ".\r\nOR file '" + orFile + "' not found in resources folders.");
			}

			locValue = repoMan.getValue(orFile, tokenVals[1]);

			if (locValue.isEmpty()) {
				throw new RepositoryException(
						"Invalid locator " + locator + ".\r\nLocator value '" + tokenVals[1] + "' is empty");
			}
		} else {
			// Use locator directory. It should be in type=path format (Eg
			// xpath=//xpath/value)
			locValue = locator;
		}
		return parseLocator(locValue);
	}

	private By parseLocator(String locator) {
		String locValue;
		By byElement = null;
		if (locator.toLowerCase().startsWith("id=")) {
			locValue = locator.substring(3);
			byElement = By.id(locValue);
		} else if (locator.toLowerCase().startsWith("name=")) {
			locValue = locator.substring(5);
			byElement = By.name(locValue);
		} else if (locator.toLowerCase().startsWith("class=")) {
			locValue = locator.substring(6);
			byElement = By.className(locValue);
		} else if (locator.toLowerCase().startsWith("css=")) {
			locValue = locator.substring(4);
			byElement = By.cssSelector(locValue);
		} else if (locator.toLowerCase().startsWith("xpath=")) {
			locValue = locator.substring(6);
			byElement = By.xpath(locValue);
		} else if (locator.toLowerCase().startsWith("classname=")) {
			locValue = locator.substring(10);
			byElement = By.className(locValue);
		} else if (locator.toLowerCase().startsWith("tagname=")) {
			locValue = locator.substring(8);
			byElement = By.tagName(locValue);
		} else if (locator.toLowerCase().startsWith("link=")) {
			locValue = locator.substring(5);
			byElement = By.linkText(locValue);
		} else if (locator.toLowerCase().startsWith("partiallinktext=")) {
			locValue = locator.substring(16);
			byElement = By.partialLinkText(locValue);
		} else {
			// set default to id
			byElement = By.id(locator);
		}
		return byElement;
	}

}
