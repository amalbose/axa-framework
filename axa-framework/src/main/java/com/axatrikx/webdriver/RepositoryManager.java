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

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Properties;

import com.axatrikx.exceptions.RepositoryException;

/**
 * Manages the operations related to Object repository.
 * 
 * @author amalbose
 *
 */
public class RepositoryManager {

	private HashMap<String, Properties> repoProps;

	RepositoryManager() {
		repoProps = new HashMap<String, Properties>();
	}

	public String getValue(String repo, String key) {
		if (!repoProps.containsKey(repo)) {
			loadRepository(repo);
		}
		String value = repoProps.get(repo).getProperty(key);
		if (value == null) {
			throw new RepositoryException("Key " + key + " not found in repository : " + repo);
		}
		return value;
	}

	/**
	 * Load the repository corresponding to the given repo file.
	 * 
	 * @param repo
	 */
	private void loadRepository(String repo) {
		File repoFile = getRepo(repo);
		if (repoFile == null) {
			throw new RepositoryException("OR file '" + repo + "' not found in resources folders.");
		}
		Properties prop = new Properties();
		try {
			prop.load(new FileReader(repoFile));
		} catch (IOException e) {
			e.printStackTrace();
		}
		repoProps.put(repo, prop);
	}

	/**
	 * Get the repository file.
	 * 
	 * @param filePath
	 * @return
	 */
	private File getRepo(String filePath) {
		URL res = getClass().getClassLoader().getResource(filePath);
		if (res == null)
			return null;
		File repoFile = new File(res.getFile());
		return repoFile.exists() ? repoFile : null;
	}

}
