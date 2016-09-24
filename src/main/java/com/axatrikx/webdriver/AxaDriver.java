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
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Navigation;
import org.openqa.selenium.WebDriver.Options;
import org.openqa.selenium.WebDriver.TargetLocator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.security.Credentials;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.axatrikx.executor.ExecutionController;
import com.axatrikx.report.ExecutionStatus;
import com.axatrikx.report.IExecutionReporter;
import com.google.common.base.Predicate;

/**
 * This class is used by the script to perform WebDriver related operations.
 * 
 * @author amalbose
 */
public class AxaDriver {

	private WebDriver driver;
	private ElementHelper eleHelper;
	private IExecutionReporter reporter;

	/**
	 * Constructor for AxaDriver and obtains the WebDriver object from
	 * {@link WebDriverFactory} based on execution configuration.
	 */
	public AxaDriver() {
		System.out.println("In axadriver constructor");
		driver = WebDriverFactory.getInstance().getWebDriver();
		eleHelper = WebDriverFactory.getInstance().getElementHelper();
		reporter = ExecutionController.getController().getReporter();
	}

	/**
	 * Returns the webdriver instance
	 * 
	 * @return webdriver instance
	 */
	public WebDriver getDriver() {
		return this.driver;
	}

	/**
	 * Navigates to the url.
	 * 
	 * @param url
	 */
	public void get(String url) {
		driver.get(url);
		reporter.log("Navigation", "Navigated to url " + url, ExecutionStatus.INFO);
	}

	/**
	 * Get a string representing the current URL that the browser is looking at.
	 * 
	 * @return
	 */
	public String getCurrentUrl() {
		return driver.getCurrentUrl();
	}

	/**
	 * Get the source of the current page.
	 * 
	 * @return
	 */
	public String getPageSource() {
		return driver.getPageSource();
	}

	/**
	 * The title of the current page.
	 * 
	 * @return
	 */
	public String getTitle() {
		return driver.getTitle();
	}

	/**
	 * Returns the current window handle.
	 * 
	 * @return
	 */
	public String getWindowHandle() {
		return driver.getWindowHandle();
	}

	/**
	 * Return a set of window handles which can be used to iterate over all open
	 * windows.
	 * 
	 * @return
	 */
	public Set<String> getWindowHandles() {
		return driver.getWindowHandles();
	}

	/**
	 * Gets the Option interface
	 * 
	 * @return
	 */
	public Options manage() {
		return driver.manage();
	}

	/**
	 * An abstraction allowing the driver to access the browser's history and to
	 * navigate to a given URL.
	 * 
	 * @return
	 */
	public Navigation navigate() {
		return driver.navigate();
	}

	/**
	 * Quits this driver, closing every associated window.
	 * 
	 */
	public void quit() {
		driver.quit();
		driver = null;
		reporter.log("Operation", "Quitting WebDriver", ExecutionStatus.INFO);
	}

	/**
	 * Close the current window, quitting the browser if it's the last window
	 * currently open.
	 */
	public void close() {
		reporter.log("Operation", "Closing window : " + driver.getWindowHandle(), ExecutionStatus.INFO);
		driver.close();
	}

	/**
	 * Send future commands to a different frame or window.
	 * 
	 * @return
	 */
	public TargetLocator switchTo() {
		return driver.switchTo();
	}

	/**
	 * Switch the focus of future commands for this driver to the window with
	 * the given name/handle.
	 * 
	 * @param nameOrHandle
	 */
	public void switchToWindow(String nameOrHandle) {
		driver.switchTo().window(nameOrHandle);
		reporter.log("Operation", "Switched window to " + nameOrHandle, ExecutionStatus.INFO);
	}

	/**
	 * Switches to alert
	 */
	public void switchToAlert() {
		driver.switchTo().alert();
		reporter.log("Operation", "Switched to alert", ExecutionStatus.INFO);
	}

	/**
	 * Accepts the alert.
	 */
	public void acceptAlert() {
		driver.switchTo().alert().accept();
		reporter.log("Action", "Accepted alert", ExecutionStatus.INFO);
	}

	/**
	 * Cancels the alert.
	 */
	public void dismissAlert() {
		driver.switchTo().alert().dismiss();
		reporter.log("Action", "Dismissed alert", ExecutionStatus.INFO);
	}

	/**
	 * Types text on alert with text field.
	 * 
	 * @param keysToSend
	 */
	public void typeOnAlert(String keysToSend) {
		driver.switchTo().alert().sendKeys(keysToSend);
		reporter.log("Action", "Typed '" + keysToSend + "' on alert.", ExecutionStatus.INFO);
	}

	/**
	 * Authenticate an HTTP Basic Auth dialog
	 * 
	 * @param credentials
	 */
	public void authenticateDialog(Credentials credentials) {
		driver.switchTo().alert().authenticateUsing(credentials);
		reporter.log("Action", "Authenticated alert.", ExecutionStatus.INFO);
	}

