package com.axatrikx.tests.search;

import org.openqa.selenium.Keys;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.axatrikx.AxaTest;

public class NewTest extends AxaTest {

	public void beforeClass(){
		System.out.println("nt before class");
	}
	
	@Test
	public void test_Navigation() {
		System.out.println("Running test...");
		driver.get("http://axatrikx.com");
		assertEquals(driver.getTitle(), "Axatrikx - Automation Testing Tutorials, Tips and Solutions",
				"Incorrect page title");
	}

	@Test(groups = { "Search" })
	public void test_SearchBox() {
		System.out.println("Running test...");
		driver.get("http://axatrikx.com");
		driver.type("{{HomePage>TextField_SearchBox}}", "webdriver" + Keys.ENTER);
		assertEquals(driver.getTitle(), "Axatrikx - Automation Testing Tutorials, Tips and Solutions",
				"Incorrect page title");
	}

	@Test(groups = { "Search", "Cucumber" })
	public void test_SearchBoxCucumber() {
		System.out.println("Running test...");
		driver.get("http://axatrikx.com");
		driver.type("{{HomePage>TextField_SearchBox}}", "cucumber" + Keys.ENTER);
		assertEquals(driver.getTitle(), "You searched for cucumber - Axatrikx",
				"Incorrect page title");
	}

	@AfterClass
	public void tearDown() {
		driver.quit();
	}

}
