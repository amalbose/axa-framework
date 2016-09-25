package com.axatrikx.tests.ui;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.axatrikx.AxaTest;

public class HomePageUITests extends AxaTest {

	public void beforeClass(){
		System.out.println("hp before class");
	}
	
	@Test
	public void test_NavigationTest() {
		System.out.println("HOME PAGE Running test...");
		driver.get("http://axatrikx.com");
		assertEquals(driver.getTitle(), "Axatrikx - Automation Testing Tutorials, Tips and Solutions",
				"Incorrect page title");
	}

	@AfterClass
	public void tearDown() {
		driver.quit();
	}
}