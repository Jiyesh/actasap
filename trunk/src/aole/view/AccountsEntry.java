/*
Accounting101
Copyright (C) 2009  Bhupendra Aole

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.
*/
/*
 * File: AccountEntry.java
 * Author: Bhupendra Aole
 * Date: Sep 28, 2009: 
 * 
 * Revisions:
 * ----------
 */

package aole.view;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import aole.db.DBConnection;
import aole.misc.SpringUtilities;
import aole.model.AccountsEO;

/**
 * 
 */
public class AccountsEntry implements ActionListener {
	static String catNames[] = { "Asset", "Liabililty", "Owners' Equity" };
	JTextField txtName;
	JTextField txtDes;
	JComboBox cboCat;
	JFrame frame;

	public AccountsEntry() {
		createAndShowGUI();
	}

	private void createAndShowGUI () {
		// Create and populate the panel.
		JPanel p = new JPanel(new SpringLayout());

		p.add(new JLabel("Name: ", JLabel.TRAILING));
		txtName = new JTextField(10);
		p.add(txtName);

		p.add(new JLabel("Category: ", JLabel.TRAILING));
		cboCat = new JComboBox(catNames);
		p.add(cboCat);

		p.add(new JLabel("Description: ", JLabel.TRAILING));
		txtDes = new JTextField(10);
		p.add(txtDes);

		JButton btnApply = new JButton("Apply");
		btnApply.addActionListener(this);
		btnApply.setActionCommand("insert");

		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(this);
		btnExit.setActionCommand("exit");

		p.add(btnExit);
		p.add(btnApply);

		// Lay out the panel.
		SpringUtilities.makeCompactGrid(p, 4, 2, // rows, cols
				6, 6, // initX, initY
				6, 6); // xPad, yPad

		// Create and set up the window.
		frame = new JFrame("Journal Entry Form");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		Container cp = frame.getContentPane();
		cp.add(p);

		// Display the window.
		frame.pack();
		frame.setVisible(true);
	}

	public void actionPerformed (ActionEvent ae) {
		if ("insert".equals(ae.getActionCommand())) {
			int seg1 = cboCat.getSelectedIndex() + 1, seg2 = 0;
			Connection con = DBConnection.getConnection();
			Statement stmt = null;
			try {
				stmt = con.createStatement();
				ResultSet rs = stmt
						.executeQuery("select max(segment2) from accounts "
								+ "where segment1 = " + seg1);
				rs.next();
				seg2 = rs.getInt(1) + 1;
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					if (stmt != null)
						stmt.close();
				} catch (SQLException e) {
				}
			}
			AccountsEO.insertRow(txtName.getText(), seg1, seg2, txtDes
					.getText());
		} else if ("exit".equals(ae.getActionCommand())) {
			DBConnection.closeConnection();
			frame.dispose();
		}
	}
}
