package com.axatrikx.tests;

import org.testng.TestListenerAdapter;
import org.testng.TestNG;

import com.axatrikx.report.ExtentReporterNG;
import com.axatrikx.tests.search.NewTest;
import com.relevantcodes.extentreports.ExtentReports;

public class RunTestNG {

	public static void main(String[] args) {
		TestListenerAdapter tla = new TestListenerAdapter();
		ExtentReporterNG report = new ExtentReporterNG();
		TestNG testng = new TestNG();
		testng.setTestClasses(new Class[] { NewTest.class });
		testng.addListener(report);
		testng.setUseDefaultListeners(true);
		testng.run();
	}

}
