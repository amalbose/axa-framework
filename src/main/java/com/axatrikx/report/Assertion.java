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

import org.testng.asserts.IAssert;

/**
 * @author amalbose
 *
 */
public class Assertion extends org.testng.asserts.Assertion {

	private IExecutionReporter reporter;

	public Assertion(IExecutionReporter reporter) {
		this.reporter = reporter;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.testng.asserts.Assertion#onAssertFailure(org.testng.asserts.IAssert,
	 * java.lang.AssertionError)
	 */
	@Override
	public void onAssertFailure(IAssert<?> assertCommand, AssertionError ex) {
		super.onAssertFailure(assertCommand, ex);
		reporter.log("Assertion Failed", assertCommand.getMessage() + " Expected [" + assertCommand.getExpected()
				+ "] : Actual [" + assertCommand.getActual() + "]", ExecutionStatus.FAIL);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.testng.asserts.Assertion#onAssertSuccess(org.testng.asserts.IAssert)
	 */
	@Override
	public void onAssertSuccess(IAssert<?> assertCommand) {
		super.onAssertSuccess(assertCommand);
		reporter.log("Assertion Passed", assertCommand.getMessage(), ExecutionStatus.PASS);
	}

}
