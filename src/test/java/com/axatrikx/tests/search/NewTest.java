package com.axatrikx.tests.search;

import org.openqa.selenium.Keys;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.axatrikx.webdriver.AxaDriver;

public class NewTest {
	AxaDriver driver;

	@BeforeClass
	public void beforeClass() {
		driver = new AxaDriver();
	}

	@Test
	public void f() {
		System.out.println("Running test...");
		driver.get("http://axatrikx.com");
		driver.type("{{HomePage>TextField_SearchBox}}", "webdriver" + Keys.ENTER);
	}

//	@Test
//	public void f2() {
//		System.out.println("Running test...");
//		driver.findElement("{{HomePage>TextField_SearchBox}}");
//	}
//
//	@Test
//	public void f3() {
//		System.out.println("Running test...");
//		driver.findElement("{{Invalid_Value}}");
//	}
}
