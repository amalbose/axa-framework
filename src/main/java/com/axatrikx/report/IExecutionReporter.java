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

/**
 * The Execution Logger interface to handle logging of testcase steps and
 * results.
 * 
 * @author amalbose
 *
 */
public interface IExecutionReporter {

	/**
	 * Logs the starting of test
	 * 
	 * @param testName
	 * @param description
	 */
	public void startTest(String testName, String description);

	/**
	 * Logs the ending of test
	 * 
	 */
	public void endTest();
	
	/**
	 * End tests
	 */
	public void finish();

	/**
	 * Add log entry for regular step
	 * 
	 * @param stepName
	 * @param details
	 * @param status
	 */
	public void log(String stepName, String details, ExecutionStatus status);

	/**
	 * Add log entry in case of exceptions with screenshot
	 * 
	 * @param stepName
	 * @param details
	 * @param status
	 * @param err
	 * @param screenShotPath
	 */
	public void log(String stepName, String details, ExecutionStatus status, Throwable err, String screenShotPath);

	/**
	 * Add log entry in case of exceptions
	 * @param stepName
	 * @param details
	 * @param status
	 * @param err
	 */
	public void log(String stepName, String details, ExecutionStatus status, Throwable err);
	
	
	/**
	 * Assign test category
	 * @param group
	 */
	public void assignCategory(String group);
	
}
