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
package com.axatrikx;

import org.testng.annotations.BeforeClass;

import com.axatrikx.executor.ExecutionController;
import com.axatrikx.report.Assertion;
import com.axatrikx.webdriver.AxaDriver;

/**
 * Base class for all test classes
 * @author amalbose
 *
 */
public class AxaTest {
	public  AxaDriver driver;
	private Assertion assertion;

	public AxaTest() {
	}
	
	@BeforeClass
	public void initializeDriver() {
		driver = new AxaDriver();
		assertion = ExecutionController.getController().getAssertion();
		beforeClass();
	}
	
	public void beforeClass(){
		// can be overridden by individual tests
	}
	
	public void assertTrue(boolean condition, String message){
		assertion.assertTrue(condition, message);
	}
	
	public void assertFalse(boolean condition, String message){
		assertion.assertFalse(condition, message);
	}
	
	public void assertEquals(Object actual, Object expected, String message){
		assertion.assertEquals(actual, expected, message);
	}
	
	public void assertNotEquals(Object actual, Object expected, String message){
		assertion.assertNotEquals(actual, expected, message);
	}
	
	public void assertNull(Object object, String message){
		assertion.assertNull(object, message);
	}

	public void assertNotNull(Object object, String message){
		assertion.assertNotNull(object, message);
	}
}