	/**
	 * Selects either the first frame on the page, or the main document when a
	 * page contains iframes.
	 * 
	 */
	public void switchToDefaultContent() {
		driver.switchTo().defaultContent();
		reporter.log("Operation", "Switched to default content", ExecutionStatus.INFO);
	}

	/**
	 * Select a frame by its (zero-based) index
	 * 
	 * @param index
	 */
	public void switchToFrame(int index) {
		driver.switchTo().frame(index);
		reporter.log("Operation", "Switched to frame by index " + index, ExecutionStatus.INFO);
	}

	/**
	 * Select a frame by its name or ID
	 * 
	 * @param nameOrId
	 */
	public void switchToFrame(String nameOrId) {
		driver.switchTo().frame(nameOrId);
		reporter.log("Operation", "Switched to frame by name or Id " + nameOrId, ExecutionStatus.INFO);
	}

	/**
	 * Select a frame using its previously located WebElement.
	 * 
	 * @param frameElement
	 */
	public void switchToFrame(WebElement frameElement) {
		driver.switchTo().frame(frameElement);
		reporter.log("Operation", "Switched to frame", ExecutionStatus.INFO);
	}

	/**
	 * Returns the WebElement for the corresponding locator
	 * 
	 * @param locator
	 * @return
	 */
	public WebElement findElement(By locator) {
		return driver.findElement(locator);
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
	public WebElement findElement(String locator) {
		return eleHelper.findElement(locator);
	}

	/**
	 * Returns the list of WebElement for the corresponding locator
	 * 
	 * @param locator
	 * @return
	 */
	public List<WebElement> findElements(By locator) {
		return driver.findElements(locator);
	}

	/**
	 * Returns the list of {@link WebElement} based on the given locator
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
	public List<WebElement> findElements(String locator) {
		return eleHelper.findElements(locator);
	}

	/**
	 * Waits for alert for the given timeout.
	 * 
	 * @param maxTimeOut
	 *            max wait period
	 * @return true or false based on whether alert was present or not.
	 */
	public boolean waitForAlert(int maxTimeOut) {
		long curDuration = 0;
		long startTime = System.currentTimeMillis();
		boolean isAlertPresent = isAlertPresent();
		curDuration = (System.currentTimeMillis() - startTime);
		while (curDuration < maxTimeOut && !isAlertPresent) {
			wait(500);
			isAlertPresent = isAlertPresent();
			curDuration = (System.currentTimeMillis() - startTime);
		}
		return isAlertPresent;
	}

	public void wait(int time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
		}
	}

