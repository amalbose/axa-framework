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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingWorker;

import com.axatrikx.common.Common;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseException;
import com.github.javaparser.ast.CompilationUnit;

/**
 * @author amalbose
 *
 */
public class TestClassParser extends SwingWorker<Boolean, String> {

	private String folder;
	private List<TestClass> data;
	private ExecutorFrame frame;
	
	TestClassParser(ExecutorFrame frame, String folder) {
		this.frame = frame;
		this.folder = folder;
		this.data = new ArrayList<TestClass>();
	}

	private List<TestClass> getClassData() {
		List<File> fileList = getAllFiles(new File(folder));
		CompilationUnit cu;
		String fileName;
		for (File file : fileList) {
			try {
				fileName = file.getName();
				cu = JavaParser.parse(file);
				if (cu == null || cu.getPackage() == null || cu.getPackage().getPackageName() == null)
					continue;
				data.add(new TestClass(fileName,
						cu.getPackage().getPackageName() + "." + fileName.replaceAll(Common.CLASS_SUFFIX, ""),
						shouldRun(fileName)));
			} catch (ParseException | IOException e) {
				e.printStackTrace();
			}
		}
		return data;
	}

	private boolean shouldRun(String fileName) {
		// can get the previous data
		return true;
	}

	public List<File> getAllFiles(File dir) {
		List<File> fileTree = new ArrayList<File>();
		if (dir == null || dir.listFiles() == null) {
			return fileTree;
		}
		File[] list = dir.listFiles();
		for (File entry : list) {
			if (entry.isFile() && entry.getName().endsWith(Common.CLASS_SUFFIX))
				fileTree.add(entry);
			else
				fileTree.addAll(getAllFiles(entry));
		}
		return fileTree;
	}

	/* (non-Javadoc)
	 * @see javax.swing.SwingWorker#doInBackground()
	 */
	@Override
	protected Boolean doInBackground() throws Exception {
		getClassData();
		return true;
	}

	/* (non-Javadoc)
	 * @see javax.swing.SwingWorker#done()
	 */
	@Override
	protected void done() {
		super.done();
		frame.doneParsing(data);
	}

}
