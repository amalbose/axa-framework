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

import java.io.File;

import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

/**
 * @author amalbose
 *
 */
public class ExecutionListener extends TestListenerAdapter {

	private IExecutionReporter reporter;

	public ExecutionListener(IExecutionReporter reporter) {
		this.reporter = reporter;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.testng.TestListenerAdapter#onStart(org.testng.ITestContext)
	 */
	@Override
	public void onStart(ITestContext testContext) {
		super.onStart(testContext);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.testng.TestListenerAdapter#onFinish(org.testng.ITestContext)
	 */
	@Override
	public void onFinish(ITestContext testContext) {
		super.onFinish(testContext);
		reporter.finish();
		System.out.println("Completed Test");
		System.out.println("Result folder " + new File(testContext.getOutputDirectory()).getParent());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.testng.TestListenerAdapter#onTestStart(org.testng.ITestResult)
	 */
	@Override
	public void onTestStart(ITestResult result) {
		reporter.startTest(result.getName(), "");
		for (String group : result.getMethod().getGroups())
			reporter.assignCategory(group);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.testng.TestListenerAdapter#onTestSuccess(org.testng.ITestResult)
	 */
	@Override
	public void onTestSuccess(ITestResult tr) {
		super.onTestSuccess(tr);
		reporter.log("Result", "Test Pass", ExecutionStatus.PASS);
		reporter.endTest();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.testng.TestListenerAdapter#onTestFailure(org.testng.ITestResult)
	 */
	@Override
	public void onTestFailure(ITestResult tr) {
		super.onTestFailure(tr);
		reporter.log("Result", "Test Failed", ExecutionStatus.FAIL, tr.getThrowable());
		reporter.endTest();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.testng.TestListenerAdapter#onTestSkipped(org.testng.ITestResult)
	 */
	@Override
	public void onTestSkipped(ITestResult tr) {
		// TODO Auto-generated method stub
		super.onTestSkipped(tr);
		reporter.log("Result", "Test Skipped", ExecutionStatus.SKIP);
		reporter.endTest();
	}

	/**
	 * Returns the reporter.
	 * 
	 * @return
	 */
	public IExecutionReporter getReporter() {
		return this.reporter;
	}

}
