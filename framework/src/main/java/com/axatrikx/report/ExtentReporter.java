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
package com.axatrikx.report;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

/**
 * ExtentReport implementation of {@link IExecutionReporter}
 * 
 * @author amalbose
 *
 */
public class ExtentReporter implements IExecutionReporter {

	private ExtentReports extent;
	private ExtentTest test;

	public ExtentReporter(ExtentReports extent) {
		this.extent = extent;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.axatrikx.report.IExecutionReporter#startTest(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public void startTest(String testName, String description) {
		test = extent.startTest(testName, description);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.axatrikx.report.IExecutionReporter#endTest(java.lang.String)
	 */
	@Override
	public void endTest() {
		extent.endTest(test);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.axatrikx.report.IExecutionReporter#log(java.lang.String,
	 * com.axatrikx.report.ExecutionStatus)
	 */
	@Override
	public void log(String stepName, String details, ExecutionStatus status) {
		test.log(getLogStatus(status), stepName, details);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.axatrikx.report.IExecutionReporter#log(java.lang.String,
	 * com.axatrikx.report.ExecutionStatus, java.lang.Throwable)
	 */
	@Override
	public void log(String stepName, String details, ExecutionStatus status, Throwable err, String screenShotPath) {
		test.log(getLogStatus(status), stepName, err);
		if (screenShotPath != null)
			test.log(LogStatus.INFO, "ScreenShot", test.addScreenCapture(screenShotPath));
	}

	/**
	 * Returns LogStatus from {@link ExecutionStatus}
	 * 
	 * @param status
	 * @return
	 */
	private LogStatus getLogStatus(ExecutionStatus status) {
		LogStatus lstatus;
		switch (status) {
		case PASS:
			lstatus = LogStatus.PASS;
			break;
		case FAIL:
			lstatus = LogStatus.FAIL;
			break;
		case SKIP:
			lstatus = LogStatus.SKIP;
			break;
		case ERROR:
			lstatus = LogStatus.ERROR;
			break;
		default:
			lstatus = LogStatus.INFO;
		}
		return lstatus;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.axatrikx.report.IExecutionReporter#finish()
	 */
	@Override
	public void finish() {
		extent.flush();
		extent.close();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.axatrikx.report.IExecutionReporter#assignCategory(java.lang.String)
	 */
	@Override
	public void assignCategory(String group) {
		test.assignCategory(group);
	}

	/* (non-Javadoc)
	 * @see com.axatrikx.report.IExecutionReporter#log(java.lang.String, java.lang.String, com.axatrikx.report.ExecutionStatus, java.lang.Throwable)
	 */
	@Override
	public void log(String stepName, String details, ExecutionStatus status, Throwable err) {
		log(stepName, details, status, err, null);
	}

}