	/**
	 * Waits for page to complete loading
	 */
	public void waitForPageLoad() {
		WebDriverWait wait = new WebDriverWait(driver, 100);
		wait.until(new Predicate<WebDriver>() {
			@Override
			public boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
			}
		});
	}

	/**
	 * Checks if Alert is present or not
	 * 
	 * @return
	 */
	public boolean isAlertPresent() {
		boolean foundAlert = false;
		try {
			driver.switchTo().alert();
			foundAlert = true;
		} catch (Exception e) {
		}
		return foundAlert;
	}

	/**
	 * Checks whether the given string is present in the page or not
	 * 
	 * @param
	 * @return
	 */
	public boolean isTextPresent(String searchText) {
		return findElement(By.xpath("//*")).getText().contains(searchText);
	}

	/**
	 * Clears the text field element
	 * 
	 * @param locator
	 *            string locator in OR locator format
	 */
	public void clear(String locator) {
		eleHelper.findElement(locator).clear();
		reporter.log("Action", "Clear text on text field" + locator, ExecutionStatus.INFO);
	}

	/**
	 * clicks on the link
	 * 
	 * @param locator
	 *            string locator in OR locator format
	 */
	public void click(String locator) {
		eleHelper.findElement(locator).click();
		reporter.log("Action", "Clicked on element " + locator, ExecutionStatus.INFO);
	}

	/**
	 * Gets the specified attribute for the element
	 * 
	 * @param locator
	 *            string locator in OR locator format
	 * @param name
	 *            attribute name
	 * @return
	 */
	public String getAttribute(String locator, String name) {
		return eleHelper.findElement(locator).getAttribute(name);
	}

	/**
	 * Get the value attribute of the element.
	 * 
	 * @param locator
	 *            string locator in OR locator format
	 * @return
	 */
	public String getValue(String locator) {
		return eleHelper.findElement(locator).getAttribute("value");
	}

	/**
	 * Gets the given css value for the element
	 * 
	 * @param locator
	 *            string locator in OR locator format
	 * @param propertyName
	 *            css property
	 * @return
	 */
	public String getCssValue(String locator, String propertyName) {
		return eleHelper.findElement(locator).getCssValue(propertyName);
	}

	/**
	 * Gets the tagname of the element
	 * 
	 * @param locator
	 *            string locator in OR locator format
	 * @return
	 */
	public String getTagName(String locator) {
		return eleHelper.findElement(locator).getTagName();
	}

	/**
	 * Gets the text of the element
	 * 
	 * @param locator
	 *            string locator in OR locator format
	 * @return
	 */
	public String getText(String locator) {
		return eleHelper.findElement(locator).getText();
	}

	/**
	 * Checks if element is displayed or not
	 * 
	 * @param locator
	 *            string locator in OR locator format
	 * @return
	 */
	public boolean isDisplayed(String locator) {
		return eleHelper.findElement(locator).isDisplayed();
	}

	/**
	 * Checks if element is displayed or not
	 * 
	 * @param locator
	 *            string locator in OR locator format
	 * @return
	 */
	public boolean isEnabled(String locator) {
		return eleHelper.findElement(locator).isEnabled();
	}

	/**
	 * Checks if element is selected or not
	 * 
	 * @param locator
	 *            string locator in OR locator format
	 * @return
	 */
	public boolean isSelected(String locator) {
		return eleHelper.findElement(locator).isSelected();
	}

	/**
	 * Type on the element. If some text is already present on it, it will be
	 * cleared before typing. For typing over existing data, use sendKeys
	 * method.
	 * 
	 * @param locator
	 *            string locator in OR locator format
	 * @param keysToType
	 *            text to type
	 */
	public void type(String locator, String keysToType) {
		WebElement ele = eleHelper.findElement(locator);
		ele.clear();
		ele.sendKeys(keysToType);
		reporter.log("Action", "Typed '" + keysToType + "' on element " + locator, ExecutionStatus.INFO);
	}

	/**
	 * Types on the text field
	 * 
	 * @param locator
	 *            string locator in OR locator format
	 * @param keysToSend
	 *            text to type
	 */
	public void sendKeys(String locator, String keysToSend) {
		eleHelper.findElement(locator).sendKeys(keysToSend);
		reporter.log("Action", "Typed '" + keysToSend + "' on element " + locator, ExecutionStatus.INFO);
	}

	/**
	 * Submits the form.
	 * 
	 * @param locator
	 *            string locator in OR locator format
	 */
	public void submit(String locator) {
		eleHelper.findElement(locator).submit();
		reporter.log("Action", "Submitted form " + locator, ExecutionStatus.INFO);
	}

	/**
	 * Returns the {@link Select} object corresponding to the locator
	 * 
	 * @param locator
	 *            string locator in OR locator format
	 * @return
	 */
	public Select select(String locator) {
		return new Select(eleHelper.findElement(locator));
	}

	/**
	 * Selects the list box by visible text
	 * 
	 * @param locator
	 *            string locator in OR locator format
	 * @param visibleText
	 *            the visible text
	 */
	public void selectByVisibleText(String locator, String visibleText) {
		Select ele = new Select(eleHelper.findElement(locator));
		ele.selectByVisibleText(visibleText);
		reporter.log("Action", "Select '" + visibleText + "' on element " + locator, ExecutionStatus.INFO);
	}

	/**
	 * Selects the list box by index
	 * 
	 * @param locator
	 *            string locator in OR locator format
	 * @param index
	 *            index to select
	 */
	public void selectByIndex(String locator, int index) {
		Select ele = new Select(eleHelper.findElement(locator));
		ele.selectByIndex(index);
		reporter.log("Action", "Select " + index + "th element on " + locator, ExecutionStatus.INFO);
	}

	/**
	 * Selects the check box by value
	 * 
	 * @param locator
	 *            string locator in OR locator format
	 * @param value
	 *            value to select
	 */
	public void selectByValue(String locator, String value) {
		Select ele = new Select(eleHelper.findElement(locator));
		ele.selectByValue(value);
		reporter.log("Action", "Select '" + value + "' on element " + locator, ExecutionStatus.INFO);
	}

	/**
	 * Selects the checkbox. Does nothing if already selected
	 * 
	 * @param locator
	 *            string locator in OR locator format
	 */
	public void selectCheckBox(String locator) {
		WebElement ele = eleHelper.findElement(locator);
		boolean isSelected = ele.isSelected();
		if (!isSelected) {
			ele.click();
			reporter.log("Action", "Selected checkbox " + locator, ExecutionStatus.INFO);
		} else {
			reporter.log("Action", "Checkbox " + locator + " was already selected", ExecutionStatus.INFO);
		}
	}

	/**
	 * Unselect the checkbox. If already unselected, does nothing.
	 * 
	 * @param locator
	 *            string locator in OR locator format
	 */
	public void unSelectCheckBox(String locator) {
		WebElement ele = eleHelper.findElement(locator);
		boolean isSelected = ele.isSelected();
		if (isSelected) {
			ele.click();
			reporter.log("Action", "Unselected checkbox " + locator, ExecutionStatus.INFO);
		} else {
			reporter.log("Action", "Checkbox " + locator + " was already unselected", ExecutionStatus.INFO);
		}
	}

}
