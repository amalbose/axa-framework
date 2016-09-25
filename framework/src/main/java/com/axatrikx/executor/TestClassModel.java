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

import java.util.List;

import javax.swing.table.AbstractTableModel;

/**
 * @author amalbose
 *
 */
public class TestClassModel extends AbstractTableModel {

	private static final long serialVersionUID = 9117449543018446396L;

	private List<TestClass> data;

	TestClassModel(List<TestClass> data) {
		this.data = data;
	}

	@Override
	public int getRowCount() {
		return data.size();
	}

	@Override
	public int getColumnCount() {
		return 3;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		TestClass tC = data.get(rowIndex);
		Object data = "";
		switch (columnIndex) {
		case 0:
			data = tC.getFileName();
			break;
		case 1:
			data = tC.getPackageName();
			break;
		case 2:
			data = tC.shouldRun();
			break;
		}
		return data;
	}

	@Override
	public String getColumnName(int column) {
		String colName = "";
		switch (column) {
		case 0:
			colName = "Name";
			break;
		case 1:
			colName = "Package";
			break;
		case 2:
			colName = "Execute?";
			break;
		}
		return colName;
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		Class<?> colClass;
		switch (columnIndex) {
		case 2:
			colClass = Boolean.class;
			break;
		default:
			colClass = String.class;
		}
		return colClass;
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		boolean isEditable;
		switch (columnIndex) {
		case 2:
			isEditable = true;
			break;
		default:
			isEditable = false;
		}
		return isEditable;
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		data.get(rowIndex).setShouldRun(Boolean.valueOf(String.valueOf(aValue)));
	}

	/**
	 * @return
	 */
	public List<TestClass> getData() {
		return this.data;
	}

}
