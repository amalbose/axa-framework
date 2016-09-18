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
package com.axatrikx.executor;

import org.testng.ITestListener;

import com.axatrikx.common.AxaConfig;
import com.axatrikx.common.Common;
import com.axatrikx.common.Utils;
import com.axatrikx.report.Assertion;
import com.axatrikx.report.ExecutionListener;
import com.axatrikx.report.IExecutionReporter;
import com.axatrikx.report.ReporterFactory;

/**
 * Manages the execution
 * 
 * @author amalbose
 *
 */
public class ExecutionController {

	private String executionFolder;
	private static ExecutionController controller;
	private ExecutionListener listener;

	public static ExecutionController getController() {
		if (controller == null)
			controller = new ExecutionController();
		return controller;
	}

	private ExecutionController() {
		executionFolder = AxaConfig.getConfiguration("EXECUTION_BASEFOLDER") + Common.SEPARATOR
				+ Utils.getTimeStampFolderName(AxaConfig.getConfiguration("EXECUTION_FOLDER_DATE_FORMAT"));

		listener = ReporterFactory.getExecutionListener(executionFolder);
	}

	public String getExecutionFolder() {
		return this.executionFolder;
	}

	/**
	 * @return
	 */
	public ITestListener getListener() {
		return listener;
	}
	
	public IExecutionReporter getReporter(){
		return listener.getReporter();
	}

	/**
	 * @return
	 */
	public Assertion getAssertion() {
		return new Assertion(getReporter());
	}
}
