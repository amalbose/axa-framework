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
package com.axatrikx.common;

import java.io.File;
import java.net.URL;

/**
 * Utils class for the commonly used functions.
 * @author amalbose
 *
 */
public class Utils {

	private static final String SUN_PACKAGE_NAME = "sun.reflect";

	public static String getCallingClass() {
		StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
		String callingClass = "", tmpClass;
		for (StackTraceElement element : stackTraceElements) {
			tmpClass = element.getClassName();
			if (tmpClass.startsWith(SUN_PACKAGE_NAME)) {
				break;
			} else {
				callingClass = tmpClass;
			}
		}
		return callingClass;
	}

	/**
	 * Joins the given list of paths.
	 * 
	 * @param paths
	 * @return
	 */
	public static String joinPaths(String... paths) {
		String finalPath = "";
		for (int i = 0; i < paths.length; i++) {
			finalPath += paths[i];
			if (i < paths.length - 1) {
				finalPath += Common.SEPARATOR;
			}
		}
		return finalPath;
	}

	public static boolean isResourcePresent(String filePath) {
		URL res = Utils.class.getClassLoader().getResource(filePath);
		if (res == null)
			return false;
		return new File(res.getFile()).exists();
	}

	/**
	 * Gets the file get resource folder.
	 * 
	 * @param filePath
	 * @return file from resource folder. null if not found.
	 */
	public static File getResource(String filePath) {
		URL res = Utils.class.getClassLoader().getResource(filePath);
		if (res == null)
			return null;
		return new File(res.getFile());
	}

	/**
	 * Turns string to boolean. true, y, yes and 1 will return true (case
	 * insensitive). Others will return false.
	 * 
	 * @param value
	 * @return
	 */
	public static boolean getBoolean(String value) {
		value = value.toLowerCase();
		if (value.equals("true") || value.equals("y") || value.equals("yes") || value.equals("1"))
			return true;
		return false;
	}
}