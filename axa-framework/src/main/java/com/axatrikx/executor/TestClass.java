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

/**
 * @author amalbose
 *
 */
public class TestClass {
	private String fileName;
	private String packageName;
	private boolean shouldRun;

	/**
	 * @param fileName
	 * @param packageName
	 * @param shouldRun
	 */
	public TestClass(String fileName, String packageName, boolean shouldRun) {
		super();
		this.fileName = fileName;
		this.packageName = packageName;
		this.shouldRun = shouldRun;
	}

	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName
	 *            the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @return the packageName
	 */
	public String getPackageName() {
		return packageName;
	}

	/**
	 * @param packageName
	 *            the packageName to set
	 */
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	/**
	 * @return the shouldRun
	 */
	public boolean shouldRun() {
		return shouldRun;
	}

	/**
	 * @param shouldRun
	 *            the shouldRun to set
	 */
	public void setShouldRun(boolean shouldRun) {
		this.shouldRun = shouldRun;
	}
}
