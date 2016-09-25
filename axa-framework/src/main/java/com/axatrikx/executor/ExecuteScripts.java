package com.axatrikx.executor;

import java.util.List;

import org.testng.TestNG;

import com.axatrikx.common.Common;
import com.axatrikx.common.Utils;

public class ExecuteScripts {

	public static void performExecution() {
		ExecutionController controller = ExecutionController.getController();
		String outputdir = controller.getExecutionFolder();
		TestNG testng = new TestNG();
		testng.setOutputDirectory(outputdir);
		testng.setTestClasses(getTestClasses());
		testng.addListener(controller.getListener());
		testng.setUseDefaultListeners(true);
		testng.run();
	}

	/**
	 * Get all the test classes to execute
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Class[] getTestClasses() {
		List<String> classList = Utils.getFileAsList(Common.SCRIPT_PROP_FILE);
		Class[] cl = new Class[classList.size()];
		int index = 0;
		for (String cls : classList) {
			try {
				cl[index++] = Class.forName(cls);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		return cl;
	}

}
