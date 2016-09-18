package com.axatrikx.executor;

import org.testng.TestNG;

import com.axatrikx.tests.search.NewTest;

public class RunTestNG {

	public static void performExecution() {
		ExecutionController controller = ExecutionController.getController();
		String outputdir = controller.getExecutionFolder();

		TestNG testng = new TestNG();
		testng.setOutputDirectory(outputdir);
		testng.setTestClasses(new Class[] { NewTest.class });
		testng.addListener(controller.getListener());
		testng.setUseDefaultListeners(true);
		testng.run();
	}

	public static void main(String[] args) {
		RunTestNG.performExecution();
	}

}
