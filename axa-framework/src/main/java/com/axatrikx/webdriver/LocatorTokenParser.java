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
package com.axatrikx.webdriver;

import java.util.Objects;

import com.axatrikx.common.Common;
import com.axatrikx.common.Utils;
import com.axatrikx.exceptions.RepositoryException;

/**
 * Parses the OR token into OR file and locator token values.
 * 
 * @author amalbose
 *
 */
public class LocatorTokenParser {

	private String RES_FOLDER;
	private String SCR_RES_FOLDER;
	private String OR_EXTN;

	private String PREFIX;
	private String SUFFIX;
	private String SEPERATOR;

	/**
	 * {@link LocatorTokenParser} constructor providing prefix and suffix
	 * values.
	 * 
	 * @param prefix
	 * @param suffix
	 * @param seperator
	 */
	LocatorTokenParser(String prefix, String suffix, String seperator, String RES_FOLDER, String SCR_RES_FOLDER,
			String OR_EXTN) {
		this.PREFIX = Objects.requireNonNull(prefix);
		this.SUFFIX = Objects.requireNonNull(suffix);
		this.SEPERATOR = Objects.requireNonNull(seperator);
		this.RES_FOLDER = Objects.requireNonNull(RES_FOLDER);
		this.OR_EXTN = Objects.requireNonNull(OR_EXTN);
		this.SCR_RES_FOLDER = Objects.requireNonNull(SCR_RES_FOLDER);
	}

	/**
	 * Checks if the given token is a valid OR token. <br/>
	 * ie in this format > [PREFIX]ORNAME[SEPERATOR]ORVALUE[SUFFIX] <br/>
	 * eg. {HomePage>Image_Logo}
	 * 
	 * @param token
	 * @return true if valid, false otherwise
	 */
	public boolean isValidORToken(String token) {
		if (!token.startsWith(PREFIX) || !token.endsWith(SUFFIX)) {
			return false;
		}
		return true;
	}

	/**
	 * Parses the token and returns the OR file and locator key in an array. 0th
	 * element is OR file and 1st element is locator key.
	 * 
	 * @param token
	 * @return
	 */
	public String[] parseToken(String token) {
		String[] parsedArr = new String[2];

		if (!isValidORToken(token)) {
			throw new RepositoryException("Invalid token format : " + token);
		}

		if (!token.contains(SEPERATOR)) {
			// Its script OR
			parsedArr[0] = Utils.joinPaths(SCR_RES_FOLDER, Utils.getCallingClass() + Common.DOT + OR_EXTN);
			parsedArr[1] = removePrefixSuffix(PREFIX, SUFFIX, token);
		} else {
			token = removePrefixSuffix(PREFIX, SUFFIX, token);
			parsedArr = token.split(SEPERATOR);
			parsedArr[0] = Utils.joinPaths(RES_FOLDER, parsedArr[0] + Common.DOT + OR_EXTN);
		}
		return parsedArr;
	}

	/**
	 * Remove the prefix and suffix from token.
	 * 
	 * @param prefix
	 * @param suffix
	 * @param token
	 * @return
	 */
	private String removePrefixSuffix(String prefix, String suffix, String token) {
		return token.substring(prefix.length(), token.length() - suffix.length());
	}

}
