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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import com.axatrikx.common.AxaConfig;
import com.axatrikx.common.Common;
import com.axatrikx.common.Utils;

import net.miginfocom.swing.MigLayout;

/**
 * @author amalbose
 *
 */
public class ExecutorFrame extends JFrame {

	private static final long serialVersionUID = -3042322128439815524L;
	
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField txtDownload;
	private JTextField textField_2;
	private JTable table;

	private String testFolder;
	private String profilePath;
	private JLabel lblLoading;
	private JComboBox<String> comboBox;
	private JCheckBox chckbxMaximizeWindow;
	private JCheckBox chckbxJavascriptEnabledhtmlunit;
	private TestClassModel model;

	/**
	 * Create the frame.
	 */
	public ExecutorFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 728, 466);
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[grow]", "[][][grow]"));
		JPanel panel = new JPanel();
		contentPane.add(panel, "cell 0 0,grow");

		JLabel lblExecutor = new JLabel("Executor");
		panel.add(lblExecutor);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Configuration", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		contentPane.add(panel_1, "cell 0 1,grow");
		panel_1.setLayout(new MigLayout("", "[][][grow][grow][grow][][][]", "[][][][]"));

		JLabel lblBrowser = new JLabel("Browser");
		panel_1.add(lblBrowser, "cell 0 0");

		comboBox = new JComboBox<String>();
		comboBox.setModel(new DefaultComboBoxModel<String>(new String[] { "Chrome", "Firefox", "IExplore","HTMLUnit" }));
		panel_1.add(comboBox, "cell 2 0,alignx left");

		JLabel lblNewLabel = new JLabel("Custom Profile Path");
		panel_1.add(lblNewLabel, "cell 4 0");

		textField_1 = new JTextField();
		panel_1.add(textField_1, "flowx,cell 6 0,alignx left");
		textField_1.setColumns(30);

		JButton btnBrowse = new JButton("Browse");
		btnBrowse.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				final JFileChooser fc = new JFileChooser();
				fc.setCurrentDirectory(new File("."));
				fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int returnVal = fc.showOpenDialog(ExecutorFrame.this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					profilePath = file.getPath();
					getProfilePathTF().setText(profilePath);
				}
			}
		});
		panel_1.add(btnBrowse, "cell 7 0,alignx left");

		JLabel lblDefaultTimeout = new JLabel("Default Timeout (sec)");
		panel_1.add(lblDefaultTimeout, "cell 0 2");

		textField = new JTextField();
		textField.setText("30");
		panel_1.add(textField, "cell 2 2,alignx left");
		textField.setColumns(10);

		chckbxMaximizeWindow = new JCheckBox("Maximize Window");
		chckbxMaximizeWindow.setSelected(true);
		panel_1.add(chckbxMaximizeWindow, "cell 4 2");

		JLabel lblDownloadDir = new JLabel("Download Directory");
		panel_1.add(lblDownloadDir, "cell 0 3");

		txtDownload = new JTextField();
		txtDownload.setText("downloads");
		panel_1.add(txtDownload, "cell 2 3,alignx left");
		txtDownload.setColumns(10);

		chckbxJavascriptEnabledhtmlunit = new JCheckBox("JavaScript Enabled (HTMLUnit)");
		chckbxJavascriptEnabledhtmlunit.setSelected(true);
		panel_1.add(chckbxJavascriptEnabledhtmlunit, "cell 4 3");

		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(null, "Test Manager", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		contentPane.add(panel_2, "cell 0 2,grow");
		panel_2.setLayout(new MigLayout("", "[][][][][grow]", "[][][grow][][]"));

		JLabel lblTestFolder = new JLabel("Test Folder");
		panel_2.add(lblTestFolder, "cell 0 0");

		textField_2 = new JTextField();
		panel_2.add(textField_2, "cell 1 0,growx");
		textField_2.setColumns(30);

		JButton btnBrowse_1 = new JButton("Browse");
		btnBrowse_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				final JFileChooser fc = new JFileChooser();
				fc.setCurrentDirectory(new File("."));
				fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int returnVal = fc.showOpenDialog(ExecutorFrame.this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					testFolder = file.getPath();
					getTestFolderTF().setText(getRelativePath(file.getPath()));
					doParse();
				}
			}

		});
		panel_2.add(btnBrowse_1, "cell 2 0");

		lblLoading = new JLabel("Loading...");
		lblLoading.setVisible(false);
		panel_2.add(lblLoading, "cell 4 0,alignx left");

		JScrollPane scrollPane = new JScrollPane();
		panel_2.add(scrollPane, "cell 0 2 5 1,grow");

		table = new JTable();
		scrollPane.setViewportView(table);

		JButton btnConfigure = new JButton("Configure");
		btnConfigure.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				configure();
			}
		});
		panel_2.add(btnConfigure, "flowx,cell 4 3,alignx right");

		JButton btnExecute = new JButton("Configure & Execute");
		btnExecute.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				configure();
				execute();
			}
		});
		panel_2.add(btnExecute, "cell 4 3,alignx right");
	}
	
	private void execute(){
		ExecuteScripts.performExecution();
	}

	private void configure() {
		configureExecutionDetails();
		configureScripts();
	}

	private void configureExecutionDetails() {
		String browser = getBrowserCB().getSelectedItem().toString().toLowerCase();
		AxaConfig.setExecutionConf(Common.EXEC_CONF_BROWSER, browser);

		if (browser.equals("chrome")) {
			AxaConfig.setExecutionConf(Common.EXEC_CONF_CHROME_PROFILEPATH, getProfilePathTF().getText());
			AxaConfig.setExecutionConf(Common.EXEC_CONF_CHROME_FIREFOX_PROFILE, "");
		} else if (browser.equals("firefox")) {
			AxaConfig.setExecutionConf(Common.EXEC_CONF_CHROME_FIREFOX_PROFILE, getProfilePathTF().getText());
			AxaConfig.setExecutionConf(Common.EXEC_CONF_CHROME_PROFILEPATH, "");
		}
		AxaConfig.setExecutionConf(Common.EXEC_CONF_DEFAULT_TIMEOUT, getDefaultTimeOutTF().getText());
		AxaConfig.setExecutionConf(Common.EXEC_CONF_MAXIMIZE_WINDOW, String.valueOf(getChckbxMaximizeWindow().isSelected()));
		AxaConfig.setExecutionConf(Common.EXEC_CONF_CHROME_DOWNLOAD_DIR,getDownloadTF().getText());
		AxaConfig.setExecutionConf(Common.EXEC_CONF_HTMLUNIT_JAVASCRIPT_ENABLED,String.valueOf(getChckbxJavascriptEnabled().isSelected()));
		AxaConfig.saveExecutionConfChanges();
	}

	private void configureScripts() {
		List<String> executableScripts = new ArrayList<String>();
		List<TestClass> classes = model.getData();
		for(TestClass cls: classes){
			if(cls.shouldRun()) {
				executableScripts.add(cls.getPackageName());
			}
		}
		Utils.writeListToFile(Common.SCRIPT_PROP_FILE,executableScripts);
	}

	private void doParse() {
		TestClassParser parser = new TestClassParser(this, testFolder);
		getLblLoading().setVisible(true);
		parser.execute();
	}

	private String getRelativePath(String path) {
		if (path.equals(new File("").getAbsolutePath()))
			return ".";
		return path.replace(new File("").getAbsolutePath(), "").substring(1);
	}

	protected JTextField getProfilePathTF() {
		return textField_1;
	}

	protected JTextField getTestFolderTF() {
		return textField_2;
	}

	/**
	 * @param data
	 */
	public void doneParsing(List<TestClass> data) {
		model = new TestClassModel(data);
		table.setModel(model);
		getLblLoading().setVisible(false);
	}

	protected JLabel getLblLoading() {
		return lblLoading;
	}

	protected JComboBox<String> getBrowserCB() {
		return comboBox;
	}

	protected JTextField getDefaultTimeOutTF() {
		return textField;
	}

	protected JTextField getDownloadTF() {
		return txtDownload;
	}

	protected JCheckBox getChckbxMaximizeWindow() {
		return chckbxMaximizeWindow;
	}

	protected JCheckBox getChckbxJavascriptEnabled() {
		return chckbxJavascriptEnabledhtmlunit;
	}
}
