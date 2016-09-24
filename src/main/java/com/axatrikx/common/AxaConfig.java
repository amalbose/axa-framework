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

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;

/**
 * Manages the configuration and execution property files.
 * 
 * @author amalbose
 *
 */
public class AxaConfig {

	private static Configuration config;
	private static Configuration execProp;
	private static FileBasedConfigurationBuilder<FileBasedConfiguration> builder;
	// Loads conf and execution property files.
	static {
		Configurations configs = new Configurations();
		try {
			config = configs.properties(new File(Common.CONF_PROP_FILE));
		} catch (ConfigurationException cex) {
			System.out.println("Error loading Config file " + Common.CONF_PROP_FILE);
			cex.printStackTrace();
		}
		try {
			// execProp = configs.properties(new
			// File(Common.EXECUTION_PROP_FILE));
			Parameters params = new Parameters();
			builder = new FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
					.configure(params.properties().setFileName(Common.EXECUTION_PROP_FILE));
			execProp = builder.getConfiguration();

		} catch (ConfigurationException cex) {
			System.out.println("Error loading Execution property file " + Common.EXECUTION_PROP_FILE);
			cex.printStackTrace();
		}
	}

	/**
	 * Returns the configuration value from conf file
	 * 
	 * @param key
	 * @return
	 */
	public static String getConfiguration(String key) {
		return config.getString(key);
	}

	/**
	 * Returns the property value from execution property file
	 * 
	 * @param key
	 * @return
	 */
	public static String getExecutionProperty(String key) {
		return execProp.getString(key);
	}

	/**
	 * Set execution configuration
	 * 
	 * @param key
	 * @param value
	 */
	public static void setExecutionConf(String key, String value) {
		execProp.setProperty(key, value);
	}

	/**
	 * Save execution configuration
	 */
	public static void saveExecutionConfChanges() {
		try {
			builder.save();
		} catch (ConfigurationException e) {
			e.printStackTrace();
		}
	}

}
